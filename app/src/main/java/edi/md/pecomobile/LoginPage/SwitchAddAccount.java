package edi.md.pecomobile.LoginPage;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import edi.md.pecomobile.CabinetLoginPage;
import edi.md.pecomobile.CabinetPage;
import edi.md.pecomobile.NetworkUtils.AutentificateUser;
import edi.md.pecomobile.R;
import edi.md.pecomobile.ServiceApi.GlobalVariable;
import edi.md.pecomobile.ServiceApi.ServiceAutentificateUser;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class SwitchAddAccount extends Fragment {
    final String BASE_URL = "http://178.168.80.129:1915";
    Button sign_in;
    EditText telephone,passwords;
    String SID;
    ProgressBar pgBar;
    Button add_new_account;
    SharedPreferences USER_Auth;
    SharedPreferences.Editor sPrefInput;
    JSONArray UsersList;
    JSONObject UserData,newUser;
    ListView listUsers;
    HashMap<String,Object> listUsersHashMap;
    ArrayList<HashMap<String, Object>> array;
    SimpleAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootViewAdmin = inflater.inflate(R.layout.fragment_switch_add_account, container, false);

        add_new_account=rootViewAdmin.findViewById(R.id.btn_add_new_account);
        listUsers = rootViewAdmin.findViewById(R.id.LW_list_accounts);
        array = new ArrayList<>();

        USER_Auth =getActivity().getSharedPreferences("UsersData", MODE_PRIVATE);
        sPrefInput = USER_Auth.edit();
        adapter = new SimpleAdapter(getContext(),array,R.layout.array_list_of_users,new String[]{"Name","TypeString"},new int[]{R.id.txt_name_user,R.id.txt_type_user});

        String UserList = USER_Auth.getString("UsersListArray","[]");
        try {
            UsersList = new JSONArray(UserList);
            for (int i=0; i<UsersList.length();i++){
                JSONObject user = UsersList.getJSONObject(i);
                int typeUser = user.getInt("AuthType");
                String NameUser = user.getString("Name");
                String PasswordUser = user.getString("Password");
                String LoginUser="null",IDNOUser= "null",PhoneUser="null",Type="null";
                if (typeUser==1){
                    LoginUser = user.getString("Login");
                    IDNOUser = user.getString("IDNO");
                    Type="Persoana juridica";
                }else if (typeUser==0){
                    PhoneUser = user.getString("Phone");
                    Type="Persoana fizica";
                }
                String SID = user.getString("SID");
                listUsersHashMap = new HashMap<>();
                listUsersHashMap.put("Name",NameUser);
                listUsersHashMap.put("Type",typeUser);
                listUsersHashMap.put("TypeString",Type);
                listUsersHashMap.put("Password",PasswordUser);
                listUsersHashMap.put("Login",LoginUser);
                listUsersHashMap.put("IDNO",IDNOUser);
                listUsersHashMap.put("Phone",PhoneUser);
                listUsersHashMap.put("SID",SID);
                array.add(listUsersHashMap);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    array.sort(new Comparator<HashMap<String, Object>>() {
                        @Override
                        public int compare(HashMap<String, Object> o1, HashMap<String, Object> o2) {
                            return o1.get("Name").toString().compareTo(o2.get("Name").toString());
                        }
                    });
                }

                listUsers.setAdapter(adapter);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        listUsers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder input = new AlertDialog.Builder(getActivity());
                input.setTitle("Atentie! ");
                input.setCancelable(false);
                input.setMessage("Doriti sa eliminati account-ul dat? ");
                input.setPositiveButton("Da", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                input.setNegativeButton("Nu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = input.create();
                dialog.show();
                return false;
            }
        });
        listUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AutentificateUser User = new AutentificateUser();
                int type = (Integer)array.get(position).get("Type");
                User.setPassword((String) array.get(position).get("Password"));
                User.setAuthType(type);
                switch (type){
                    case 0:{
                        User.setPhone((String) array.get(position).get("Phone"));
                    }break;
                    case 1:{
                        User.setIDNO((String) array.get(position).get("IDNO"));
                        User.setUser((String) array.get(position).get("Login"));
                    }break;
                }
                autentic_user(User);
            }
        });



        add_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frm_layaout,new CabinetLoginPage()).commit();
            }
        });
        return rootViewAdmin;
    }
    private void autentic_user(AutentificateUser user) {
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
                        ((GlobalVariable) getActivity().getApplication()).setClientInfoReceived(false);
                        //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frm_layaout,new CabinetPage()).commit();
                    }else{
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
                Toast.makeText(getActivity(), "EroareAutentificare" +t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
