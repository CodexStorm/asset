package com.ninja.ultron.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.ninja.ultron.R;
import com.ninja.ultron.constant.Constants;
import com.ninja.ultron.entity.CodeDecodeEntity;
import com.ninja.ultron.entity.InitApiEntity;
import com.ninja.ultron.functions.CommonFunctions;
import com.ninja.ultron.functions.StartIntent;
import com.ninja.ultron.functions.UserDetails;
import com.ninja.ultron.restclient.RestClientImplementation;

import java.util.ArrayList;
import java.util.List;

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

    List<CodeDecodeEntity> myAssetList = new ArrayList<>();


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
        splashCentreProgressBar = (ProgressBar) findViewById(R.id.centreProgressBar);
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
        InitApiEntity initApiEntity = new InitApiEntity(CommonFunctions.getPackageVersion(SplashScreen.this), Constants.INIT_APP_NAME, UserDetails.getAsgardUserId(SplashScreen.this));
        RestClientImplementation.initApi(initApiEntity, new InitApiEntity.UltronRestClientInterface() {
            @Override
            public void onInitialize(InitApiEntity initApiEntity, VolleyError error) {
                isSplashIsLoading = false;
                if(error == null) {
                    if (initApiEntity.getAsgardUser() == null) {
                        CommonFunctions.toastString("Not a proper user", SplashScreen.this);
                        failureHideOrShowView();
                        return;
                    }
                    if (initApiEntity.getAsgardUser().getAsgardUserPropertyMap() == null) {
                        CommonFunctions.toastString("Not able to find asgard user property map for this user.", SplashScreen.this);
                        failureHideOrShowView();
                        return;
                    }
                    if (initApiEntity.getAsgardUser().getAsgardUserPropertyMap().getCity() == null) {
                        CommonFunctions.toastString("User doesn't map with any city", SplashScreen.this);
                        failureHideOrShowView();
                        return;
                    }
                    if (initApiEntity.getAsgardUser().getAsgardUserPropertyMap().getFacility() == null) {
                        CommonFunctions.toastString("User doesn't map with any facility", SplashScreen.this);
                        failureHideOrShowView();
                        return;
                    }
                    if (initApiEntity.getInitializeConfig().get(1).getMandatoryUpdateFlag() == 1) {//Mandatory upgrade true/==1
//                        imgProgressLoader.setVisibility(View.GONE);
                        splashCentreProgressBar.setVisibility(View.GONE);
                        dialogEmptyCartHolder.setVisibility(View.VISIBLE);
                    } else {
                        Gson gs = new Gson();
                        Log.d("", " ServerTime = " + initApiEntity.getCurrentServerDate());
                        Log.d("", " CurrentTime = " + System.currentTimeMillis());
                        long serveCurrentTime = initApiEntity.getCurrentServerDate() + 19080000; //adding +5.30
                        long currentSystemTime = System.currentTimeMillis();
                        long currentServerTime = initApiEntity.getCurrentServerDate();
                        UserDetails.setServerTimeDifference(SplashScreen.this, (currentServerTime - currentSystemTime));
                        UserDetails.setCurrentServerDate(SplashScreen.this, serveCurrentTime);
                        String userCityId = String.valueOf(initApiEntity.getAsgardUser().getAsgardUserPropertyMap().getCity().getId());
                        UserDetails.setUserCityId(SplashScreen.this, userCityId);
                        UserDetails.setCustomerCareMobileNumber(SplashScreen.this, initApiEntity.getInitializeConfig().get(1).getCustomerCareNumber());
                        UserDetails.setCustomerCareEmail(SplashScreen.this, initApiEntity.getInitializeConfig().get(1).getCustomerCareEmail());

                        if (initApiEntity.getAsgardUser().getAsgardUserPropertyMap().getCity() != null) {
                            UserDetails.setCityId(SplashScreen.this, initApiEntity.getAsgardUser().getAsgardUserPropertyMap().getCity().getId());
                        }

                        if (initApiEntity.getInitializeConfig() != null) {
                            String initializeConfig = gs.toJson(initApiEntity.getInitializeConfig());
                            Log.d("", "" + initializeConfig);
                            UserDetails.setInitializeConfig(SplashScreen.this, (new Gson()).toJson(initializeConfig));
                        }

                        /*if (initApiEntity.getMyAssetList() != null && initApiEntity.getMyAssetList().size() > 0) {
                             myAssetList = initApiEntity.getMyAssetList();
                            String getMyAssetListAsString = gs.toJson(myAssetList);
                            UserDetails.setMyAssetList(SplashScreen.this, getMyAssetListAsString);
                        } else {
                            UserDetails.setMyAssetList(SplashScreen.this, "");
                        }*/

                        if (initApiEntity.getAsgardUser().getAsgardUserPropertyMap().getFacility() != null) {
                            UserDetails.setFacilityId(SplashScreen.this, initApiEntity.getAsgardUser().getAsgardUserPropertyMap().getFacility().getId());
                            UserDetails.setFacilityName(SplashScreen.this, initApiEntity.getAsgardUser().getAsgardUserPropertyMap().getFacility().getName());
                        }

                        StartIntent.HomescreenActivity(SplashScreen.this);
                        //StartIntent.startTestActivity(SplashScreen.this);
                        finish();
                    }
                } else {
                    //imgProgressLoader.setVisibility(View.GONE);
                    splashCentreProgressBar.setVisibility(View.GONE);
                    if (initApiEntity.getCode() == 401) {//401 == authentication error
                        CommonFunctions.errorMessage(initApiEntity.getMessage(), SplashScreen.this);
                        rlSplashActionContainer.setVisibility(View.VISIBLE);
                        tvSplashNoInternet.setVisibility(View.GONE);
                    } else if (initApiEntity.getMessage() != null) {
                        CommonFunctions.toastString(initApiEntity.getMessage(), SplashScreen.this);
                        rlSplashActionContainer.setVisibility(View.VISIBLE);
                        tvSplashNoInternet.setVisibility(View.GONE);
                    } else {
                        hideOrShowOfflineView();
                        ;
                        CommonFunctions.toastString(Constants.NO_INTERNET_CONNECTION, SplashScreen.this);
                        rlSplashActionContainer.setVisibility(View.VISIBLE);
                    }

                }
            }
        }, SplashScreen.this);





    }

    public void hideOrShowOfflineView() {
    }

    public void failureHideOrShowView() {

        splashCentreProgressBar.setVisibility(View.GONE);
        rlSplashActionContainer.setVisibility(View.VISIBLE);
        tvSplashNoInternet.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public void finish() {
        super.finish();
    }
}
