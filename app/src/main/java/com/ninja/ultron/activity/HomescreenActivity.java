package com.ninja.ultron.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ninja.ultron.R;
import com.ninja.ultron.adapter.HomeScreenViewPagerAdapter;
import com.ninja.ultron.entity.CodeDecodeEntity;
import com.ninja.ultron.functions.CommonFunctions;

import java.util.List;


/**
 * Created by Prabhu Sivanandam on 18-May-17.
 */

public class HomescreenActivity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    HomeScreenViewPagerAdapter adapter;
    List<CodeDecodeEntity> myAssetList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_homescreen);
        CommonFunctions.clearLocalPreference(getApplicationContext());
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        adapter=new HomeScreenViewPagerAdapter(getSupportFragmentManager());
        //add the fragments with titles here

        adapter.addFragments(new MyAssetsFragment(),"Tab1");
        adapter.addFragments(new fragment2(),"Tab 2");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }


}
