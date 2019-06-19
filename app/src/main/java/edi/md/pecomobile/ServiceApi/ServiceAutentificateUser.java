package edi.md.pecomobile.ServiceApi;


import edi.md.pecomobile.NetworkUtils.AutentificateUser;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServiceAutentificateUser {
//    @GET("/epos/json/GetAssortmentList")
//      Call<AutentificateUser> AuthenticateUser(@Query("Token") String param1, @Query("WorkplaceId") String param2);
    @POST("/PetrolCabinetWebService/json/AuthenticateUser")
    Call<AutentificateUser> postUser (@Body AutentificateUser post);
}
