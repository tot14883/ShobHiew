package test.shobhiew;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

public class Setting extends PreferenceActivity {

    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content,new SettingFramgment()).commit();
        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = mPreferences.edit();
    }
    public static class SettingFramgment extends PreferenceFragment{

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.setting_screen);


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
            editor.putString("COUNT","2");
            editor.apply();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
