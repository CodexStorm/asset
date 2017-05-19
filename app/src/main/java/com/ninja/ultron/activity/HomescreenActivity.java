package com.ninja.ultron.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ninja.ultron.R;
import com.ninja.ultron.adapter.HomeScreenViewPagerAdapter;


/**
 * Created by Prabhu Sivanandam on 18-May-17.
 */

public class HomescreenActivity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    HomeScreenViewPagerAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);
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
