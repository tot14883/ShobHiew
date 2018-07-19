package test.shobhiew.Account;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

import test.shobhiew.LocaleHelper;
import test.shobhiew.Login.MainLogin;
import test.shobhiew.MainActivity;
import test.shobhiew.PostHiew.PostHiew;
import test.shobhiew.R;
import test.shobhiew.Setting;

public class AccountLogin extends Fragment {
    View x;
    private Button btn_signout;
    private Button btn_post;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private ImageView setting;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        x =  inflater.inflate(R.layout.activity_account_login,container,false);
        FacebookSdk.sdkInitialize(getActivity());
        AppEventsLogger.activateApp(getActivity());
        mAuth = FirebaseAuth.getInstance();
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };

        btn_post = (Button) x.findViewById(R.id.btn_post_product);
        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PostHiew.class);
                startActivity(intent);
            }
        });
        btn_signout = (Button) x.findViewById(R.id.btn_signout);
        btn_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("ล๊อคอิน");
                dialog.setCancelable(true);
                dialog.setMessage("คุณต้องการออกจากการล๊อคอินหรือไม่?");
                dialog.setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mAuth.signOut();
                        LoginManager.getInstance().logOut();
                        Intent mIntent = new Intent(getActivity(),MainActivity.class);
                        getActivity().finish();
                        startActivity(mIntent);
                        Toast.makeText(getActivity(),"ออกจากระบบเรียบร้อย",Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setNegativeButton("ไม่ใช่", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                dialog.show();
            }
        });

        setting = (ImageView) x.findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Setting.class);
                startActivity(intent);
                getActivity().finish();

            }
        });
        return x;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }

}
