package edi.md.pecomobile.home_screen;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edi.md.pecomobile.R;

public class NewsPage extends Fragment {
    View rootViewAdmin;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootViewAdmin!=null){
            if((ViewGroup)rootViewAdmin.getParent()!=null)
                ((ViewGroup)rootViewAdmin.getParent()).removeView(rootViewAdmin);
            return rootViewAdmin;
        }
        rootViewAdmin = inflater.inflate(R.layout.fragment_news, container, false);


        return rootViewAdmin;
    }
}
