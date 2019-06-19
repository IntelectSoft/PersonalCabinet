package edi.md.pecomobile.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import edi.md.pecomobile.HomePage;
import edi.md.pecomobile.home_screen.NewsPage;
import edi.md.pecomobile.home_screen.PricePage;
import edi.md.pecomobile.home_screen.PromoPage;

public class ViewPageAdapterHome extends FragmentPagerAdapter {

    public ViewPageAdapterHome(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        if(position==0) {
            NewsPage nes = new NewsPage();
            return nes;
        }else if(position==1){
            PromoPage promo = new PromoPage();
            return promo;
        }
        else{
            PricePage set = new PricePage();
            return set;
        }
    }


    @Override
    public int getCount() {
        return 3;
    }
}
