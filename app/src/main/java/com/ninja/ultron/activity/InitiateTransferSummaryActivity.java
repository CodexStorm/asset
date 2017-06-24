package com.ninja.ultron.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.ninja.ultron.R;
import com.ninja.ultron.adapter.TransferAssetAdapter;
import com.ninja.ultron.constant.Constants;
import com.ninja.ultron.entity.CodeDecodeEntity;
import com.ninja.ultron.entity.InitiateTransferEntity;
import com.ninja.ultron.functions.UserDetails;
import com.ninja.ultron.restclient.RestClientImplementation;

import java.util.ArrayList;
import java.util.List;

public class InitiateTransferSummaryActivity extends AppCompatActivity {

    RecyclerView.LayoutManager layoutManager;
    RecyclerView transferAssetRecyclerView;
    TransferAssetAdapter transferAssetAdapter;
    TextView tvRequestedBy;
    TextView tvFacility;
    TextView tvhFacility;
    TextView tvRequestReason;
    TextView tvRequestedTo;
    List<Integer>  selectedAssetId;
    RelativeLayout rlInitateTransfer;
    InitiateTransferEntity initiateTransferEntity;
    int categoryId;
    String TransferTo;
    int reasonId;
    AlertDialog.Builder alertDialogBuilder = null;
    AlertDialog alertDialog = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initiate_transfer_summary);

        rlInitateTransfer = (RelativeLayout)findViewById(R.id.rlInitiateButton);
        tvRequestedBy = (TextView)findViewById(R.id.tvRequestedBy);
        tvFacility = (TextView)findViewById(R.id.Facility);
        tvFacility.setText(UserDetails.getFacilityName(InitiateTransferSummaryActivity.this));
        tvhFacility = (TextView)findViewById(R.id.tvhFaciltity);
        tvRequestedTo = (TextView)findViewById(R.id.tvRequestedto);
        tvRequestReason = (TextView)findViewById(R.id.tvRequestReason);
        Intent transferAssetSummary = getIntent();
        categoryId = transferAssetSummary.getIntExtra("category",0);
        reasonId = transferAssetSummary.getIntExtra("RequestReasonId",0);
        TransferTo = transferAssetSummary.getStringExtra("TransferTo");
        List<CodeDecodeEntity> transferAssetEntityList =(List<CodeDecodeEntity>) transferAssetSummary.getSerializableExtra("TransferAssetList");
        selectedAssetId = (List<Integer>)transferAssetSummary.getSerializableExtra("SelectedAssetId");

        alertDialogBuilder = new AlertDialog.Builder(InitiateTransferSummaryActivity.this, R.style.AlertDialogBackground);
        final Intent intent = new Intent(InitiateTransferSummaryActivity.this, AssetActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        tvRequestedBy.setText(UserDetails.getUserName(InitiateTransferSummaryActivity.this));
        tvRequestReason.setText(transferAssetSummary.getStringExtra("RequestReason"));
        tvRequestedTo.setText(transferAssetSummary.getStringExtra("TransferTo"));

        if(transferAssetSummary.getIntExtra("category",0) == 2) {
            tvFacility.setVisibility(View.VISIBLE);
            tvhFacility.setVisibility(View.VISIBLE);
        }

        createTransferEntity();
        transferAssetRecyclerView = (RecyclerView)findViewById(R.id.rvTransferAssets);
        transferAssetRecyclerView.hasFixedSize();
        layoutManager = new LinearLayoutManager(this);
        transferAssetRecyclerView.setLayoutManager(layoutManager);
        transferAssetAdapter = new TransferAssetAdapter(InitiateTransferSummaryActivity.this, transferAssetEntityList);
        transferAssetRecyclerView.setAdapter(transferAssetAdapter);
        transferAssetAdapter.notifyDataSetChanged();

        rlInitateTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                        alertDialogBuilder
                                .setMessage("Are you sure do you want to Delete  the selected assets ")
                                .setCancelable(true)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                callInitiateTransferApi();
                                                startActivity(intent);
                                                alertDialog.dismiss();
                                            }
                                        })
                                .setNegativeButton("No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                alertDialog.dismiss();
                                            }
                                        });
                        alertDialog = alertDialogBuilder.create();
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
                        alertDialog.show();

            }
        });

    }

    private void callInitiateTransferApi() {
        RestClientImplementation.initiateTranferApi(initiateTransferEntity, new InitiateTransferEntity.UltronRestClientInterface() {
            @Override
            public void onInitialize(InitiateTransferEntity initiateTransferEntity, VolleyError error) {
                if(error == null)
                {

                }
            }
        },InitiateTransferSummaryActivity.this);
    }

    private void createTransferEntity() {
        int userId = UserDetails.getAsgardUserId(InitiateTransferSummaryActivity.this);
        int assetIssueTypeId = Constants.TRANSFER_ISSUE_ID;
        int assetSkuCategoryId = categoryId ;
        String transferTo = TransferTo;
        int facilityId = UserDetails.getFacilityId(InitiateTransferSummaryActivity.this);
        int transferReasonsId =reasonId;
        initiateTransferEntity = new InitiateTransferEntity();
        initiateTransferEntity.setUserId(userId);
        initiateTransferEntity.setAssetList(selectedAssetId);
        initiateTransferEntity.setAssetIssueTypeId(assetIssueTypeId);
        initiateTransferEntity.setAssetSkuCategoryId(assetSkuCategoryId);
        initiateTransferEntity.setTransferTo(transferTo);
        initiateTransferEntity.setFacilityId(facilityId);
        initiateTransferEntity.setTransferReasonId(reasonId);
    }


}
