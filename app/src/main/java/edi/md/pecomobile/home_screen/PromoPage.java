package edi.md.pecomobile.home_screen;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import edi.md.pecomobile.R;

public class PromoPage extends Fragment {
    View rootViewAdmin;
    ListView promoList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootViewAdmin = inflater.inflate(R.layout.fragment_promo, container, false);
        promoList=rootViewAdmin.findViewById(R.id.LW_promo);

        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        HashMap<String, String> map;

        for (int i=0; i<25;i++){
            map = new HashMap<>();
            map.put("Name", "Мурзик"+i);
            map.put("Tel", "495 501-3545"+i);
            arrayList.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(getContext(), arrayList, android.R.layout.simple_list_item_2,
                new String[]{"Name", "Tel"},
                new int[]{android.R.id.text1, android.R.id.text2});
        promoList.setAdapter(adapter);

        return rootViewAdmin;
    }
}
