package edi.md.pecomobile.LoginPage;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import edi.md.pecomobile.NetworkUtils.AutentificateUser;
import edi.md.pecomobile.R;

public class FizicTab extends Fragment {
    final String BASE_URL = "http://178.168.80.129:1915";
    Button sign_in;
    EditText telephone,passwords;
    String SID;
    ProgressBar pgBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootViewAdmin = inflater.inflate(R.layout.fragment_fizic_tab, container, false);

        telephone= rootViewAdmin.findViewById(R.id.username_fizic);
        passwords=rootViewAdmin.findViewById(R.id.password_fizic);
        sign_in = rootViewAdmin.findViewById(R.id.login);
        pgBar=rootViewAdmin.findViewById(R.id.progressBarFizic);

        pgBar.setVisibility(View.INVISIBLE);

        final AutentificateUser user = new AutentificateUser();
        user.setAuthType(0);
        user.setPassword(passwords.getText().toString());
        user.setUser(telephone.getText().toString());

        return rootViewAdmin;
    }

}
