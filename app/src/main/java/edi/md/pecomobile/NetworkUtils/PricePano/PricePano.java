
package edi.md.pecomobile.NetworkUtils.PricePano;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PricePano {

    @SerializedName("GetPriceResult")
    @Expose
    private GetPriceResult getPriceResult;

    public GetPriceResult getGetPriceResult() {
        return getPriceResult;
    }

    public void setGetPriceResult(GetPriceResult getPriceResult) {
        this.getPriceResult = getPriceResult;
    }

}
