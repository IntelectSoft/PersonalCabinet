package edi.md.pecomobile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import edi.md.pecomobile.NetworkUtils.ContractInfo.Contract;
import edi.md.pecomobile.NetworkUtils.ContractInfo.ContractInfo;
import edi.md.pecomobile.ServiceApi.ServiceGetContractInfo;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class MyCardsPage extends Fragment {
    final String BASE_URL = "http://178.168.80.129:1915";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootViewAdmin = inflater.inflate(R.layout.fragment_my_cards, container, false);
        TextView txt = rootViewAdmin.findViewById(R.id.textViewmyCads);



//        SharedPreferences Client =getActivity().getSharedPreferences("SaveClient", MODE_PRIVATE);
//        String SID = Client.getString("SID","0");
//        String ID_contract = Client.getString("ID_Contract","0");
//
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .connectTimeout(3, TimeUnit.MINUTES)
//                .readTimeout(4, TimeUnit.MINUTES)
//                .writeTimeout(2, TimeUnit.MINUTES)
//                .build();
//        Gson gson = new GsonBuilder()
//                .setLenient()
//                .create();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .client(okHttpClient)
//                .build();
//
//        ServiceGetContractInfo gerritAPI = retrofit.create(ServiceGetContractInfo.class);
//
//        Call<ContractInfo> callContractInfo = gerritAPI.getContractInfo(SID,ID_contract);
//        getCardInfo(callContractInfo);
        return rootViewAdmin;
    }
    private void getCardInfo(final Call<ContractInfo> autent) {
        autent.enqueue(new Callback<ContractInfo>() {
            @Override
            public void onResponse(Call<ContractInfo> call, Response<ContractInfo> response) {
                if(response.isSuccessful()) {
                    ContractInfo clientInfo = response.body();
                    int ErrorCodeContract = clientInfo.getErrorCode();
                    if(ErrorCodeContract==0){
                        Contract contract = clientInfo.getContract();

                    }
                }
            }
            @Override
            public void onFailure(Call<ContractInfo> call, Throwable t) {

            }
        });
    }
}
