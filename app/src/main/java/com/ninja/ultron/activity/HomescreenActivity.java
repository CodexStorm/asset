package com.ninja.ultron.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.support.v7.widget.CardView;
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
    CardView cvMyAsset;
    CardView cvMyAttendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        /*bMyAssets = (TextView) findViewById(R.id.bMyAssets);
        bAttendnace = (TextView) findViewById(R.id.bAttendance);*/
        String role = UserDetails.getRole(this);
        Log.d("ROle", role);

        if (role.contains(Constants.USER_ROLE_DC_SUPERVISOR)) {
            cvMyAsset = (CardView) findViewById(R.id.cvMyAsset);
            cvMyAttendance = (CardView) findViewById(R.id.cvMyAttendance);
        /*if(role== Constants.USER_ROLE_DC_SUPERVISOR){
>>>>>>> fa262b693ee647142b2cfd649c71487441429674

        }
        else{
            bAttendnace.setVisibility(View.GONE);
<<<<<<< HEAD
        }

        bMyAssets.setOnClickListener(new View.OnClickListener() {
=======
        }*/
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
        /*bMyAssets.setOnClickListener(new View.OnClickListener() {
>>>>>>> fa262b693ee647142b2cfd649c71487441429674
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
        });*/
        }
    }
}
