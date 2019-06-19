package edi.md.pecomobile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edi.md.pecomobile.adapters.ViewPageAdapterHome;
import edi.md.pecomobile.home_screen.NewsPage;
import edi.md.pecomobile.home_screen.PricePage;
import edi.md.pecomobile.home_screen.PromoPage;

public class HomePage extends Fragment {
    private ViewPageAdapterHome mSectionsPagerAdapter;
    private ViewPager mViewPager;
    Fragment News,Promo,Price;
    TabLayout tabLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootViewAdmin = inflater.inflate(R.layout.fragment_home, container, false);
        News = new NewsPage();
        Promo = new PromoPage();
        Price = new PricePage();

        tabLayout = (TabLayout) rootViewAdmin.findViewById(R.id.tb_home_items);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.vwPage_home_screen,News).commit();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position){
                    case 0 :
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.vwPage_home_screen,News).commit();
                        break;
                    case 1 :
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.vwPage_home_screen,Promo).commit();
                        break;
                    case 2 :
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.vwPage_home_screen,Price).commit();
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
