package test.shobhiew.PostHiew;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import test.shobhiew.Login.MainLogin;
import test.shobhiew.MainActivity;
import test.shobhiew.R;

public class PostHiew extends AppCompatActivity {
    private Toolbar toolbar;
    private FloatingActionButton floating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_hiew);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        floating = (FloatingActionButton) findViewById(R.id.Add_Post);
        floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent create = new Intent(PostHiew.this,CreatePost.class);
                    create.putExtra("OKEY","1");
                    startActivity(create);
                    finish();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            Intent intent = new Intent(PostHiew.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
