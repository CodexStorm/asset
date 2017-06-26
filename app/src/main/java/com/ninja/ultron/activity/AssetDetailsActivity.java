package com.ninja.ultron.activity;

import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_details);

        tvName1 = (TextView) findViewById(R.id.tvName1);
        tvCategory1 = (TextView) findViewById(R.id.tvCategory1);
        tvType1 = (TextView) findViewById(R.id.tvType1);
        tvAssetMake = (TextView)findViewById(R.id.tvAssetMake1);
        lvAccessories = (ListView) findViewById(R.id.lvAccessories);
        rlProgress = (RelativeLayout)findViewById(R.id.rlProgress);
        centreProgressBar = (ProgressBar)findViewById(R.id.centreProgressBar);
        tvFacility = (TextView)findViewById(R.id.tvFacility);
        tvStatus = (TextView)findViewById(R.id.tvStatus);



        callAssetDetailsApi();
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
}
