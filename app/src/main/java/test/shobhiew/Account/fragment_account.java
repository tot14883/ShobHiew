package test.shobhiew.Account;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import test.shobhiew.Login.MainLogin;
import test.shobhiew.R;

public class fragment_account extends Fragment {
    View x;
    Button btnLogin;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        x =  inflater.inflate(R.layout.activity_fragment_account,container,false);
        btnLogin = (Button) x.findViewById(R.id.btn_login_app);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainLogin.class);
                startActivity(intent);
            }
        });
        return x;
    }
}
