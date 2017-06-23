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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
    LinearLayout bBeforeRmApproval;
    LinearLayout bAfterAdminApproval;
    Button bEdit;
    Button bDelete;
    Button bAccept;
    Button bReject;
    AlertDialog.Builder alertDialogBuilder = null;
    AlertDialog alertDialog = null;
    ProgressBar centreProgressBar;
    RelativeLayout rlProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_asset_requeest_details);

        tvRequestId = (TextView)findViewById(R.id.tvRequestId);
        tvCategoryName = (TextView)findViewById(R.id.tvCategoryName);
        tvStatus = (TextView)findViewById(R.id.tvStatus);
        tvRequestType = (TextView)findViewById(R.id.tvRequestType);
        rvNewAssets = (RecyclerView)findViewById(R.id.rvNewAssets);
        rlProgress = (RelativeLayout)findViewById(R.id.rlProgress);
        centreProgressBar = (ProgressBar)findViewById(R.id.centreProgressBar);
        bBeforeRmApproval = (LinearLayout)findViewById(R.id.bBeforeRmApproval);
        bAfterAdminApproval = (LinearLayout)findViewById(R.id.bAfterAdminApproval);
        bEdit = (Button)findViewById(R.id.bEdit);
        bDelete = (Button)findViewById(R.id.bDelete);
        bAccept = (Button)findViewById(R.id.bAccept);
        bReject = (Button)findViewById(R.id.bReject);
        assetDetails = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(NewAssetRequeestDetailsActivity.this);
        rvNewAssets.setLayoutManager(manager);
        alertDialogBuilder = new AlertDialog.Builder(NewAssetRequeestDetailsActivity.this, R.style.AlertDialogBackground);
        final Intent intent = new Intent(NewAssetRequeestDetailsActivity.this, AssetActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        callNewAssetRequestDetailsApi();

        bAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogBuilder
                        .setMessage("Are you sure do you want to accept the selected assets ")
                        .setCancelable(true)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
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

        bReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogBuilder
                        .setMessage("Are you sure do you want to reject the selected assets ")
                        .setCancelable(true)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
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

        /*bEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogBuilder
                        .setMessage("Are you sure do you want to accept the selected assets ")
                        .setCancelable(true)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
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
        });*/

        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogBuilder
                        .setMessage("Are you sure do you want to Delete  the selected assets ")
                        .setCancelable(true)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
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

    private void callNewAssetRequestDetailsApi() {
        entity = new NewAssetEntityGroup();
        rlProgress.setVisibility(View.VISIBLE);
        centreProgressBar.setVisibility(View.VISIBLE);
        NewAssetMiniEntityGroup newAssetMiniEntityGroup = new NewAssetMiniEntityGroup();
        RestClientImplementation.getNewAssetDetailsApi(newAssetMiniEntityGroup, new NewAssetMiniEntityGroup.UltronRestClientInterface() {
            @Override
            public void onInitialize(NewAssetMiniEntityGroup newAssetMiniEntityGroup, VolleyError error) {
                if(error == null)
                {
                    if(newAssetMiniEntityGroup.getResponse()!=null)
                    {
                        centreProgressBar.setVisibility(View.GONE);
                        rlProgress.setVisibility(View.GONE);
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
                            if(entity.getRequestDetails().getStatus().equals("WAITING FOR USER RECEIVAL"))
                                bAfterAdminApproval.setVisibility(View.VISIBLE);
                            else if(entity.getRequestDetails().getStatus().equals("WAITING FOR RM APPROVAL"))
                                bBeforeRmApproval.setVisibility(View.VISIBLE);
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
