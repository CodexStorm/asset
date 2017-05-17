package com.ninja.ultron.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ninja.ultron.R;

import org.w3c.dom.Text;

public class HomepageActivity extends AppCompatActivity {

    TextView tvMyAssets,tvPendingRequests;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home);
        tvMyAssets=(TextView)findViewById(R.id.tvMyAsset);
        tvPendingRequests=(TextView)findViewById(R.id.tvPendingRequests);
        tvMyAssets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomepageActivity.this,MyAssetsActivity.class));
            }
        });
        tvPendingRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(HomepageActivity.this,PendingRequests.class));
            }
        });
    }
}