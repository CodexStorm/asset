package com.ninja.ultron.functions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ninja.ultron.activity.AssetActivity;
import com.ninja.ultron.activity.HomescreenActivity;
import com.ninja.ultron.activity.LoginActivity;
import com.ninja.ultron.activity.SplashScreen;

public class StartIntent {

    public static void startLoginActivity(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

    public static void HomescreenActivity(Activity act) {
        Intent myIntent = new Intent(act, HomescreenActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        act.startActivity(myIntent);
        act.finish();
    }

    public static void MyAssetActivity(Activity activity){
        Intent myIntent = new Intent(activity, AssetActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(myIntent);
    }

    public static void startSplashScreen(Activity activity) {
        Intent myIntent = new Intent(activity, SplashScreen.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//FLAG_ACTIVITY_NEW_TASK
        activity.startActivity(myIntent);
        activity.finish();
    }

    public static void Attendance(Activity activity){
        Intent myIntent = new Intent("com.ninja.flash.labourattendance.ATTENDANCE");
        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(myIntent);

    }

    public static void exitApplication(Activity act) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        act.startActivity(intent);
    }

    public static void commonStartActivity(Activity activity, Class nextActivity, Bundle bundle, boolean activityResultRequired, int activityResultCode, boolean finishActivityRequried) {
        Intent intent = new Intent(activity, nextActivity);
        if (bundle != null)
            intent.putExtras(bundle);
        if (activityResultRequired) {
            activity.startActivityForResult(intent, activityResultCode);
        } else
            activity.startActivity(intent);
        if (finishActivityRequried)
            activity.finish();
    }

    public static void commonStartActivity(Activity activity, Class className, Bundle bundle) {
        Intent intent = new Intent(activity, className);
        if (bundle != null)
            intent.putExtras(bundle);
        activity.startActivity(intent);
    }

}
