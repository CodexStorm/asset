package com.ninja.ultron.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ninja.ultron.Fragments.MyAssetsFragment;
import com.ninja.ultron.Fragments.PendingRequestsFragment;
import com.ninja.ultron.R;
import com.ninja.ultron.adapter.HomeScreenViewPagerAdapter;
import com.ninja.ultron.functions.CommonFunctions;


/**
 * Created by Prabhu Sivanandam on 18-May-17.
 */

public class AssetActivity extends AppCompatActivity{
    Toolbar toolbar;
    TabLayout tabLayout;
    TextView title;
    ViewPager viewPager;
    Toolbar mainActivityBar;
    HomeScreenViewPagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_asset);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mainActivityBar = (Toolbar) findViewById(R.id.mainActivityBar);
        title=(TextView)findViewById(R.id.title);
        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        viewPager=(ViewPager)findViewById(R.id.viewPager);

        setSupportActionBar(toolbar);
        CommonFunctions.clearLocalPreference(getApplicationContext());

        adapter=new HomeScreenViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragments(new MyAssetsFragment(),"My Assets");
        adapter.addFragments(new PendingRequestsFragment(),"Pending Requests");
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }

            @Override
            public void onPageSelected(int position) {


            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }
        });




        tabLayout.setupWithViewPager(viewPager);
        viewPager.setVisibility(View.VISIBLE);
    }


}
