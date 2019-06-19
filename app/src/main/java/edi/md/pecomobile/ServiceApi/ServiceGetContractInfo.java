package edi.md.pecomobile.ServiceApi;


import edi.md.pecomobile.NetworkUtils.ContractInfo.ContractInfo;
import edi.md.pecomobile.NetworkUtils.GetClientInfo.GetClientInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceGetContractInfo {
    @GET("/PetrolCabinetWebService/json/GetContractInfo")
    Call<ContractInfo> getContractInfo(@Query("SID") String param1, @Query("ContractID") String param2);
}
