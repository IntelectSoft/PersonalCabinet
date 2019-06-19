package edi.md.pecomobile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import edi.md.pecomobile.ServiceApi.GlobalVariable;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    ImageView img_logo;
    BottomNavigationView navView;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navView = findViewById(R.id.nav_view);
        img_logo=findViewById(R.id.img_logo);

        SharedPreferences USER_Auth =getSharedPreferences("UserData", MODE_PRIVATE);
        boolean user_auth = USER_Auth.getBoolean("UserAuth",false);
        ((GlobalVariable)getApplication()).setLoginAutentificate(user_auth);

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.frm_layaout,new HomePage()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

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
                        selectedFragment=new CabinetLoginPage();
                    }else{
                        selectedFragment=new CabinetPage();
                    }
                    break;
                case R.id.navigation_maps:

                    selectedFragment=new MapsPage();
                    break;
            }
            if (selectedFragment!=null)
                getSupportFragmentManager().beginTransaction().replace(R.id.frm_layaout,selectedFragment).commit();
            return true;
        }
    };
}
