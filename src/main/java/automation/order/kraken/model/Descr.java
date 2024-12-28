package automation.order.kraken.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Descr {
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("close")
    @Expose
    private String close;

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    @SerializedName("pair")
    @Expose
    private String pair;
    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }
    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }
    /**
     *
     * @return
     * The price
     */
    public String getPrice() {
        return price;
    }
    /**
     *
     * @param price
     * The price
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     *
     * @return
     * The close
     */
    public String getClose() {
        return close;
    }
    /**
     *
     * @param close
     * The close
     */
    public void setClose(String close) {
        this.close = close;
    }
}
