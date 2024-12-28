package automation.order.kraken;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import automation.order.kraken.api.KrakenApi;
import automation.order.kraken.api.KrakenApi.Method;
import automation.order.kraken.model.Order;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class KrakenAutomatedOrders {
    //private static double percentage = 1.055;
    private static double percentage = Double.parseDouble(System.getenv("PROFIT_PERCENTAGE")); // Set the value as per your desired profit slab, for example 1.055 for 5.5%, and 1.028 for 2.8% in AWS lambda environment variables

    private static KrakenApi api = new KrakenApi();

    public static void main(String[] args) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InterruptedException {

        api.setKey("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"); // Put your Kraken account's Key here. This should ideally be moved to AWS Secrets manager or a third party Vault in the future
        api.setSecret("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"); // Put your Kraken account's Secret here. This should ideally be moved to AWS Secrets manager or a third party Vault in the future

        String response;

        response = api.queryPrivate(Method.OPEN_ORDERS);
        System.out.println(response);
        Gson gson = new Gson();
        Order myObj = gson.fromJson(response,
                new TypeToken<Order>() {
                }.getType());

        Map<String, String> input = new HashMap<>();
        for (String key : myObj.getResult().getOpen().keySet()) {

            if (myObj.getResult().getOpen().get(key).getDescr().getClose().isEmpty() &&
                    myObj.getResult().getOpen().get(key).getDescr().getPair().equals("XBTUSD")
            ) {
                double volume = Double.parseDouble(myObj.getResult().getOpen().get(key).getVol());
                double price = Double.parseDouble(myObj.getResult().getOpen().get(key).getDescr().getPrice());
                double priceOfVolumeBeingTraded = volume * price;

                if (priceOfVolumeBeingTraded < 125 &&
                        priceOfVolumeBeingTraded > 75) {

                    System.out.println("Lonely transaction found with order id: " + key + "of type : " + myObj.getResult().getOpen().get(key).getDescr().getType() + "for price : " + myObj.getResult().getOpen().get(key).getDescr().getPrice());
                    input.clear();
                    input.put("txid", key);
                    api.queryPrivate(Method.CANCEL_ORDER, input);
                    Thread.sleep(8000);
                    if (myObj.getResult().getOpen().get(key).getDescr().getType().equals("buy")) {
                        buyOrder((int) Double.parseDouble(myObj.getResult().getOpen().get(key).getDescr().getPrice()));
                    }
                    if (myObj.getResult().getOpen().get(key).getDescr().getType().equals("sell")) {
                        sellOrder((int) Double.parseDouble(myObj.getResult().getOpen().get(key).getDescr().getPrice()));
                    }
                }
            }
        }
    }

    private static void sellOrder(int sellAtAmountInUSD) throws IOException, InterruptedException, InvalidKeyException, NoSuchAlgorithmException {
        String response;

        double buyAtAmountInUSD = (1.0 * sellAtAmountInUSD) / percentage;

        buyAtAmountInUSD = Math.round(buyAtAmountInUSD * 10);
        buyAtAmountInUSD = buyAtAmountInUSD / 10;

        double volume = 100.0 / buyAtAmountInUSD;
        Map<String, String> input = new HashMap<>();
        input.put("pair", "XXBTZUSD");

        /*for (int i = 1; i <= 1; i++) {
            response = api.queryPublic(Method.TICKER, input);
            Gson gson = new Gson();
            Ticker ticker = gson.fromJson(response, Ticker.class);
            System.out.println("XBT to USD last trade closed price : "+ticker.result.XXBTZUSD.c[0]);
            Thread.sleep(1000);
        }*/

        input.clear();
        input.put("pair", "XXBTZUSD");
        input.put("type", "sell");
        input.put("ordertype", "limit");
        input.put("price", String.valueOf(sellAtAmountInUSD));
        input.put("volume", String.valueOf(volume));
        input.put("close[ordertype]", "take-profit-limit");
        input.put("close[price]", String.valueOf(buyAtAmountInUSD));
        input.put("close[price2]", String.valueOf(buyAtAmountInUSD));
        //System.out.println("Placing the take-profit-limit SELL order for sellAtAmountInUSD : "+sellAtAmountInUSD+ "   Volume : "+volume + "   buyAtAmountInUSD : " + buyAtAmountInUSD);
        response = api.queryPrivate(Method.ADD_ORDER, input);
        //System.out.println(response);
    }

    private static void buyOrder(int buyAtAmountInUSD) throws IOException, InterruptedException, InvalidKeyException, NoSuchAlgorithmException {
        String response;

        double volume = 100.0 / buyAtAmountInUSD;
        double sellAtAmountInUSD = buyAtAmountInUSD * percentage;

        sellAtAmountInUSD = Math.round(sellAtAmountInUSD * 10);
        sellAtAmountInUSD = sellAtAmountInUSD / 10;

        Map<String, String> input = new HashMap<>();
        input.put("pair", "XXBTZUSD");

        /*for (int i = 1; i <= 1; i++) {
            response = api.queryPublic(Method.TICKER, input);
            Gson gson = new Gson();
            Ticker ticker = gson.fromJson(response, Ticker.class);
            System.out.println("XBT to USD last trade closed price : "+ticker.result.XXBTZUSD.c[0]);
            Thread.sleep(1000);
        }*/

        input.clear();
        input.put("pair", "XXBTZUSD");
        input.put("type", "buy");
        input.put("ordertype", "limit");
        input.put("price", String.valueOf(buyAtAmountInUSD));
        input.put("volume", String.valueOf(volume));
        input.put("close[ordertype]", "take-profit-limit");
        input.put("close[price]", String.valueOf(sellAtAmountInUSD));
        input.put("close[price2]", String.valueOf(sellAtAmountInUSD));
        //System.out.println("Placing the take-profit-limit BUY order for buyAtAmountInUSD : "+buyAtAmountInUSD+ "   Volume : "+volume + "   sellAtAmountInUSD : " + sellAtAmountInUSD);
        response = api.queryPrivate(Method.ADD_ORDER, input);
        //System.out.println(response);
    }
}
