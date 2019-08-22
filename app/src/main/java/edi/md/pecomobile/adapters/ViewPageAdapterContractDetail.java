package edi.md.pecomobile.adapters;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import edi.md.pecomobile.ContractDetail.AssortimentList;
import edi.md.pecomobile.ContractDetail.CardList;

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
