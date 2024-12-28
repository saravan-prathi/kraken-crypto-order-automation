package automation.order.kraken.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Result {
    public Prices XXBTZUSD;

    @SerializedName("open")
    @Expose
    private Map<String, Open> open;

    /**
     *
     * @return
     * The open
     */
    public Map<String, Open>  getOpen() {
        return open;
    }
    /**
     *
     * @param open
     * The open
     */
    public void setOpen(Map<String, Open>  open) {
        this.open = open;
    }


}
