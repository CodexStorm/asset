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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ninja.ultron.R;
import com.ninja.ultron.adapter.SkuAssetDetailsAdapter;
import com.ninja.ultron.adapter.TransferAssetDetailsAdapter;
import com.ninja.ultron.entity.EntityGroup;
import com.ninja.ultron.entity.SkuAssetDetailsEntity;
import com.ninja.ultron.entity.TranferDetailsAssetListMiniEntity;
import com.ninja.ultron.entity.TransferAssetTypeDetailsEntity;
import com.ninja.ultron.entity.TransferDetailsAssetListEntity;
import com.ninja.ultron.functions.CommonFunctions;
import com.ninja.ultron.restclient.RestClientImplementation;

import java.util.ArrayList;
import java.util.List;

public class TransferAssetRequestDetailsActivity extends AppCompatActivity {


    List<TransferDetailsAssetListEntity> transferDetailsAssetListEntities;
    TransferDetailsAssetListEntity entity;
    List<SkuAssetDetailsEntity> skuAssetDetails;
    List<TransferAssetTypeDetailsEntity> transferAssetTypeDetailsEntities;
    String nomenclature;
    String AssetMake;
    String AssetType;
    String details[];

    TextView tvRequestId;
    TextView tvCategoryName;
    TextView tvStatus;
    TextView tvRequestType;
    RecyclerView rvTransferAssets;
    TransferAssetDetailsAdapter adapter;
    LinearLayout bBeforeRmApproval;
    LinearLayout bAfterAdminApproval;
    Button bEdit;
    Button bDelete;
    Button bAccept;
    Button bReject;
    AlertDialog.Builder alertDialogBuilder = null;
    AlertDialog alertDialog = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_asset_request_details);
        tvRequestId = (TextView) findViewById(R.id.tvRequestId);
        tvCategoryName = (TextView) findViewById(R.id.tvCategoryName);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        tvRequestType = (TextView) findViewById(R.id.tvRequestType);
        rvTransferAssets = (RecyclerView) findViewById(R.id.rvTransferAssets);
        bBeforeRmApproval = (LinearLayout) findViewById(R.id.bBeforeRmApproval);
        bAfterAdminApproval = (LinearLayout) findViewById(R.id.bAfterAdminApproval);
        bEdit = (Button) findViewById(R.id.bEdit);
        bDelete = (Button) findViewById(R.id.bDelete);
        bAccept = (Button) findViewById(R.id.bAccept);
        bReject = (Button) findViewById(R.id.bReject);
        transferAssetTypeDetailsEntities = new ArrayList<>();
        transferDetailsAssetListEntities = new ArrayList<>();
        skuAssetDetails = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(TransferAssetRequestDetailsActivity.this);
        rvTransferAssets.setLayoutManager(manager);
        alertDialogBuilder = new AlertDialog.Builder(TransferAssetRequestDetailsActivity.this, R.style.AlertDialogBackground);
        final Intent intent = new Intent(TransferAssetRequestDetailsActivity.this, AssetActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        callGetDetailsApi();

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

                            for(int i =0 ; i<skuAssetDetails.size();i++)
                            {
                                AssetType = skuAssetDetails.get(i).getSkuName();
                                int size = skuAssetDetails.get(i).getList().size();
                                for(int j = 0 ; j<size ; j++){
                                    nomenclature = skuAssetDetails.get(i).getList().get(j);
                                    details = new String[nomenclature.split("@").length];
                                    details = nomenclature.split("@");
                                    nomenclature = details[0];
                                    Log.d("NOME",nomenclature);
                                    TransferAssetTypeDetailsEntity object = new TransferAssetTypeDetailsEntity(AssetType,nomenclature);
                                    transferAssetTypeDetailsEntities.add(object);
                                }
                            }
                            adapter = new TransferAssetDetailsAdapter(transferAssetTypeDetailsEntities);
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
