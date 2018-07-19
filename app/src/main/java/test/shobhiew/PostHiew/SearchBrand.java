package test.shobhiew.PostHiew;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import test.shobhiew.R;

public class SearchBrand extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView listView;
    private String[] type_brand;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private ArrayAdapter<String> adapter;
    private String search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_brand);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        search = getResources().getString(R.string.Search);

        listView = (ListView) findViewById(R.id.list_brand_product_search);
        type_brand = getResources().getStringArray(R.array.totalBrand);
        Arrays.sort(type_brand);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,type_brand);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                 //mEditor.putString("MY_BRAND",type_brand[i]);


                  //mEditor.apply();
                String text = adapterView.getItemAtPosition(i).toString();
                mEditor.putString("MY_BRAND",text);
                mEditor.apply();
                if(text.equals("other") || text.equals("อื่นๆ")) {
                    Intent intent = new Intent(SearchBrand.this, AddOther.class);
                    intent.putExtra("Hint","Brand ?");
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(SearchBrand.this, CreatePost.class);
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

        MenuItem searchItem = menu.findItem(R.id.search_brand);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            Intent intent = new Intent(SearchBrand.this, CreatePost.class);
            intent.putExtra("OKEY","2");
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
