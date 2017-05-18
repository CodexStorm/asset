package com.ninja.ultron.activity;

import android.Manifest;
import android.animation.Animator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ninja.ultron.R;
import com.ninja.ultron.functions.CommonFunctions;
import com.ninja.ultron.functions.StartIntent;
import com.ninja.ultron.functions.UserDetails;

public class SplashScreen extends AppCompatActivity {

    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    boolean isSplashIsLoading = false;
    RelativeLayout rlSplashActionContainer;
    TextView tvSplashNoInternet, directHeader, copyRight;
    Button bSplashRetry;
    ProgressBar splashCentreProgressBar, splashProgressBar;
    ImageView imgProgressLoader;
    RelativeLayout dialogEmptyCartHolder;
    View circle1,circle2;
    TextView tvCancelUpdate, tvUpdateApp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Intent intent = new Intent(this, MetaDetaService.class);
        startService(intent);*/
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    CommonFunctions.toastString("Kindly allow permission to proceed", SplashScreen.this);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
                String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE};
                CommonFunctions.updatedCheckPermisson(SplashScreen.this, permissions, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.act_splash_screen);
        splashCentreProgressBar = (ProgressBar) findViewById(R.id.splashCentreProgressBar);
        splashProgressBar = (ProgressBar) findViewById(R.id.splashProgressBar);
        tvSplashNoInternet = (TextView) findViewById(R.id.tvSplashNoInternet);
        copyRight = (TextView) findViewById(R.id.copyRight);
        bSplashRetry = (Button) findViewById(R.id.bSplashRetry);
        rlSplashActionContainer = (RelativeLayout) findViewById(R.id.rlSplashActionContainer);
        rlSplashActionContainer.setVisibility(View.GONE);
        moveToNextActivity();
        circle1 = findViewById(R.id.circle1);
        circle2 = findViewById(R.id.circle2);

        Animation pulse1 = AnimationUtils.loadAnimation(this,R.anim.pulse);
        Animation pulse2 = AnimationUtils.loadAnimation(this,R.anim.pulse);
        circle1.startAnimation(pulse1);
        circle2.startAnimation(pulse2);
    }

    public void retry(View v) {
        moveToNextActivity();
    }

    private void moveToNextActivity() {
        splashCentreProgressBar.setVisibility(View.VISIBLE);
        if(CommonFunctions.isNetworkAvailable(SplashScreen.this)) {
            if(UserDetails.isUserLoggedIn(SplashScreen.this)) {
                String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE};
                if (CommonFunctions.updatedCheckPermisson(SplashScreen.this, permissions, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS)) {
                    moveToMainActivity();
                }
            } else {
                StartIntent.startLoginActivity(SplashScreen.this);
                finish();
            }
        } else {
            splashCentreProgressBar.setVisibility(View.GONE);
            rlSplashActionContainer.setVisibility(View.VISIBLE);
           // CommonFunctions.toastString("No Internet Connection",SplashScreen.this);
        }
    }

    private void moveToMainActivity() {
        isSplashIsLoading = true;
        rlSplashActionContainer.setVisibility(View.GONE);
        splashCentreProgressBar.setVisibility(View.VISIBLE);


    }
}
