package edi.md.pecomobile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import edi.md.pecomobile.LoginPage.SwitchAddAccount;
import edi.md.pecomobile.NetworkUtils.AutentificateUser;
import edi.md.pecomobile.NetworkUtils.GetClientInfo.Client;
import edi.md.pecomobile.NetworkUtils.GetClientInfo.ContractClient;
import edi.md.pecomobile.NetworkUtils.GetClientInfo.GetClientInfo;
import edi.md.pecomobile.ServiceApi.GlobalVariable;
import edi.md.pecomobile.ServiceApi.ServiceAutentificateUser;
import edi.md.pecomobile.ServiceApi.ServiceGetClientInfo;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;


public class CabinetPage extends Fragment {
    TextView name,idnp,balance,amount,credit,master_balance,contracts,total_debt_sum;
    final String BASE_URL = "http://178.168.80.129:1915";
    String Contracts_ID="0",Contracts_Name="--",Contracts_Code="--";
    ListView contracte;
    ProgressDialog pgH;
    String SID;
    HashMap<String,String> listContracts;
    ArrayList<HashMap<String, String>> array;
    SimpleAdapter adapter;
    SharedPreferences USER_Auth;
    SharedPreferences.Editor sPrefInput;
    Spinner spinner_log_out;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootViewAdmin = inflater.inflate(R.layout.fragment_cabinet, container, false);
        name = rootViewAdmin.findViewById(R.id.txtNameClient);
        idnp = rootViewAdmin.findViewById(R.id.txtIDNPClient);
        balance = rootViewAdmin.findViewById(R.id.txtBalanceClient);
        amount = rootViewAdmin.findViewById(R.id.txtAmountClient);
        credit = rootViewAdmin.findViewById(R.id.txtCreditClient);
        master_balance = rootViewAdmin.findViewById(R.id.txtMasterBalance);
        contracts = rootViewAdmin.findViewById(R.id.txtContractsClient);
        total_debt_sum = rootViewAdmin.findViewById(R.id.txtTotalDebtSum);
        contracte=rootViewAdmin.findViewById(R.id.LW_Cards);
        spinner_log_out = rootViewAdmin.findViewById(R.id.SpinerCabinetPage);

        List<String> categories_spiner = new ArrayList<String>();
        categories_spiner.add("");
        categories_spiner.add("Log-out");
        categories_spiner.add("Swich account");

        array = new ArrayList<>();

