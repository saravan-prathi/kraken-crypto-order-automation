package automation.order.kraken;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class LambdaRequestHandler
        implements RequestStreamHandler {
    public void handleRequest(InputStream inputStream,
                              OutputStream outputStream, Context context) {
        context.getLogger().log("Running the lambda for kraken order automation");
        try {
            KrakenAutomatedOrders.main(null);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}