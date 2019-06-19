package edi.md.pecomobile.home_screen;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import edi.md.pecomobile.R;

public class PricePage extends Fragment {
    View rootViewAdmin;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootViewAdmin!=null){
            if((ViewGroup)rootViewAdmin.getParent()!=null)
                ((ViewGroup)rootViewAdmin.getParent()).removeView(rootViewAdmin);
            return rootViewAdmin;
        }
        rootViewAdmin = inflater.inflate(R.layout.fragment_price, container, false);


        return rootViewAdmin;
    }
}
