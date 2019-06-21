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

public class CardList extends Fragment {
    View rootViewAdmin;
    ListView LW_Cards;
    SimpleAdapter adapterCardstList;
    ArrayList<HashMap<String,Object>> arrayCardsList;
    String str;

    public void getBundle(){
        Bundle args = getArguments();
        assert args != null;
        str = args.getString("CardList");
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootViewAdmin = inflater.inflate(R.layout.fragment_card_contract, container, false);
        LW_Cards  =rootViewAdmin.findViewById(R.id.LW_CardsList_Contract);
        arrayCardsList =new ArrayList<>();

        adapterCardstList = new SimpleAdapter(getActivity(),arrayCardsList,R.layout.array_list_of_cards_contract,new String[]{"Name","Code","Balance","LimitType"},new int[]{R.id.txtNameCardContract,R.id.txtCodCardContract,R.id.txtBalanceCardContract,R.id.txtLimitTypeCardContract});

        getBundle();
        if (!str.equals("[]")){
            try {
                JSONArray list_of_cards = new JSONArray(str);
                for (int i=0; i<list_of_cards.length(); i++){
                    JSONObject card = list_of_cards.getJSONObject(i);
                    String NameCard = card.getString("Name");
                    String CodeCard = card.getString("Code");
                    int LimitTypeCard = card.getInt("LimitType");
                    double BalanceAccountCard = card.getDouble("BalanceAccountCard");
                    boolean ActiveCard = card.getBoolean("IsActive");

                    String Limit="--";
                    if(LimitTypeCard==1){
                        Limit = "Cant";
                    }else{
                        Limit = "Suma";
                    }
                    String Activ="--";
                    if(ActiveCard){
                        Activ = "Activ";
                    }else{
                        Activ = "Inactiv";
                    }
                    HashMap<String,Object> productHashMap = new HashMap<>();
                    productHashMap.put("Name",CodeCard+" "+ NameCard);
                    productHashMap.put("Code",CodeCard);
                    productHashMap.put("LimitType",Limit);
                    productHashMap.put("Balance",BalanceAccountCard+ " lei");
                    productHashMap.put("IsActive",Activ);
                    arrayCardsList.add(productHashMap);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            LW_Cards.setAdapter(adapterCardstList);
        }
        int i = 9;
        return rootViewAdmin;
    }
}
