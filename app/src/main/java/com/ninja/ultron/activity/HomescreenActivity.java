package com.ninja.ultron.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ninja.ultron.R;
import com.ninja.ultron.constant.Constants;
import com.ninja.ultron.functions.StartIntent;
import com.ninja.ultron.functions.UserDetails;

public class HomescreenActivity extends AppCompatActivity {

    TextView bMyAssets;
    TextView bAttendnace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        bMyAssets = (TextView) findViewById(R.id.bMyAssets);
        bAttendnace = (TextView) findViewById(R.id.bAttendance);
        String role= UserDetails.getRole(this);

        /*if(role== Constants.USER_ROLE_DC_SUPERVISOR){

        }
        else{
            bAttendnace.setVisibility(View.GONE);
        }*/

        bMyAssets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               StartIntent.MyAssetActivity(HomescreenActivity.this);
            }
        });

        bAttendnace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //StartIntent.Attendance(HomescreenActivity.this);
                    StartIntent.commonStartActivity(HomescreenActivity.this, LabourAttendanceMainActivity.class, null);
            }
        });
    }
}
