package com.ninja.ultron.entity;

/**
 * Created by ompra on 5/3/2017.
 */

public class NavDrawerItem {
    private boolean mShowNotify;
    private String mTitle;

    public NavDrawerItem() {
    }

    public NavDrawerItem(boolean mShowNotify, String mTitle) {
        this.mShowNotify = mShowNotify;
        this.mTitle = mTitle;
    }

    public boolean ismShowNotify() {
        return mShowNotify;
    }

    public void setmShowNotify(boolean mShowNotify) {
        this.mShowNotify = mShowNotify;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }
}
