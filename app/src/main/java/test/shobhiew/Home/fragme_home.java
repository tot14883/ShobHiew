package test.shobhiew.Home;


import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import test.shobhiew.LocaleHelper;
import test.shobhiew.MainActivity;
import test.shobhiew.PostDetail;
import test.shobhiew.PostRecycleAdapter;
import test.shobhiew.R;

public class fragme_home extends Fragment {
    View x;
    TextView Acc_Text,Candy_Text,Supple_Text;
    private SharedPreferences mSharedPreferences;
    private String Languages;

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    PostRecycleAdapter adapter;
    List<PostDetail> postDetail;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        x =  inflater.inflate(R.layout.activity_fragme_home,container,false);

        Acc_Text = (TextView) x.findViewById(R.id.Acc_text);
        Candy_Text = (TextView) x.findViewById(R.id.candy_text);
        Supple_Text = (TextView) x.findViewById(R.id.supple_food_text);

        mRecyclerView = (RecyclerView) x.findViewById(R.id.recycle_postHiew);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        adapter = new PostRecycleAdapter(postDetail);
        mRecyclerView.setAdapter(adapter);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Languages = mSharedPreferences.getString(getString(R.string.setting_languages), "Thai");
        if (Languages.equals("Thai")) {
           Acc_Text.setText("เครื่องประดับ");
           Acc_Text.setTextSize(10);
           Candy_Text.setText("ขนมเเละเครื่องดื่ม");
           Candy_Text.setTextSize(10);
           Supple_Text.setText("อาหารเสริม");
           Supple_Text.setTextSize(10);
        }
      return x;

    }
}
