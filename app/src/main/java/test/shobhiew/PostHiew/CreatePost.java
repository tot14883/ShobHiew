package test.shobhiew.PostHiew;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import test.shobhiew.LocaleHelper;
import test.shobhiew.MainActivity;
import test.shobhiew.R;

public class CreatePost extends AppCompatActivity {
    private Toolbar toolbar;
    private Button btn_create;
    private TextView textStart,textEnd;
    private EditText edt_topic,edt_detail;
    private TextView text_type,text_brand;
    private static final String TAG = "CreatePost";
    private String Okey;
    private String Yes;
    private SharedPreferences mSharedPreferences;
    private Context mContext;
    private String Languages;
    private String Province;
    private static Resources resources;
    private TextView AddLocation;
    private DatePickerDialog.OnDateSetListener mDateSetListener, mDateSetListener1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Okey = getIntent().getExtras().getString("OKEY");

        mContext = getApplicationContext();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        Languages = mSharedPreferences.getString(getString(R.string.setting_languages),"Thai");
        if(Languages.equals("Thai")){
            mContext = LocaleHelper.setLocale(CreatePost.this,"th");
            resources = mContext.getResources();
        }
        else if(Languages.equals("English")){
            mContext = LocaleHelper.setLocale(CreatePost.this,"en");
            resources = mContext.getResources();
        }



        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = mPreferences.edit();

        final String Type = mPreferences.getString("MY_TYPE","");
        final String Brand  = mPreferences.getString("MY_BRAND","");

        final String check = getResources().getString(R.string.SubType);
        final String brand_check = getResources().getString(R.string.SubBrand);


        text_type = (TextView) findViewById(R.id.text_type);
        text_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent type = new Intent(CreatePost.this,TypeProduct.class);
                startActivity(type);
                finish();
            }
        });




        text_brand = (TextView) findViewById(R.id.text_brand);
        text_brand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(Okey.equals("1")){
                   Toast.makeText(CreatePost.this, "Please, Selection Product Type", Toast.LENGTH_SHORT).show();
               }
               else if(!Okey.equals("1")){
                   Intent intent = new Intent(CreatePost.this,BrandProduct.class);
                   intent.putExtra("Brand",Type);
                   startActivity(intent);
                   finish();
               }
            }
        });

        if(Okey.equals("1"))
        {
            text_type.setText(check);
        }
        else if(Okey.equals("2")){
            if(Type.equals(check)){
                text_type.setText(check);
                text_brand.setText(brand_check);
            }
            else if(!Type.equals(check)){
                text_type.setText(Type);
                text_brand.setText(brand_check);
            }
        }
        else if(Okey.equals("3")){
            text_type.setText(Type);
            text_brand.setText(Brand);
        }

        edt_topic = (EditText) findViewById(R.id.Name_Product);
        edt_detail = (EditText) findViewById(R.id.Detail_Product);

        AddLocation = (TextView) findViewById(R.id.add_location);
        AddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(CreatePost.this,Location.class);
               startActivity(intent);
            }
        });

        textStart = (TextView) findViewById(R.id.start_date_text_pro);
        textStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                final int[] day = {cal.get(Calendar.DAY_OF_MONTH)};

                DatePickerDialog dialog = new DatePickerDialog(
                        CreatePost.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        ,mDateSetListener
                        ,year,month, day[0]);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();


            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month +1;
                String date = day +"/"+ month + "/" + year;
                textStart.setText(date);

            }
        };
        textEnd = (TextView) findViewById(R.id.end_date_text_pro);
        textEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        CreatePost.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        ,mDateSetListener1
                        ,year,month,day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        mDateSetListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month +1;
                String date = day +"/"+ month + "/" + year;
                textEnd.setText(date);

            }
        };


        btn_create = (Button) findViewById(R.id.btn_create_post);

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 if(edt_topic.length() == 0){
                     edt_topic.setError("Field is empty");
                 }
                 if(edt_detail.length() == 0){
                     edt_detail.setError("Field is empty");
                 }
                 if(Type.equals(check)){
                     Toast.makeText(CreatePost.this, "Please, Selection Product Type", Toast.LENGTH_SHORT).show();
                 }
                 if(Brand.equals(brand_check)){
                     Toast.makeText(CreatePost.this, "Please, Selection Product Brand", Toast.LENGTH_SHORT).show();

                 }

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            Intent intent = new Intent(CreatePost.this, PostHiew.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
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
