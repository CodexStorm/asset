package com.ninja.ultron.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.ninja.ultron.R;
import com.ninja.ultron.adapter.SkuAssetDetailsAdapter;
import com.ninja.ultron.entity.EntityGroup;
import com.ninja.ultron.entity.SkuAssetDetailsEntity;
import com.ninja.ultron.entity.TranferDetailsAssetListMiniEntity;
import com.ninja.ultron.entity.TransferDetailsAssetListEntity;
import com.ninja.ultron.functions.CommonFunctions;
import com.ninja.ultron.restclient.RestClientImplementation;

import java.util.ArrayList;
import java.util.List;

public class TransferAssetRequestDetailsActivity extends AppCompatActivity {


    List<TransferDetailsAssetListEntity> transferDetailsAssetListEntities;
    TransferDetailsAssetListEntity entity;
    List<SkuAssetDetailsEntity> skuAssetDetails;

    TextView tvRequestId;
    TextView tvCategoryName;
    TextView tvStatus;
    TextView tvRequestType;
    RecyclerView rvTransferAssets;
    SkuAssetDetailsAdapter adapter;
    LinearLayout bBeforeRmApproval;
    LinearLayout bAfterAdminApproval;
    Button bEdit;
    Button bDelete;
    Button bAccept;
    Button bReject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_asset_request_details);
        tvRequestId = (TextView)findViewById(R.id.tvRequestId);
        tvCategoryName = (TextView)findViewById(R.id.tvCategoryName);
        tvStatus = (TextView)findViewById(R.id.tvStatus);
        tvRequestType = (TextView)findViewById(R.id.tvRequestType);
        rvTransferAssets = (RecyclerView)findViewById(R.id.rvTransferAssets);
        bBeforeRmApproval = (LinearLayout)findViewById(R.id.bBeforeRmApproval);
        bAfterAdminApproval = (LinearLayout)findViewById(R.id.bAfterAdminApproval);
        bEdit = (Button)findViewById(R.id.bEdit);
        bDelete = (Button)findViewById(R.id.bDelete);
        bAccept = (Button)findViewById(R.id.bAccept);
        bReject = (Button)findViewById(R.id.bReject);
        transferDetailsAssetListEntities = new ArrayList<>();
        skuAssetDetails = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(TransferAssetRequestDetailsActivity.this);
        rvTransferAssets.setLayoutManager(manager);
        callGetDetailsApi();

    }

    private void callGetDetailsApi() {
        entity = new TransferDetailsAssetListEntity();
        TranferDetailsAssetListMiniEntity tranferDetailsAssetListMiniEntity = new TranferDetailsAssetListMiniEntity();
        RestClientImplementation.getPendingTransferDetailsApi(tranferDetailsAssetListMiniEntity, new TranferDetailsAssetListMiniEntity.UltronRestClientInterface() {
            @Override
            public void onInitialize(TranferDetailsAssetListMiniEntity tranferDetailsAssetListMiniEntity, VolleyError error) {
                if(error == null)
                {
                    if (tranferDetailsAssetListMiniEntity.getResponse()!=null)
                    {
                        EntityGroup entityGroup = tranferDetailsAssetListMiniEntity.getResponse();
                        TransferDetailsAssetListEntity entity = entityGroup.getRequestDetails();
                        Log.d("qwerty",entity.getRequestType()+"");
                        tvRequestId.setText(""+(entity.getRequestId()));
                        tvRequestType.setText(entity.getRequestType());
                        tvStatus.setText(entity.getStatus());
                        tvCategoryName.setText(entity.getCategoryName());
                        skuAssetDetails = entity.getAssetDetails();
                        if(skuAssetDetails.size() == 0)
                        {

                        }
                        else
                        {
                            adapter = new SkuAssetDetailsAdapter(TransferAssetRequestDetailsActivity.this,skuAssetDetails);
                            rvTransferAssets.setAdapter(adapter);
                            rvTransferAssets.hasFixedSize();
                            adapter.notifyDataSetChanged();
                            if(entity.getStatus().equals("WAITING FOR USER RECEIVAL"))
                                bAfterAdminApproval.setVisibility(View.VISIBLE);
                            else if(entity.getStatus().equals("WAITING FOR RM APPROVAL"))
                                bBeforeRmApproval.setVisibility(View.VISIBLE);

                        }
                    }
                    else{
                        if(tranferDetailsAssetListMiniEntity.getStatusCode()==401)
                        {
                            CommonFunctions.toastString("Unauthoeiszed",TransferAssetRequestDetailsActivity.this);
                        }
                    }

                }
            }
        },TransferAssetRequestDetailsActivity.this);

    }
}
