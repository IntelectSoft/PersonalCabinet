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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import edi.md.pecomobile.R;

public class AssortimentList extends Fragment  {
    View rootViewAdmin;
    ListView LW_Product;
    SimpleAdapter adapterProductList;
    ArrayList<HashMap<String,Object>> arrayProductList;
    String str;

    public void getBundle(){
        Bundle args = getArguments();
        assert args != null;
        str = args.getString("AssortimentList");
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootViewAdmin = inflater.inflate(R.layout.fragment_asortment_contract, container, false);
        LW_Product=rootViewAdmin.findViewById(R.id.LW_assortmentContract);
        arrayProductList =new ArrayList<>();

        adapterProductList = new SimpleAdapter(getActivity(),arrayProductList,R.layout.array_list_of_assortment_contract,new String[]{"Name","Price","Group","Discount"}, new int[]{R.id.txtNameAssortmentContract,R.id.txtPriceAssortmentContract,R.id.txtGroupNameAssortmentContract,R.id.txtDiscountAssortmentContract});

        getBundle();
        if (!str.equals("[]")){
            try {
                JSONArray list_of_products = new JSONArray(str);
                for (int i=0; i<list_of_products.length(); i++){
                    JSONObject product = list_of_products.getJSONObject(i);
                    String NameProduct = product.getString("Name");
                    String GroupProduct = product.getString("Group");
                    String IDProduct = product.getString("ID");
                    String PriceProduct = product.getString("Price");
                    String CodeProduct = product.getString("Code");
                    double DiscountProduct= product.getDouble("Discount");

                    HashMap<String,Object> productHashMap = new HashMap<>();
                    productHashMap.put("Name",NameProduct);
                    productHashMap.put("Group",GroupProduct);
                    productHashMap.put("ID",IDProduct);
                    productHashMap.put("Price",PriceProduct+ " lei");
                    productHashMap.put("Code",CodeProduct);
                    productHashMap.put("Discount",DiscountProduct + " lei");
                    arrayProductList.add(productHashMap);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            LW_Product.setAdapter(adapterProductList);
        }
        int i = 9;
        return rootViewAdmin;
    }
}
