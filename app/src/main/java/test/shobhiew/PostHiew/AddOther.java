package test.shobhiew.PostHiew;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import test.shobhiew.R;

public class AddOther extends Activity {
    private EditText edt_other;
    private Button btn_submit;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_other);
        final String Hint = getIntent().getExtras().getString("Hint");

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        edt_other = (EditText) findViewById(R.id.edt_other_product);
        edt_other.setHint(Hint);

        btn_submit = (Button) findViewById(R.id.btn_other_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 mEditor.putString("MY_BRAND",edt_other.getText().toString());
                 mEditor.apply();
                if(Hint.equals("Brand ?")){
                     Intent intent = new Intent(AddOther.this, CreatePost.class);
                     intent.putExtra("OKEY", "3");
                     startActivity(intent);
                     finish();
                 }
            }
        });

    }
}
