package com.ninja.ultron.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Prabhu Sivanandam on 18-May-17.
 */

public class HomeScreenViewPagerAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> fragments=new ArrayList<>();
    ArrayList<String> tabTitles=new ArrayList<>();

    public void addFragments(Fragment fragment,String tabTitle)
    {
        this.fragments.add(fragment);
        this.tabTitles.add(tabTitle);
    }

    public HomeScreenViewPagerAdapter(FragmentManager manager)
    {
        super(manager);
    }
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }
}
