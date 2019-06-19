package edi.md.pecomobile.ServiceApi;


import edi.md.pecomobile.NetworkUtils.AutentificateUser;
import edi.md.pecomobile.NetworkUtils.GetClientInfo.GetClientInfo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServiceGetClientInfo {
    @GET("/PetrolCabinetWebService/json/GetClientInfo")
    Call<GetClientInfo> getClientInfo(@Query("SID") String param1);
}
