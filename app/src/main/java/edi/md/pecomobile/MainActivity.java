package edi.md.pecomobile;

import android.content.SharedPreferences;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import edi.md.pecomobile.LoginPage.SwitchAddAccount;
import edi.md.pecomobile.ServiceApi.GlobalVariable;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage,mTextViewHome,mTextViewMyCards,mTextViewMap,mTextViewSettings;
    ImageView img_logo;
    BottomNavigationView navView;
    ViewPager viewPager;
    JSONArray UsersList;
    int countUsers;

    boolean mHomeClicked, mMyCardsClicked,mMapClicked,mSettingsClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);


        navView = findViewById(R.id.bottom_navigation);

        SharedPreferences USER_Auth =getSharedPreferences("UsersData", MODE_PRIVATE);
        boolean user_auth = USER_Auth.getBoolean("UserAuth",false);
        ((GlobalVariable)getApplication()).setLoginAutentificate(user_auth);

        String UserList = USER_Auth.getString("UsersListArray","[]");
        try {
            UsersList = new JSONArray(UserList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        countUsers=UsersList.length();


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectedFragment=new HomePage();
                    break;
                case R.id.navigation_cards:
                    selectedFragment=new MyCardsPage();
                    break;
                case R.id.navigation_cabinet:
                    boolean autentificate = ((GlobalVariable)getApplication()).getLoginAutentificate();
                    if(!autentificate){
                        if(countUsers==0)
                            selectedFragment=new CabinetLoginPage();
                        else
                            selectedFragment=new SwitchAddAccount();
                    }else{
                        selectedFragment=new CabinetPage();
                    }
                    break;
                case R.id.navigation_maps:

                    selectedFragment=new MapsPage();
                    break;
            }
            //if (selectedFragment!=null)
                //getSupportFragmentManager().beginTransaction().replace(R.id.frm_layaout,selectedFragment).commit();
            return true;
        }
    };

}
