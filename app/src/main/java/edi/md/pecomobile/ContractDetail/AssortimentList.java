package edi.md.pecomobile.ContractDetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import edi.md.pecomobile.R;

public class AssortimentList extends Fragment {
    View rootViewAdmin;
    ListView LW_Product;
    SimpleAdapter adapterProductList;
    ArrayList<HashMap<String,Object>> arrayProductList =new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootViewAdmin = inflater.inflate(R.layout.fragment_asortment_contract, container, false);
        LW_Product=rootViewAdmin.findViewById(R.id.LW_assortmentContract);
        adapterProductList = new SimpleAdapter(getActivity(),arrayProductList,R.layout.array_list_contracts,new String[]{"Code","Name"},new int[]{R.id.txtName_of_price,R.id.txtPrice_of_price});

        String pdf = getArguments().getString("pdf");
        int i = 9;
        return rootViewAdmin;
    }
}
