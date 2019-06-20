package edi.md.pecomobile.LoginPage;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

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

public class FizicTab extends Fragment {
    final String BASE_URL = "http://178.168.80.129:1915";
    Button sign_in;
    EditText telephone,passwords;
    String SID;
    ProgressBar pgBar;
    SharedPreferences USER_Auth;
    SharedPreferences.Editor sPrefInput;
    JSONArray UsersList;
    JSONObject UserData,newUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootViewAdmin = inflater.inflate(R.layout.fragment_fizic_tab, container, false);

        telephone= rootViewAdmin.findViewById(R.id.username_fizic);
        passwords=rootViewAdmin.findViewById(R.id.password_fizic);
        sign_in = rootViewAdmin.findViewById(R.id.btn_add_new_account);
        pgBar=rootViewAdmin.findViewById(R.id.progressBarFizic);
        pgBar.setVisibility(View.INVISIBLE);

        USER_Auth =getActivity().getSharedPreferences("UsersData", MODE_PRIVATE);
        sPrefInput = USER_Auth.edit();

        String UserList = USER_Auth.getString("UsersListArray","[]");
        try {
            UsersList = new JSONArray(UserList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pgBar.setVisibility(View.VISIBLE);
                final AutentificateUser user = new AutentificateUser();
                user.setAuthType(0);
                user.setPassword(passwords.getText().toString());
                user.setPhone(telephone.getText().toString());
                autentic_user(user);

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
                        UserData = new JSONObject();
                        try {
                            UserData.put("AuthType",0);
                            UserData.put("SID",SID);
                            UserData.put("Password",passwords.getText().toString());
                            UserData.put("Phone",telephone.getText().toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (int i=0;i<UsersList.length();i++) {
                            try {
                                JSONObject saved_user = UsersList.getJSONObject(i);
                                int type= saved_user.getInt("AuthType");
                                if (type==0){
                                    String Phone_saved = saved_user.getString("Phone");
                                    if (Phone_saved.equals(telephone.getText().toString())) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                            UsersList.remove(i);
                                        }
                                    }
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        UsersList.put(UserData);

                        sPrefInput.putInt("AuthType",0);
                        sPrefInput.putString("Phone",telephone.getText().toString());
                        sPrefInput.putString("Password_fizic",passwords.getText().toString());
                        sPrefInput.putString("SID",SID);
                        sPrefInput.putBoolean("UserAuth",true);
                        sPrefInput.putString("UsersListArray",UsersList.toString());
                        sPrefInput.apply();
                        pgBar.setVisibility(View.INVISIBLE);
                        ((GlobalVariable) getActivity().getApplication()).setLoginAutentificate(true);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frm_layaout,new CabinetPage()).commit();
                    }else{
                        sPrefInput.putBoolean("UserAuth",false);
                        sPrefInput.apply();
                        Toast.makeText(getActivity(), "Eroare autentificare"+ ErrorCode, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<AutentificateUser> call, Throwable t) {
                sPrefInput.putBoolean("UserAuth",false);
                sPrefInput.apply();
                pgBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity(), "EroareAutentificare" +t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
