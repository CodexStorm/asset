package com.ninja.ultron.functions;

import android.app.Activity;
import android.content.Intent;

import com.ninja.ultron.activity.AssetActivity;
import com.ninja.ultron.activity.LoginActivity;
import com.ninja.ultron.activity.SplashScreen;

public class StartIntent {

    public static void startLoginActivity(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

    public static void HomescreenActivity(Activity act) {
        Intent myIntent = new Intent(act, AssetActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        act.startActivity(myIntent);
        act.finish();
    }

    public static void startSplashScreen(Activity activity) {
        Intent myIntent = new Intent(activity, SplashScreen.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//FLAG_ACTIVITY_NEW_TASK
        activity.startActivity(myIntent);
        activity.finish();
    }

    public static void exitApplication(Activity act) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        act.startActivity(intent);
    }
}
