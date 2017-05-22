package com.ninja.ultron.activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.ninja.ultron.R;
import com.ninja.ultron.entity.LoginEntity;
import com.ninja.ultron.restclient.RestClientImplementation;

public class LoginActivity extends AppCompatActivity {
    Button bLogin;
    RelativeLayout rlProgress;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setViewId();
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
                Intent intent = new Intent(LoginActivity.this,HomescreenActivity.class);
                startActivity(intent);
            }
        });
    }

    public void setViewId() {
        rlProgress = (RelativeLayout) findViewById(R.id.rlProgress);
        bLogin = (Button) findViewById(R.id.bLogin);

    }

    public void login() {
        LoginEntity loginEntity = new LoginEntity("sarath", "123456");
        rlProgress.setVisibility(View.VISIBLE);
        RestClientImplementation.userLogin(loginEntity, new LoginEntity.RestClientInterface() {
            @Override
            public void onLogin(LoginEntity loginEntity, VolleyError error) {
                if (error == null) {
                    Toast.makeText(LoginActivity.this, "success", Toast.LENGTH_SHORT).show();
                } else if (error != null) {
                    Toast.makeText(LoginActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
                rlProgress.setVisibility(View.GONE);
            }
        }, LoginActivity.this);
    }
}
