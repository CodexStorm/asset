package com.ninja.ultron.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ninja.ultron.Fragments.AssetDetailsFragment;
import com.ninja.ultron.Fragments.InitiateTransferFragment;
import com.ninja.ultron.Fragments.MyAssetsFragment;
import com.ninja.ultron.Fragments.PendingRequestsFragment;
import com.ninja.ultron.R;
import com.ninja.ultron.adapter.HomeScreenViewPagerAdapter;
import com.ninja.ultron.entity.CodeDecodeEntity;
import com.ninja.ultron.functions.CommonFunctions;

import java.util.List;


/**
 * Created by Prabhu Sivanandam on 18-May-17.
 */

public class HomescreenActivity extends AppCompatActivity{

    String id;
    String name;
    String toName;
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
    }


}
