package com.ninja.ultron.functions;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by omprakash on 17/5/17.
 */

public class CommonFunctions {

    public static void toastString(String toastText, Context context) {
        if (context != null) {
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, toastText, duration);
            toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP, 0, 100);
            toast.show();
        }
    }

    public static boolean isNetworkAvailable(Activity act) {
        ConnectivityManager connectivityManager = (ConnectivityManager) act
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean updatedCheckPermisson(Activity context, String[] permissions, final int permission_request_code) {
        if (Build.VERSION.SDK_INT >= 23) {
            for (String checkPermission : permissions) {
                if (ContextCompat.checkSelfPermission(context,
                        checkPermission)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(context, permissions,
                            permission_request_code);
                    return false;
                }
            }
        }
        return true;
    }
}
