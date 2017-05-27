package com.ninja.ultron.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.ninja.ultron.Fragments.InitiateTransferFragment;
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
    List<AssetAccessoryEntity> assetAccessoryList = new ArrayList<>();
    AssetAccessoryEntity assetAccessory;
    List<AssetDetailsEntity> assetDetailsList;
    AssetAccessoryAdapter adapter;
    BottomNavigationView bottomNavigationView;
    InitiateTransferFragment initiateTransferFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_details);

        tvName1 = (TextView) findViewById(R.id.tvName1);
        tvId1 = (TextView) findViewById(R.id.tvId1);
        tvCategory1 = (TextView) findViewById(R.id.tvCategory1);
        tvType1 = (TextView) findViewById(R.id.tvType1);
        tvMaker1 = (TextView) findViewById(R.id.tvMaker1);
        tvSpecifiaction1 = (TextView) findViewById(R.id.tvSpecification1);
        lvAccessories = (ListView) findViewById(R.id.lvAccessories);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_request:
                        initiateTransferFragment = new InitiateTransferFragment();
                        initiateTransferFragment.selectedName = tvName1.getText().toString();
                        initiateTransferFragment.selectedId = Integer.parseInt(tvId1.getText().toString());
                        initiateTransferFragment.selectedToName = "Admin";
                        getSupportFragmentManager().beginTransaction().replace(R.id.rlMyAssetList, initiateTransferFragment,initiateTransferFragment.getClass().getSimpleName()).addToBackStack(null).commit();
                        break;
                    case R.id.action_report:
                        initiateTransferFragment = new InitiateTransferFragment();

                        break;

                }
                return true;
            }
        });


        callAssetDetailsApi();
    }

    private void callAssetDetailsApi() {
        AssetDetailsMiniEntity assetDetailsMiniEntity = new AssetDetailsMiniEntity();
        RestClientImplementation.assetDetailsApi(assetDetailsMiniEntity, new AssetDetailsMiniEntity.UltronRestClientInterface() {
            @Override
            public void onInitialize(AssetDetailsMiniEntity assetDetailsMiniEntity, VolleyError error) {
                if (error == null) {
                    if (assetDetailsMiniEntity.getResponse() != null) {
                        Gson gs = new Gson();
                        assetDetailsList = assetDetailsMiniEntity.getResponse();
                        AssetDetailsEntity assetDetailsEntity = assetDetailsList.get(0);
                        tvId1.setText("" + assetDetailsEntity.getAssetId());
                        tvName1.setText(assetDetailsEntity.getAssetName());
                        tvCategory1.setText(assetDetailsEntity.getAssetCategory());
                        tvMaker1.setText(assetDetailsEntity.getAssetMaker());
                        tvType1.setText(assetDetailsEntity.getAssetType());
                        tvSpecifiaction1.setText(assetDetailsEntity.getAssetSpecification());
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
