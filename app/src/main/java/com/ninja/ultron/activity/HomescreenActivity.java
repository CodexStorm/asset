package com.ninja.ultron.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ninja.ultron.R;
import com.ninja.ultron.constant.Constants;
import com.ninja.ultron.functions.StartIntent;
import com.ninja.ultron.functions.UserDetails;

public class HomescreenActivity extends AppCompatActivity {

    CardView cvMyAsset;
    CardView cvMyAttendance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        String role= UserDetails.getRole(this);
        Log.d("ROle",role);

        if(role.contains(Constants.USER_ROLE_DC_SUPERVISOR)){
        cvMyAsset = (CardView) findViewById(R.id.cvMyAsset);
        cvMyAttendance = (CardView)findViewById(R.id.cvMyAttendance);
        cvMyAsset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartIntent.MyAssetActivity(HomescreenActivity.this);
            }
        });
        cvMyAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartIntent.commonStartActivity(HomescreenActivity.this, LabourAttendanceMainActivity.class, null);
            }
        });
        }
        else {
            Toast.makeText(getApplicationContext(),"Session Blocked for you",Toast.LENGTH_LONG).show();
        }
  }
}
