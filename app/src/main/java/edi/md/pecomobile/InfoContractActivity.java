package edi.md.pecomobile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.jar.Attributes;

import edi.md.pecomobile.ContractDetail.AssortimentList;
import edi.md.pecomobile.ContractDetail.CardList;
import edi.md.pecomobile.NetworkUtils.ContractInfo.CardsList;
import edi.md.pecomobile.NetworkUtils.ContractInfo.Contract;
import edi.md.pecomobile.NetworkUtils.ContractInfo.ContractInfo;
import edi.md.pecomobile.NetworkUtils.ContractInfo.ProductsList;
import edi.md.pecomobile.ServiceApi.ServiceGetContractInfo;
import edi.md.pecomobile.adapters.ViewPageAdapterContractDetail;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InfoContractActivity extends AppCompatActivity {
    ProgressDialog pgH;
    final String BASE_URL = "http://178.168.80.129:1915";
    ArrayList<HashMap<String,Object>> arrayProductList =new ArrayList<>();

    private ViewPageAdapterContractDetail mSectionsPagerAdapter;
    private ViewPager mViewPager;

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

        mViewPager = (ViewPager) findViewById(R.id.ViewPageContract);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout_Asl_OR_cards);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

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
                        String DateValidTo =contract.getDateValidTo();
                        int PaymentDelay = contract.getPaymentDelay();
                        int Status = contract.getStatus();
                        List<ProductsList> productList = contract.getProductsList();
                        List<CardsList> cardsList = contract.getCardsList();

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

                        JSONArray listProduct = new JSONArray();
                        JSONArray listCards = new JSONArray();

                        for(ProductsList list:productList){
                            JSONObject new_product = new JSONObject();
                            String NameProduct = list.getName();
                            String GroupProduct = list.getGroup();
                            String IDProduct = list.getID();
                            Double PriceProduct=list.getPrice();
                            String CodeProduct = list.getCode();
                            Double DiscountProduct = list.getDiscount();
                            try {
                                new_product.put("Name",NameProduct);
                                new_product.put("Group",GroupProduct);
                                new_product.put("ID",IDProduct);
                                new_product.put("Price",PriceProduct);
                                new_product.put("Code",CodeProduct);
                                new_product.put("Discount",DiscountProduct);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            listProduct.put(new_product);
                        }

                        for (CardsList cards:cardsList){
                            JSONObject new_card = new JSONObject();
                            String CodeCard = cards.getCode();
                            int LimitType = cards.getLimitType();
                            double BalanceAccountCard = cards.getBalanceAccountCard();
                            String NameCards = cards.getName();
                            boolean IsActiveCards = cards.getIsActive();

                            try {
                                new_card.put("Code",CodeCard);
                                new_card.put("LimitType",LimitType);
                                new_card.put("BalanceAccountCard",BalanceAccountCard);
                                new_card.put("Name", NameCards);
                                new_card.put("IsActive",IsActiveCards);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            listCards.put(new_card);
                        }

                        mSectionsPagerAdapter = new ViewPageAdapterContractDetail(getSupportFragmentManager(),listProduct.toString(), listCards.toString());
                        mViewPager.setAdapter(mSectionsPagerAdapter);

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
                Toast.makeText(InfoContractActivity.this, "Eroare " +t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
