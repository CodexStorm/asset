package com.ninja.ultron.functions;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.widget.Toast;

import com.ninja.ultron.constant.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    public static String convertEpochTimeToHumanReadableFormat(long dateFromServer, String formatDate) {
        SimpleDateFormat formatter, FORMATTER;
        String displayDateToUser = null;
        Date tempDate = null;
        FORMATTER = new SimpleDateFormat(formatDate);
        displayDateToUser = FORMATTER.format(dateFromServer);
        return displayDateToUser;
    }

    public static void errorMessage(String message, Activity act) {
        if (message != null) {
            toastString("SignIn unsuccessful. Try Again",
                    act.getApplicationContext());
            SharedPreferences settings = (act.getApplicationContext())
                    .getSharedPreferences(Constants.USER_PREFERENCE, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("userLoggedIn", false);
            // Commit the edits!
            editor.commit();
            StartIntent.startLoginActivity(act);

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

    public static int getPackageVersion(Context context) {
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return info.versionCode;
    }

    public static void clearLocalPreference(Context context) {
        UserDetails.setMyAssetList(context,null);
    }

    public static void restClientErrorValidation(int statusCode, String serverResponseMessage, Context context) {
        if (statusCode == Constants.HTTP_AUTHENTICATON_FAILURE_CODE) {
            errorMessage(serverResponseMessage, (Activity) context);
        } else if (serverResponseMessage != null) {
            toastString(serverResponseMessage, context);
        } else {
            if (isNetworkAvailable((Activity) context)) {
                String showErrorMessage = "Code : " + statusCode + " " + Constants.REST_FAILURE_MESSAGE;
                toastString(showErrorMessage, context);
            } else {
                toastString(Constants.NO_INTERNET_CONNECTION, context);
            }
        }
    }

}