        adapter = new SimpleAdapter(getContext(),array,R.layout.array_list_contracts,new String[]{"Code","Name"},new int[]{R.id.txtName_of_price,R.id.txtPrice_of_price});
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories_spiner);

        boolean receivedClientInfo = ((GlobalVariable) getActivity().getApplication()).getClientInfoReceived();

        USER_Auth =getActivity().getSharedPreferences("UsersData", MODE_PRIVATE);
        sPrefInput = USER_Auth.edit();

        SID = USER_Auth.getString("SID","0");

        spinner_log_out.setAdapter(dataAdapter);
        spinner_log_out.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        break;
                    case 1:
                        sPrefInput.putBoolean("UserAuth",false);
                        sPrefInput.apply();
                        ((GlobalVariable) getActivity().getApplication()).setLoginAutentificate(false);
                        ((GlobalVariable) getActivity().getApplication()).setClientInfoReceived(false);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frm_layaout,new CabinetLoginPage()).commit();
                        break;
                    case 2 :
                        sPrefInput.putBoolean("UserAuth",false);
                        sPrefInput.apply();
                        ((GlobalVariable) getActivity().getApplication()).setLoginAutentificate(false);
                        ((GlobalVariable) getActivity().getApplication()).setClientInfoReceived(false);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frm_layaout,new SwitchAddAccount()).commit();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!receivedClientInfo){
            pgH = new ProgressDialog(getActivity());
            pgH.setMessage("loading..");
            pgH.setIndeterminate(true);
            pgH.setCancelable(false);
            pgH.show();
            getClientInfo();
        }else{
            SharedPreferences Client =getActivity().getSharedPreferences("SaveClient", MODE_PRIVATE);
            Contracts_Code = Client.getString("Code_Contracts","--");
            Contracts_Name = Client.getString("Name_Contracts","--");
            Contracts_ID = Client.getString("ID_Contract","0");
            if(Contracts_ID.equals("0")){
                listContracts = new HashMap<>();
                listContracts.put("Name",Contracts_Name);
                listContracts.put("Code",Contracts_Code);
                listContracts.put("ID",Contracts_ID);
                array.add(listContracts);
                contracte.setAdapter(adapter);
            }
            name.setText(Client.getString("Name","--"));
            idnp.setText(Client.getString("IDNP","--"));
            balance.setText(Client.getString("Balance","--"));
            amount.setText(Client.getString("Amount","--"));
            credit.setText(Client.getString("Credit","--"));
            master_balance.setText(Client.getString("MasterBalance","--"));
            contracts.setText(Client.getString("Code_Contracts","--"));
            total_debt_sum.setText(Client.getString("TotalDebtSum","--"));
        }

        contracte.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent contracts = new Intent(".InfoContract");
                contracts.putExtra("ID",Contracts_ID);
                contracts.putExtra("SID",SID);
                startActivity(contracts);
            }
        });
        return rootViewAdmin;
    }
    private void getClientInfo() {

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
        ServiceGetClientInfo gerritAPI = retrofit.create(ServiceGetClientInfo.class);
        Call<GetClientInfo> callClient = gerritAPI.getClientInfo(SID);

        callClient.enqueue(new Callback<GetClientInfo>() {
            @Override
            public void onResponse(Call<GetClientInfo> call, Response<GetClientInfo> response) {
                if(response.isSuccessful()) {
                    GetClientInfo clientInfo = response.body();
                    int ErrorCodeClient = clientInfo.getErrorCode();
                    if(ErrorCodeClient==0){
                        ((GlobalVariable) getActivity().getApplication()).setClientInfoReceived(true);
                        Client received = clientInfo.getClient();
                        Double Amount = received.getAmount();
                        Double Balance = received.getBalance();
                        Double CardsBalance = received.getCardsBalance();

                        List<ContractClient> Contracts = received.getContracts();
                        if(Contracts.size()>0){
                            Contracts_ID = Contracts.get(0).getID();
                            Contracts_Code = Contracts.get(0).getCode();
                            Contracts_Name= Contracts.get(0).getName();

                            listContracts = new HashMap<>();
                            listContracts.put("Name",Contracts_Name);
                            listContracts.put("Code",Contracts_Code);
                            listContracts.put("ID",Contracts_ID);
                            array.add(listContracts);
                            contracte.setAdapter(adapter);
                        }


                        Double Credit = received.getCredit();
                        String IDNP = received.getIDNP();
                        String Name =received.getName();
                        Double MasterBalance = received.getMasterBalance();
                        Double NonInvoicedConsumptionAmount = received.getNonInvoicedConsumptionAmount();
                        Double Overdraft = received.getOverdraft();
                        Integer Status = received.getStatus();
                        Double TotalDebtSum = received.getTotalDebtSum();
                        Double UnpaidInvoiceConsumptionAmount = received.getUnpaidInvoiceConsumptionAmount();

                        SharedPreferences Client =getActivity().getSharedPreferences("SaveClient", MODE_PRIVATE);
                        SharedPreferences.Editor sPrefInput = Client.edit();
                        sPrefInput.putString("SID", SID);
                        sPrefInput.putString("Amount", String.format("%.2f",Amount));
                        sPrefInput.putString("Balance",String.format("%.2f",Balance));
                        sPrefInput.putString("CardsBalance",String.format("%.2f",CardsBalance));
                        sPrefInput.putString("ID_Contract", Contracts_ID);
                        sPrefInput.putString("Code_Contracts", Contracts_Code);
                        sPrefInput.putString("Name_Contracts", Contracts_Name);
                        sPrefInput.putString("Credit", String.format("%.2f",Credit));
                        sPrefInput.putString("IDNP", IDNP);
                        sPrefInput.putString("Name", Name);
                        sPrefInput.putString("MasterBalance", String.format("%.2f",MasterBalance));
                        sPrefInput.putString("NonInvoicedConsumptionAmount", String.format("%.2f",NonInvoicedConsumptionAmount));
                        sPrefInput.putString("Overdraft", String.format("%.2f",Overdraft));
                        sPrefInput.putInt("Status", Status);
                        sPrefInput.putString("TotalDebtSum", String.format("%.2f",TotalDebtSum));
                        sPrefInput.putString("UnpaidInvoiceConsumptionAmount", String.format("%.2f",UnpaidInvoiceConsumptionAmount));
                        sPrefInput.apply();

                        String usersList = USER_Auth.getString("UsersListArray","[]");
                        try {
                            JSONArray ListUsers = new JSONArray(usersList);
                            for (int i=0; i<ListUsers.length();i++){
                                JSONObject user = ListUsers.getJSONObject(i);
                                String User_SID = user.getString("SID");
                                if (User_SID.contains(SID)){
                                    user.put("Name",Name);
                                }
                            }
                            SharedPreferences.Editor UserAdd = USER_Auth.edit();
                            UserAdd.putString("UsersListArray",ListUsers.toString());
                            UserAdd.apply();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        name.setText(Name);
                        idnp.setText(IDNP);
                        balance.setText(String.format("%.2f",Balance));
                        amount.setText(String.format("%.2f",Amount));
                        credit.setText(String.format("%.2f",Credit));
                        master_balance.setText(String.format("%.2f",MasterBalance));
                        contracts.setText(Contracts_Code);
                        total_debt_sum.setText( String.format("%.2f",TotalDebtSum));

                        pgH.dismiss();

                    }else if (ErrorCodeClient==5){
                        AutentificateUser user = new AutentificateUser();
                        int auth_type = USER_Auth.getInt("AuthType",10);
                        user.setAuthType(auth_type);
                        if (auth_type==1){
                            user.setIDNO(USER_Auth.getString("IDNO","0"));
                            user.setPassword(USER_Auth.getString("Password","0"));
                            user.setUser(USER_Auth.getString("Login","0"));
                        }else if (auth_type==0){
                            user.setPassword(USER_Auth.getString("Password_fizic","0"));
                            user.setUser(USER_Auth.getString("Phone","0"));
                        }
                        autentic_user(user);
                    }else{
                        pgH.dismiss();
                        Toast.makeText(getActivity(), "Eroare! Codul: " + ErrorCodeClient, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<GetClientInfo> call, Throwable t) {
                pgH.dismiss();
                Toast.makeText(getActivity(), "Eroare "+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void autentic_user(AutentificateUser user) {
        USER_Auth =getActivity().getSharedPreferences("UserData", MODE_PRIVATE);
        sPrefInput = USER_Auth.edit();
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

        ServiceAutentificateUser gerritAPI = retrofit.create(ServiceAutentificateUser.class);

        Call<AutentificateUser> call = gerritAPI.postUser(user);
        call.enqueue(new Callback<AutentificateUser>() {
            @Override
            public void onResponse(Call<AutentificateUser> call, Response<AutentificateUser> response) {
                if(response.isSuccessful()) {
                    AutentificateUser resp = response.body();
                    SID = resp.getSID();
                    int ErrorCode= resp.getErrorCode();
                    if(ErrorCode==0){
                        sPrefInput.putString("SID",SID);
                        sPrefInput.putBoolean("UserAuth",true);
                        sPrefInput.apply();
                        ((GlobalVariable) getActivity().getApplication()).setLoginAutentificate(true);
                        getClientInfo();
                    }else{
                        pgH.dismiss();
                        ((GlobalVariable) getActivity().getApplication()).setLoginAutentificate(false);
                        sPrefInput.putBoolean("UserAuth",false);
                        sPrefInput.apply();
                        Toast.makeText(getActivity(), "Eroare autentificare "+ ErrorCode, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<AutentificateUser> call, Throwable t) {
                ((GlobalVariable) getActivity().getApplication()).setLoginAutentificate(false);
                sPrefInput.putBoolean("UserAuth",false);
                sPrefInput.apply();
                pgH.dismiss();
                Toast.makeText(getActivity(), "EroareAutentificare" +t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
