package edi.md.pecomobile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edi.md.pecomobile.LoginPage.FizicTab;
import edi.md.pecomobile.LoginPage.JuridicTab;

public class CabinetLoginPage extends Fragment {
    Fragment Fizic, juridic;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootViewAdmin = inflater.inflate(R.layout.fragment_cabinet_login, container, false);
//Adden new branch
        Fizic = new FizicTab();
        juridic = new JuridicTab();
        TabLayout tabLayout = (TabLayout) rootViewAdmin.findViewById(R.id.tb_login_items);

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.vwPage_login_screen,Fizic).commit();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position){
                    case 0 :
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.vwPage_login_screen,Fizic).commit();
                        break;
                    case 1 :
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.vwPage_login_screen,juridic).commit();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return rootViewAdmin;
    }
}
