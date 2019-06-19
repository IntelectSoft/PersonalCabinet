package edi.md.pecomobile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.jar.Attributes;

import edi.md.pecomobile.NetworkUtils.ContractInfo.CardsList;
import edi.md.pecomobile.NetworkUtils.ContractInfo.Contract;
import edi.md.pecomobile.NetworkUtils.ContractInfo.ContractInfo;
import edi.md.pecomobile.NetworkUtils.ContractInfo.ProductsList;
import edi.md.pecomobile.ServiceApi.ServiceGetContractInfo;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InfoCardActivity extends AppCompatActivity {
    ProgressDialog pgH;
    final String BASE_URL = "http://178.168.80.129:1915";
    ArrayList<HashMap<String,Object>> arrayProductList =new ArrayList<>();

    TextView txtCodContract,txtValidTo,txtValidFrom,txtBalanceContract,txtStatus,txtPaymentDelay,txtBonus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_card);
        pgH =new ProgressDialog(this);

        txtBalanceContract=findViewById(R.id.txtBalanceContract);
        txtCodContract=findViewById(R.id.txtCodContract);
        txtValidTo=findViewById(R.id.txtValidToContract);
        txtValidFrom=findViewById(R.id.txtValidFromContract);
        txtStatus=findViewById(R.id.txtStatusContract);
        txtPaymentDelay=findViewById(R.id.txtPaymentDelay);
        txtBonus=findViewById(R.id.txtBonusContract);

        pgH.setMessage("loading..");
        pgH.setIndeterminate(true);
        pgH.setCancelable(false);
        pgH.show();
        Intent contracts = getIntent();
        String SID = contracts.getStringExtra("SID");
        String ID_contract = contracts.getStringExtra("ID");

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

        ServiceGetContractInfo gerritAPI = retrofit.create(ServiceGetContractInfo.class);

        Call<ContractInfo> callContractInfo = gerritAPI.getContractInfo(SID,ID_contract);
        getContractInfo(callContractInfo);
    }
    private void getContractInfo(final Call<ContractInfo> autent) {
        autent.enqueue(new Callback<ContractInfo>() {
            @Override
            public void onResponse(Call<ContractInfo> call, Response<ContractInfo> response) {
                if(response.isSuccessful()) {
                    ContractInfo clientInfo = response.body();
                    int ErrorCodeContract = clientInfo.getErrorCode();
                    if(ErrorCodeContract==0){
                        Contract contract = clientInfo.getContract();
                        double Bonus = contract.getBonus();
                        double CardsBalance = contract.getCardsBalance();
                        String Code = contract.getCode();
                        String DateValidFrom = contract.getDateValidFrom();
                        if(DateValidFrom!=null) {
                            if (DateValidFrom != null)
                                DateValidFrom = DateValidFrom.replace("/Date(", "");
                            if (DateValidFrom != null)
                                DateValidFrom = DateValidFrom.replace("+0300)/", "");
                            if (DateValidFrom != null)
                                DateValidFrom = DateValidFrom.replace("+0200)/", "");
                            if (DateValidFrom != null)
                                DateValidFrom = DateValidFrom.replace(")/", "");
                        }
                            long timeFrom = Long.parseLong(DateValidFrom);
                            Date dateFrom = new Date(timeFrom);
                            SimpleDateFormat sdfChisinau = new SimpleDateFormat("yyyy.MM.dd");
                            TimeZone tzInChisinau = TimeZone.getTimeZone("Europe/Chisinau");
                            sdfChisinau.setTimeZone(tzInChisinau);
                            DateValidFrom = sdfChisinau.format(dateFrom);

                        String DateValidTo =contract.getDateValidTo();
                        if(DateValidTo!=null) {
                            if (DateValidTo != null)
                                DateValidTo = DateValidTo.replace("/Date(", "");
                            if (DateValidTo != null)
                                DateValidTo = DateValidTo.replace("+0300)/", "");
                            if (DateValidTo != null)
                                DateValidTo = DateValidTo.replace("+0200)/", "");
                            if (DateValidTo != null)
                                DateValidTo = DateValidTo.replace(")/", "");
                        }
                        long timeTo = Long.parseLong(DateValidTo);
                        Date dateTo = new Date(timeTo);
                        DateValidTo = sdfChisinau.format(dateTo);


                        int PaymentDelay = contract.getPaymentDelay();
                        int Status = contract.getStatus();

                        List<CardsList> cardsList = contract.getCardsList();

                        List<ProductsList> productList = contract.getProductsList();
                        for(ProductsList list:productList){
                            String NameProduct = list.getName();
                            String GroupProduct = list.getGroup();
                            String IDProduct = list.getID();
                            Double PriceProduct=list.getPrice();
                            String CodeProduct = list.getCode();
                            HashMap<String,Object> product = new HashMap<>();
                            product.put("Name",NameProduct);
                            product.put("Group",GroupProduct);
                            product.put("ID",IDProduct);
                            product.put("Price",PriceProduct);
                            product.put("Code",CodeProduct);
                            arrayProductList.add(product);
                        }

                        txtBalanceContract.setText(String.format("%.2f",CardsBalance));
                        txtCodContract.setText(Code);
                        txtValidFrom.setText(DateValidFrom);
                        txtValidTo.setText(DateValidTo);
                        txtStatus.setText(String.valueOf(Status));
                        txtPaymentDelay.setText(String.valueOf(PaymentDelay));
                        txtBonus.setText(String.format("%.2f",Bonus));
                        pgH.dismiss();

                    }else{
                        pgH.dismiss();
                    }
                }
            }
            @Override
            public void onFailure(Call<ContractInfo> call, Throwable t) {
                pgH.dismiss();
            }
        });
    }
}
