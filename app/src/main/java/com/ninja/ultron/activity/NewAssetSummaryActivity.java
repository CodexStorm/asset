package com.ninja.ultron.activity;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ninja.ultron.R;
import com.ninja.ultron.adapter.CheckAdapter;
import com.ninja.ultron.entity.NewAssetEntity;
import com.ninja.ultron.functions.UserDetails;

import java.util.ArrayList;
import java.util.List;

public class NewAssetSummaryActivity extends AppCompatActivity {

    RecyclerView.LayoutManager layoutManager;
    RecyclerView newAssetRecyclerView;
    CheckAdapter newAssetAdapter;
    TextView tvRequestedBy;
    TextView tvFacility;
    TextView tvhFacility;
    int categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_asset_summary);
        Intent newAssetSummary = getIntent();
        List<NewAssetEntity> newAssetEntityList =(List<NewAssetEntity>)newAssetSummary.getSerializableExtra("NewAssetList");
        categoryId = newAssetSummary.getIntExtra("CategoryId",0);
        tvRequestedBy = (TextView)findViewById(R.id.tvRequestedBy);
        tvFacility = (TextView)findViewById(R.id.Facility);
        tvhFacility = (TextView)findViewById(R.id.tvhFaciltity);
        tvRequestedBy.setText(UserDetails.getUserName(NewAssetSummaryActivity.this));


        newAssetRecyclerView = (RecyclerView)findViewById(R.id.rvNewAsset);
        newAssetRecyclerView.hasFixedSize();
        layoutManager = new LinearLayoutManager(this);
        newAssetRecyclerView.setLayoutManager(layoutManager);
        newAssetAdapter = new CheckAdapter(NewAssetSummaryActivity.this,newAssetEntityList);
        newAssetRecyclerView.setAdapter(newAssetAdapter);
        newAssetAdapter.notifyDataSetChanged();

        if(categoryId == 2){
            tvhFacility.setVisibility(View.VISIBLE);
            tvFacility.setVisibility(View.VISIBLE);
            tvFacility.setText(UserDetails.getFacilityName(NewAssetSummaryActivity.this));
        }

    }
}
