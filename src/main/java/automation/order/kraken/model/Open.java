package automation.order.kraken.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Open {
    @SerializedName("descr")
    @Expose
    private Descr descr;
    /**
     *
     * @return
     * The descr
     */
    public Descr getDescr() {
        return descr;
    }
    /**
     *
     * @param descr
     * The descr
     */
    public void setDescr(Descr descr) {
        this.descr = descr;
    }


    public String getVol() {
        return vol;
    }

    public void setVol(String vol) {
        this.vol = vol;
    }

    @SerializedName("vol")
    @Expose
    private String vol;

}