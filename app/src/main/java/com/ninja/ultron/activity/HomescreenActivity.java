package com.ninja.ultron.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ninja.ultron.R;
import com.ninja.ultron.functions.StartIntent;

public class HomescreenActivity extends AppCompatActivity {

    TextView bMyAssets;
    TextView bAttendnace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        bMyAssets = (TextView) findViewById(R.id.bMyAssets);
        bAttendnace = (TextView) findViewById(R.id.bAttendance);

        bMyAssets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               StartIntent.MyAssetActivity(HomescreenActivity.this);
            }
        });

        bAttendnace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartIntent.Attendance(HomescreenActivity.this);

            }
        });
    }
}
