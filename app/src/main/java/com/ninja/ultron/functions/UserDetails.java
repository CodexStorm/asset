package com.ninja.ultron.functions;

import android.content.Context;
import android.content.SharedPreferences;

import com.ninja.ultron.constant.Constants;

public class UserDetails {

    public static void setUserLoggedIn(Context context,Boolean loginStatus){
        SharedPreferences settings = context.getSharedPreferences(Constants.USER_PREFERENCE,0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("userLoggedIn",loginStatus);
        editor.commit();
    }

    public static Boolean isUserLoggedIn(Context context){
        SharedPreferences settings = context.getSharedPreferences(Constants.USER_PREFERENCE,0);
        return (settings.getBoolean("userLoggedIn", false));
    }

    public static Boolean isAdmin(Context context) {
        SharedPreferences settings = context.getSharedPreferences(Constants.USER_PREFERENCE, 0);
        return (settings.getBoolean("isAdmin", false));
    }

    public static void setOfflineMode(Context context, Boolean procurementOfflineMode) {
        SharedPreferences settings = context.getSharedPreferences(Constants.USER_PREFERENCE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("procurementOfflineMode", procurementOfflineMode);
        editor.commit();
    }

    public static Boolean isOfflineMode(Context context) {
        SharedPreferences settings = context.getSharedPreferences(Constants.USER_PREFERENCE, 0);
        return (settings.getBoolean("procurementOfflineMode", false));
    }

    public static void setUserName(Context context, String userName) {
        SharedPreferences settings = context.getSharedPreferences(Constants.USER_PREFERENCE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("userName", userName);
        editor.commit();
    }

    public static String getUserName(Context context) {
        SharedPreferences settings = context.getSharedPreferences(Constants.USER_PREFERENCE, 0);
        return (settings.getString("userName", ""));
    }

    public static void setUserPassword(Context context, String userPassword) {
        SharedPreferences settings = context.getSharedPreferences(Constants.USER_PREFERENCE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("userPassword", userPassword);
        editor.commit();
    }

    public static String getUserPassword(Context context) {
        SharedPreferences settings = context.getSharedPreferences(Constants.USER_PREFERENCE, 0);
        return (settings.getString("userPassword", ""));
    }

    public static long getCurrentServerDate(Context context) {
        SharedPreferences settings = context.getSharedPreferences(Constants.USER_PREFERENCE, 0);
        return settings.getLong("currentServerDate", 0);

    }

    public static void setServerTimeDifference(Context context, long serverTimeDifference) {
        SharedPreferences settings = context.getSharedPreferences(Constants.USER_PREFERENCE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong("serverTimeDifference", serverTimeDifference);
        editor.commit();
    }

    public static long getServerTimeDifference(Context context) {
        SharedPreferences settings = context.getSharedPreferences(Constants.USER_PREFERENCE, 0);
        return settings.getLong("serverTimeDifference", 0);

    }

    public static void serAsgardUserId(Context context, int asgardUserId) {
        SharedPreferences settings = context.getSharedPreferences(Constants.USER_PREFERENCE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("asgardUserId", asgardUserId);
        editor.commit();
    }

    public static int getAsgardUserId(Context context) {
        SharedPreferences settings = context.getSharedPreferences(Constants.USER_PREFERENCE, 0);
        return settings.getInt("asgardUserId", 0);

    }

    public static void setUserReportingLogin(Context context, Boolean loggedInStatus) {
        SharedPreferences settings = context.getSharedPreferences(Constants.USER_PREFERENCE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("userReportingLoggedInStatus", loggedInStatus);
        editor.commit();
    }

    public static Boolean idUserReportingLoggedin(Context context) {
        SharedPreferences settings = context.getSharedPreferences(Constants.USER_PREFERENCE, 0);
        return (settings.getBoolean("userReportingLoggedInStatus", false));
    }

    public static void setRoleType(Context context, int roleType) {
        SharedPreferences settings = context.getSharedPreferences(Constants.USER_PREFERENCE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("roleType", roleType);
        editor.commit();
    }

    public static int getRoleType(Context context) {
        SharedPreferences settings = context.getSharedPreferences(Constants.USER_PREFERENCE, 0);
        return settings.getInt("roleType", 0);
    }

    public static void setMyAssetList(Context context,String myAssetList) {
        SharedPreferences settings = context.getSharedPreferences(Constants.USER_PREFERENCE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("myAssetList", myAssetList);
        editor.commit();
    }

    public static int getMyAssetList(Context context) {
        SharedPreferences settings = context.getSharedPreferences(Constants.USER_PREFERENCE, 0);
        return settings.getInt("roleType", 0);
    }

}
