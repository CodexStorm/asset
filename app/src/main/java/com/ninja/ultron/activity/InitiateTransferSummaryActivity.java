package com.ninja.ultron.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ninja.ultron.R;
import com.ninja.ultron.adapter.TransferAssetAdapter;
import com.ninja.ultron.entity.CodeDecodeEntity;
import com.ninja.ultron.functions.UserDetails;

import java.util.List;

public class InitiateTransferSummaryActivity extends AppCompatActivity {

    RecyclerView.LayoutManager layoutManager;
    RecyclerView transferAssetRecyclerView;
    TransferAssetAdapter transferAssetAdapter;
    TextView tvRequestedBy;
    TextView tvFacility;
    TextView tvhFacility;
    int categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initiate_transfer_summary);
        Intent transferAssetSummary = getIntent();
       // List<CodeDecodeEntity> transferAssetEntityList =(List<CodeDecodeEntity>) transferAssetSummary.getSerializableExtra("TransferAssetList");
       // categoryId = transferAssetSummary.getIntExtra("CategoryId",0);
        tvRequestedBy = (TextView)findViewById(R.id.tvRequestedBy);
        tvFacility = (TextView)findViewById(R.id.Facility);
        tvFacility.setText(UserDetails.getFacilityName(InitiateTransferSummaryActivity.this));
        tvhFacility = (TextView)findViewById(R.id.tvhFaciltity);
        tvRequestedBy.setText(UserDetails.getUserName(InitiateTransferSummaryActivity.this));
        if(transferAssetSummary.getIntExtra("category",0) == 2) {
            tvFacility.setVisibility(View.VISIBLE);
            tvhFacility.setVisibility(View.VISIBLE);
        }

/*
        transferAssetRecyclerView = (RecyclerView)findViewById(R.id.rvNewAsset);
        transferAssetRecyclerView.hasFixedSize();
        layoutManager = new LinearLayoutManager(this);
        transferAssetRecyclerView.setLayoutManager(layoutManager);
        transferAssetAdapter = new TransferAssetAdapter(InitiateTransferSummaryActivity.this, transferAssetEntityList);
        transferAssetRecyclerView.setAdapter(transferAssetAdapter);
        transferAssetAdapter.notifyDataSetChanged();*/
    }
}
