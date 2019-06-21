package edi.md.pecomobile.adapters;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edi.md.pecomobile.ContractDetail.AssortimentList;
import edi.md.pecomobile.ContractDetail.CardList;
import edi.md.pecomobile.HomePage;
import edi.md.pecomobile.NetworkUtils.ContractInfo.ProductsList;
import edi.md.pecomobile.home_screen.NewsPage;
import edi.md.pecomobile.home_screen.PricePage;
import edi.md.pecomobile.home_screen.PromoPage;

public class ViewPageAdapterContractDetail extends FragmentPagerAdapter {
    String products,cards;

    public ViewPageAdapterContractDetail(FragmentManager fm,String listProducts,String listCards) {
        super(fm);
        this.products=listProducts;
        this.cards = listCards;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            Bundle bundle = new Bundle();
            AssortimentList assortment = new AssortimentList();
            bundle.putString("AssortimentList", products);
            assortment.setArguments(bundle);
            return assortment;
        } else {
            Bundle bundle = new Bundle();
            CardList promo = new CardList();
            bundle.putString("CardList", cards);
            promo.setArguments(bundle);
            return promo;
        }
    }
    @Override
    public int getCount() {
        return 2;
    }
}
