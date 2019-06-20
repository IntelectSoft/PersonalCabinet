package edi.md.pecomobile.ServiceApi;


import edi.md.pecomobile.NetworkUtils.GetClientInfo.GetClientInfo;
import edi.md.pecomobile.NetworkUtils.PricePano.GetPriceResult;
import edi.md.pecomobile.NetworkUtils.PricePano.PricePano;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceGetPricePano {
    @GET("/PetrolCabinetWebService/json/GetPrice")
    Call<PricePano> getPricePano();
}
