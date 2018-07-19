package test.shobhiew.PostHiew;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import test.shobhiew.R;

public class TypeProduct extends AppCompatActivity {
    private Toolbar toolbar;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_product);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        final String[] type_product = getResources().getStringArray(R.array.type_product);
        ListView listView = (ListView) findViewById(R.id.list_type_product);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,type_product);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              mEditor.putString("MY_TYPE",type_product[i]);
              mEditor.apply();
                  Intent intent = new Intent(TypeProduct.this, CreatePost.class);
                  intent.putExtra("OKEY", "2");
                  startActivity(intent);
                  finish();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            Intent intent = new Intent(TypeProduct.this, CreatePost.class);
            intent.putExtra("OKEY","1");
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
