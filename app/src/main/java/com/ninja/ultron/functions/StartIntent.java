package com.ninja.ultron.functions;

import android.app.Activity;
import android.content.Intent;

import com.ninja.ultron.activity.LoginActivity;

public class StartIntent {

    public static void startLoginActivity(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }
}
