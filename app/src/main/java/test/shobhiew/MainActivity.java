package test.shobhiew;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;

import test.shobhiew.Account.AccountLogin;
import test.shobhiew.Account.fragment_account;
import test.shobhiew.Deposit.fragmentDeposit;
import test.shobhiew.Home.fragme_home;
import test.shobhiew.favorite.fragment_favorite;
import test.shobhiew.chat.fragment_chat;

public class MainActivity extends AppCompatActivity {
    DrawerLayout mDrawerLayout;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private SharedPreferences mSharedPreferences;
    private Context mContext;
    private String Languages;
    private String Province;
    private static Resources resources;
    private SharedPreferences.Editor mEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
       // mContext = getApplicationContext();

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();
        Languages = mSharedPreferences.getString(getString(R.string.setting_languages), "Thai");
        if (Languages.equals("English")) {
            LocaleHelper.setLocale(MainActivity.this, "en");
        } else if (Languages.equals("Thai")) {
           LocaleHelper.setLocale(MainActivity.this, "th");
        }
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);


        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragment_container, new fragme_home()).commit();


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container,new
                            fragme_home()).commit();
                    return true;
                case R.id.navigation_deposit:
                    FragmentTransaction fragmentTransaction3 = mFragmentManager.beginTransaction();
                    fragmentTransaction3.replace(R.id.fragment_container,new
                            fragmentDeposit()).commit();
                    return true;
                case R.id.navigation_favorite:
                    FragmentTransaction fragmentTransaction1 = mFragmentManager.beginTransaction();
                    fragmentTransaction1.replace(R.id.fragment_container,new
                            fragment_favorite()).commit();
                    return true;
                case R.id.navigation_chat:
                    FragmentTransaction fragmentTransaction2 = mFragmentManager.beginTransaction();
                    fragmentTransaction2.replace(R.id.fragment_container,new
                            fragment_chat()).commit();
                    return true;
                case R.id.navigation_account:
                    CheckUser();
                    return true;
            }
            return false;
        }
    };



    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        if(mSharedPreferences.getString("COUNT","").equals("2")){
            Intent intent = getIntent();
            startActivity(intent);
            finish();
            mEditor.putString("COUNT","1");
            mEditor.apply();
        }
        else if( !mSharedPreferences.getString("COUNT","").equals("2") || mSharedPreferences.getString("COUNT","").isEmpty() ){
            mEditor.putString("COUNT","1");
            mEditor.apply();
        }
    }

    public void CheckUser(){
        if(mAuth.getCurrentUser() == null ){
            FragmentTransaction fragmentTransaction3 = mFragmentManager.beginTransaction();
            fragmentTransaction3.replace(R.id.fragment_container,new
                    fragment_account()).commit();
        }
        else if(mAuth.getCurrentUser() != null){
            FragmentTransaction fragmentTransaction3 = mFragmentManager.beginTransaction();
            fragmentTransaction3.replace(R.id.fragment_container,new
                    AccountLogin()).commit();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }
}
