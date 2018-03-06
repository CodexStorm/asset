package com.ninja.ultron.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.ninja.ultron.Fragments.ReportAssetFragment;
import com.ninja.ultron.R;
import com.ninja.ultron.adapter.AssetAccessoryAdapter;
import com.ninja.ultron.entity.AssetAccessoryEntity;
import com.ninja.ultron.entity.AssetDetailsEntity;
import com.ninja.ultron.entity.AssetDetailsMiniEntity;
import com.ninja.ultron.functions.UserDetails;
import com.ninja.ultron.restclient.RestClientImplementation;

import java.util.ArrayList;
import java.util.List;

public class AssetDetailsActivity extends AppCompatActivity {

    TextView tvName1;
    TextView tvId1;
    TextView tvCategory1;
    TextView tvType1;
    TextView tvMaker1;
    TextView tvSpecifiaction1;
    ListView lvAccessories;
    TextView tvAssetMake;
    TextView tvFacility;
    TextView tvStatus;
    List<AssetAccessoryEntity> assetAccessoryList = new ArrayList<>();
    AssetAccessoryEntity assetAccessory;
    List<AssetDetailsEntity> assetDetailsList;
    AssetAccessoryAdapter adapter;
    BottomNavigationView bottomNavigationView;
    ReportAssetFragment reportAssetFragment;
    ProgressBar centreProgressBar;
    RelativeLayout rlProgress;
    String assetName;
    int assetId;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_details);
        back = (ImageButton)findViewById(R.id.back);
        tvName1 = (TextView) findViewById(R.id.tvName1);
        tvCategory1 = (TextView) findViewById(R.id.tvCategory1);
        tvType1 = (TextView) findViewById(R.id.tvType1);
        tvAssetMake = (TextView)findViewById(R.id.tvAssetMake1);
        lvAccessories = (ListView) findViewById(R.id.lvAccessories);
        rlProgress = (RelativeLayout)findViewById(R.id.rlProgress);
        centreProgressBar = (ProgressBar)findViewById(R.id.centreProgressBar);
        tvFacility = (TextView)findViewById(R.id.tvFacility);
        tvStatus = (TextView)findViewById(R.id.tvStatus);
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_report:
                        Intent reportAssetIntent = new Intent(AssetDetailsActivity.this,ReportAssetActivity.class);
                        reportAssetIntent.putExtra("AssetName",assetName);
                        reportAssetIntent.putExtra("AssetId",assetId);
                        startActivity(reportAssetIntent);
                        //initiateTransferFragment.selectedToName = "Admin";
                        break;
                    case R.id.request_asset:

                        break;

                }
                return true;
            }
        });


        callAssetDetailsApi();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void callAssetDetailsApi() {
        AssetDetailsMiniEntity assetDetailsMiniEntity = new AssetDetailsMiniEntity();
        rlProgress.setVisibility(View.VISIBLE);
        centreProgressBar.setVisibility(View.VISIBLE);
        RestClientImplementation.assetDetailsApi(assetDetailsMiniEntity, new AssetDetailsMiniEntity.UltronRestClientInterface() {
            @Override
            public void onInitialize(AssetDetailsMiniEntity assetDetailsMiniEntity, VolleyError error) {
                if (error == null) {
                    if (assetDetailsMiniEntity.getResponse() != null) {
                        Gson gs = new Gson();
                        centreProgressBar.setVisibility(View.GONE);
                        rlProgress.setVisibility(View.GONE);
                        assetDetailsList = assetDetailsMiniEntity.getResponse();
                        AssetDetailsEntity assetDetailsEntity = assetDetailsList.get(0);
                        tvName1.setText(assetDetailsEntity.getNomenclature());
                        tvCategory1.setText(assetDetailsEntity.getAssetCategory());
                        tvType1.setText(assetDetailsEntity.getAssetType());
                        tvAssetMake.setText(assetDetailsEntity.getAssetMake());
                        tvStatus.setText(assetDetailsEntity.getStatusName());
                        tvFacility.setText(assetDetailsEntity.getFacilityId()+"");
                        assetAccessoryList = assetDetailsEntity.getAssetAccessory();
                        assetName = assetDetailsEntity.getAssetMake();
                        assetId = assetDetailsEntity.getAssetId();
                        if (assetAccessoryList.size() != 0) {
                            String myAssetAccessoryListAsString = gs.toJson(assetAccessoryList);
                            UserDetails.setAssetAccessoryList(AssetDetailsActivity.this, myAssetAccessoryListAsString);
                            adapter = new AssetAccessoryAdapter(AssetDetailsActivity.this, assetAccessoryList);
                            lvAccessories.setAdapter(adapter);
                        } else {
                            lvAccessories.setVisibility(View.GONE);
                        }
                    }
                } else {

                }
            }
        }, AssetDetailsActivity.this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
