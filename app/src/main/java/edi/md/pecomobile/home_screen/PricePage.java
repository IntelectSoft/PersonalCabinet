package edi.md.pecomobile.home_screen;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import edi.md.pecomobile.NetworkUtils.PricePano.GetPriceResult;
import edi.md.pecomobile.NetworkUtils.PricePano.Price;
import edi.md.pecomobile.NetworkUtils.PricePano.PricePano;
import edi.md.pecomobile.R;
import edi.md.pecomobile.ServiceApi.ServiceGetPricePano;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PricePage extends Fragment {
    View rootViewAdmin;
    final String BASE_URL = "http://178.168.80.129:1915";
    HashMap<String,String> listPrice;
    ArrayList<HashMap<String, String>> arrayPrice;
    SimpleAdapter adapterPriceList;
    ListView ListPrice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootViewAdmin = inflater.inflate(R.layout.fragment_price, container, false);
        ListPrice=rootViewAdmin.findViewById(R.id.LW_Price_of_price);
        arrayPrice= new ArrayList<>();

        adapterPriceList = new SimpleAdapter(getContext(),arrayPrice,R.layout.array_list_of_price,new String[]{"Name","Price"},new int[]{R.id.txtName_of_price,R.id.txtPrice_of_price});
        getPricePano();

        return rootViewAdmin;
    }
    private void getPricePano() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.MINUTES)
                .readTimeout(4, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

        ServiceGetPricePano gerritAPI = retrofit.create(ServiceGetPricePano.class);

        Call<PricePano> call = gerritAPI.getPricePano();
        call.enqueue(new Callback<PricePano>() {
            @Override
            public void onResponse(Call<PricePano> call, Response<PricePano> response) {
                if(response.isSuccessful()) {
                    PricePano resp = response.body();
                    GetPriceResult Reponse = resp.getGetPriceResult();

                    int ErrorCode= Reponse.getErrorCode();
                    if(ErrorCode==0){
                        List<Price> list_of_price = Reponse.getPrices();
                        for (Price prices:list_of_price){
                            String Name_of_price = prices.getName();
                            String Price_of_price = prices.getPrice();
                            listPrice = new HashMap<>();
                            listPrice.put("Name", Name_of_price);
                            listPrice.put("Price", Price_of_price);
                            arrayPrice.add(listPrice);
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            arrayPrice.sort(new Comparator<HashMap<String, String>>() {
                                @Override
                                public int compare(HashMap<String, String> o1, HashMap<String, String> o2) {
                                    return o1.get("Name").compareTo(o2.get("Name"));
                                }
                            });
                        }
                        ListPrice.setAdapter(adapterPriceList);

                    }else{
                        Toast.makeText(getActivity(), "Eroare pret "+ ErrorCode, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<PricePano> call, Throwable t) {
                Toast.makeText(getActivity(), "Eroare pret" +t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
