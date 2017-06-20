package com.ninja.ultron.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.ninja.ultron.R;
import com.ninja.ultron.adapter.NewAssetAdapter;
import com.ninja.ultron.entity.AssetTypeEntity;
import com.ninja.ultron.entity.NewAssetEntity;
import com.ninja.ultron.entity.NewAssetInitateRequestEntity;
import com.ninja.ultron.entity.RequestNewAssetEntity;
import com.ninja.ultron.functions.CommonFunctions;
import com.ninja.ultron.functions.UserDetails;
import com.ninja.ultron.restclient.RestClientImplementation;

import java.util.ArrayList;
import java.util.List;

public class NewAssetSummaryActivity extends AppCompatActivity {

    RecyclerView.LayoutManager layoutManager;
    RecyclerView newAssetRecyclerView;
    NewAssetAdapter newAssetAdapter;
    TextView tvRequestedBy;
    TextView tvFacility;
    TextView tvhFacility;
    RelativeLayout rlRequestButton;
    int categoryId;
    List<NewAssetEntity> newAssetEntityList;
    List<Integer> assetTypeIdList;
    NewAssetInitateRequestEntity newAssetInitateRequestEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_asset_summary);
        Intent newAssetSummary = getIntent();
        newAssetEntityList =(List<NewAssetEntity>)newAssetSummary.getSerializableExtra("NewAssetList");
        assetTypeIdList = (List<Integer>)newAssetSummary.getSerializableExtra("AssetTypeId");
        Log.d("List",assetTypeIdList+"");
        categoryId = newAssetSummary.getIntExtra("CategoryId",0);
        tvRequestedBy = (TextView)findViewById(R.id.tvRequestedBy);
        tvFacility = (TextView)findViewById(R.id.Facility);
        tvhFacility = (TextView)findViewById(R.id.tvhFaciltity);
        rlRequestButton = (RelativeLayout)findViewById(R.id.rlRequestButton);
        tvRequestedBy.setText(UserDetails.getUserName(NewAssetSummaryActivity.this));


        newAssetRecyclerView = (RecyclerView)findViewById(R.id.rvNewAsset);
        newAssetRecyclerView.hasFixedSize();
        layoutManager = new LinearLayoutManager(this);
        newAssetRecyclerView.setLayoutManager(layoutManager);
        newAssetAdapter = new NewAssetAdapter(NewAssetSummaryActivity.this,newAssetEntityList);
        newAssetRecyclerView.setAdapter(newAssetAdapter);
        newAssetAdapter.notifyDataSetChanged();

        if(categoryId == 2){
            tvhFacility.setVisibility(View.VISIBLE);
            tvFacility.setVisibility(View.VISIBLE);
            tvFacility.setText(UserDetails.getFacilityName(NewAssetSummaryActivity.this));
        }

        createNewAssetRequestEntity();

        rlRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callRequestAssetApi();
            }
        });

    }

    private void createNewAssetRequestEntity() {
        int Requestedby;
        String comments="";
        Requestedby = UserDetails.getAsgardUserId(NewAssetSummaryActivity.this);
        List<RequestNewAssetEntity> requestNewAssetEntityList = new ArrayList<>();
        for(int i = 0 ; i<assetTypeIdList.size();i++ )
        {
            RequestNewAssetEntity entity = new RequestNewAssetEntity();
            entity.setSkuQuantity(newAssetEntityList.get(i).getQuantity());
            entity.setSkuTypeId(assetTypeIdList.get(i));
            requestNewAssetEntityList.add(entity);
        }
        newAssetInitateRequestEntity = new NewAssetInitateRequestEntity();
        newAssetInitateRequestEntity.setAssetMakeDetails(requestNewAssetEntityList);
        newAssetInitateRequestEntity.setComment(comments);
        newAssetInitateRequestEntity.setUserId(Requestedby);
        newAssetInitateRequestEntity.setFacilityId(UserDetails.getFacilityId(NewAssetSummaryActivity.this));
        newAssetInitateRequestEntity.setAssetSkuCategoryId(categoryId);
    }

    private void callRequestAssetApi() {
       // NewAssetInitateRequestEntity newAssetInitateRequestEntity1= new NewAssetInitateRequestEntity();
        RestClientImplementation.intiateNewAssetRequestApi(newAssetInitateRequestEntity, new NewAssetInitateRequestEntity.UltronRestClientInterface() {
            @Override
            public void onInitialize(NewAssetInitateRequestEntity newAssetInitateRequestEntity, VolleyError error) {
                if(error==null)
                {

                }
            }
        },NewAssetSummaryActivity.this);
    }
}
