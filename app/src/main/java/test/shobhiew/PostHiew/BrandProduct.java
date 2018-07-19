package test.shobhiew.PostHiew;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;

import test.shobhiew.R;

public class BrandProduct extends AppCompatActivity {
    private String Okey;
    private Toolbar toolbar;
    private ListView listView;
    private  ArrayAdapter<String> adapter;
    private String[] brand_product;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_product);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Okey = getIntent().getExtras().getString("Brand");


        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        final String[] type_product = getResources().getStringArray(R.array.type_product);

        ListView listView = (ListView) findViewById(R.id.list_brand_product);

        if(Okey.equals(type_product[0])) {
            brand_product = getResources().getStringArray(R.array.Shoes);
        }
        else if(Okey.equals(type_product[1])) {
            brand_product = getResources().getStringArray(R.array.bag);
        }
        else if(Okey.equals(type_product[2])) {
            brand_product = getResources().getStringArray(R.array.Cosmetic);
        }
        else if(Okey.equals(type_product[3])) {
            brand_product = getResources().getStringArray(R.array.supplementary);
        }
        else if(Okey.equals(type_product[4])) {
            brand_product = getResources().getStringArray(R.array.Accessory);
        }
        else if(Okey.equals(type_product[5])) {
            brand_product = getResources().getStringArray(R.array.Candy);
        }
        else if(Okey.equals(type_product[6])) {
            brand_product = getResources().getStringArray(R.array.Perfume);
        }
        else if(Okey.equals(type_product[7])) {
            brand_product = getResources().getStringArray(R.array.Clothes);
        }
        else if(Okey.equals(type_product[8])) {
            brand_product = getResources().getStringArray(R.array.Toy);
        }
        else if(Okey.equals(type_product[9])) {
            brand_product = getResources().getStringArray(R.array.stationary);
        }
        else if(Okey.equals(type_product[10])) {
            brand_product = getResources().getStringArray(R.array.Book);
        }
        else if(Okey.equals(type_product[11])) {
            brand_product = getResources().getStringArray(R.array.sport);
        }
        else if(Okey.equals(type_product[12])) {
            brand_product = getResources().getStringArray(R.array.Computer);
        }
        else if(Okey.equals(type_product[13])) {
            brand_product = getResources().getStringArray(R.array.Camera);
        }
        else{
            brand_product = getResources().getStringArray(R.array.totalBrand);
        }
        Arrays.sort(brand_product);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, brand_product);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mEditor.putString("MY_BRAND",brand_product[i]);
                mEditor.apply();
                if(brand_product[i].equals("other") || brand_product[i].equals("อื่นๆ")) {
                    Intent intent = new Intent(BrandProduct.this, AddOther.class);
                    intent.putExtra("Hint","Brand ?");
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(BrandProduct.this, CreatePost.class);
                    intent.putExtra("OKEY", "3");
                    startActivity(intent);
                    finish();
                }
            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            Intent intent = new Intent(BrandProduct.this, CreatePost.class);
            intent.putExtra("OKEY","2");
            startActivity(intent);
            finish();
        }
        if(item.getItemId() == R.id.search_brand){
            Intent intent = new Intent(BrandProduct.this,SearchBrand.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
