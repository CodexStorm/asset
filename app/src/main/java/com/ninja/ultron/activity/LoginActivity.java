package com.ninja.ultron.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.ninja.ultron.R;
import com.ninja.ultron.entity.LoginEntity;
import com.ninja.ultron.functions.CommonFunctions;
import com.ninja.ultron.functions.StartIntent;
import com.ninja.ultron.functions.UserDetails;
import com.ninja.ultron.restclient.RestClientImplementation;

public class LoginActivity extends AppCompatActivity {
    TextView tvLogin;
    RelativeLayout rlProgress;
    InputMethodManager imm;
    EditText etLoginUserName, etLoginPassword;
    String userName,userPassword;
    RelativeLayout rlLogin;
    ProgressBar splashCentreProgressBar;
    Boolean isLoginInProcess;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setViewId();
        if(UserDetails.isUserLoggedIn(LoginActivity.this)) {
            StartIntent.HomescreenActivity(LoginActivity.this);
        }
        userName = etLoginUserName.getText().toString();
        userPassword = etLoginPassword.getText().toString();

        etLoginPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    userName = etLoginUserName.getText().toString();
                    userPassword = etLoginPassword.getText().toString();
                    if (userPassword == null || userPassword.length() < 1) {
                        CommonFunctions.toastString("password should not be empty", LoginActivity.this);
                    } else {
                        loginValidation(v);
                    }
                }
                return false;
            }
        });

    }

    public void loginValidation(View v) {
        userName = etLoginUserName.getText().toString();
        userPassword = etLoginPassword.getText().toString();
        tvLogin.setText("Retry");
        if (userName == null || userName.length()<3 ) {
            CommonFunctions.toastString("Enter a Valid User Name",LoginActivity.this);
        }else if(userPassword == null || userPassword.length()<1){
            CommonFunctions.toastString("password should not be empty",LoginActivity.this);
        }else{
            tvLogin.setVisibility(View.GONE);
            tvLogin.setVisibility(View.GONE);
            login();
        }
    }

    public void setViewId() {
        rlProgress = (RelativeLayout) findViewById(R.id.rlProgress);
        tvLogin = (TextView) findViewById(R.id.tvLogin);
        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        etLoginUserName =  (EditText)findViewById(R.id.etLoginUserName);
        etLoginPassword = (EditText)findViewById(R.id.etLoginPassword);
        rlLogin = (RelativeLayout) findViewById(R.id.rlLogin);
        splashCentreProgressBar = (ProgressBar) findViewById(R.id.centreProgressBar);
    }

    public void login() {
        rlLogin.setVisibility(View.GONE);
        rlProgress.setVisibility(View.VISIBLE);
        isLoginInProcess = true;
        LoginEntity loginEntity = new LoginEntity(userName,userPassword);
        splashCentreProgressBar.setVisibility(View.VISIBLE);

        RestClientImplementation.userLogin(loginEntity, new LoginEntity.RestClientInterface() {
            @Override
            public void onLogin(LoginEntity loginEntity, VolleyError error) {
                   isLoginInProcess = false;
                    if(error == null){
                        tvLogin.setVisibility(View.VISIBLE);
                        splashCentreProgressBar.setVisibility(View.GONE);
                        if(loginEntity.getAsgardUser()==null){
                            CommonFunctions.toastString("Your access is denied. Please contact your admin.",LoginActivity.this);
                        }else {
                            UserDetails.serAsgardUserId(LoginActivity.this, loginEntity.getAsgardUser().getId());
                            UserDetails.setRole(LoginActivity.this,loginEntity.getAsgardUser().getRoles());
                            UserDetails.setUserName(LoginActivity.this, userName);
                            UserDetails.setUserPassword(LoginActivity.this, userPassword);
                            UserDetails.setUserLoggedIn(LoginActivity.this, true);
                            //Toast.makeText(LoginActivity.this,loginEntity.getToken()+"   "+loginEntity.getSessionId(),Toast.LENGTH_LONG).show();
                            UserDetails.setSessionId(LoginActivity.this,loginEntity.getSessionId());
                            UserDetails.setSessionToken(LoginActivity.this,loginEntity.getToken());
                            Intent i = new Intent(LoginActivity.this,AssetActivity.class);
                            startActivity(i);
                            //StartIntent.startSplashScreen(LoginActivity.this);
                        }
                    } else {
                        tvLogin.setVisibility(View.VISIBLE);
                        tvLogin.setText("Retry");
                        splashCentreProgressBar.setVisibility(View.GONE);
                        rlLogin.setVisibility(View.VISIBLE);
                        if(loginEntity.getCode() == 401){//404 == authentication error
                            CommonFunctions.toastString("Invalid Username or Password",LoginActivity.this);
                        }else if(loginEntity.getMessage()!=null){
                            CommonFunctions.toastString(loginEntity.getMessage(),LoginActivity.this);
                        }else{
                            CommonFunctions.toastString("We are revamping our system for giving you a better performance. Kindly try again later.",LoginActivity.this);
                        }
                    }
            }
        },LoginActivity.this);
    }

    @Override
    public void onBackPressed() {
        if(isLoginInProcess) {

        }else{
            StartIntent.exitApplication(LoginActivity.this);
        }
    }
}
