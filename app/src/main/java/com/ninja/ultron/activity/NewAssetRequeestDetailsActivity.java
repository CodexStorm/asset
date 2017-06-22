package com.ninja.ultron.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.ninja.ultron.R;
import com.ninja.ultron.adapter.AssetDetailsAdapter;
import com.ninja.ultron.entity.NewAssetEntityGroup;
import com.ninja.ultron.entity.NewAssetMiniEntityGroup;
import com.ninja.ultron.entity.NewAssetTypeDetailsEntity;
import com.ninja.ultron.functions.CommonFunctions;
import com.ninja.ultron.restclient.RestClientImplementation;

import java.util.ArrayList;
import java.util.List;

public class NewAssetRequeestDetailsActivity extends AppCompatActivity {


    NewAssetEntityGroup newAssetEntityGroupList;
    NewAssetEntityGroup entity;
    List<NewAssetTypeDetailsEntity> assetDetails;
    TextView tvRequestId;
    TextView tvCategoryName;
    TextView tvStatus;
    TextView tvRequestType;
    RecyclerView rvNewAssets;
    AssetDetailsAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_asset_requeest_details);

        tvRequestId = (TextView)findViewById(R.id.tvRequestId);
        tvCategoryName = (TextView)findViewById(R.id.tvCategoryName);
        tvStatus = (TextView)findViewById(R.id.tvStatus);
        tvRequestType = (TextView)findViewById(R.id.tvRequestType);
        rvNewAssets = (RecyclerView)findViewById(R.id.rvNewAssets);
        assetDetails = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(NewAssetRequeestDetailsActivity.this);
        rvNewAssets.setLayoutManager(manager);
        callNewAssetRequestDetailsApi();

    }

    private void callNewAssetRequestDetailsApi() {
        entity = new NewAssetEntityGroup();
        NewAssetMiniEntityGroup newAssetMiniEntityGroup = new NewAssetMiniEntityGroup();
        RestClientImplementation.getNewAssetDetailsApi(newAssetMiniEntityGroup, new NewAssetMiniEntityGroup.UltronRestClientInterface() {
            @Override
            public void onInitialize(NewAssetMiniEntityGroup newAssetMiniEntityGroup, VolleyError error) {
                if(error == null)
                {
                    if(newAssetMiniEntityGroup.getResponse()!=null)
                    {
                        newAssetEntityGroupList=newAssetMiniEntityGroup.getResponse();
                        entity=newAssetEntityGroupList;
                        tvCategoryName.setText(entity.getRequestDetails().getCategoryName());
                        tvRequestId.setText(""+entity.getRequestDetails().getRequestId());
                        tvRequestType.setText(entity.getRequestDetails().getRequestType());
                        tvStatus.setText(entity.getRequestDetails().getStatus());
                        assetDetails = entity.getRequestDetails().getAssetTypeDetails();
                        if(assetDetails.size()==0)
                        {

                        }
                        else
                        {
                            adapter = new AssetDetailsAdapter(assetDetails);
                            rvNewAssets.setAdapter(adapter);
                            rvNewAssets.hasFixedSize();
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
                else {
                    if(newAssetMiniEntityGroup.getStatusCode()==401)
                    {
                        CommonFunctions.toastString("Unauthorized",NewAssetRequeestDetailsActivity.this);
                    }
                }
            }
        },NewAssetRequeestDetailsActivity.this);

    }
}
