package com.ninja.ultron.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.ninja.ultron.R;
import com.ninja.ultron.adapter.NewAssetAdapter;
import com.ninja.ultron.entity.AssetTypeEntity;
import com.ninja.ultron.entity.AssetTypeMiniEntity;
import com.ninja.ultron.entity.NewAssetEntity;
import com.ninja.ultron.functions.CommonFunctions;
import com.ninja.ultron.restclient.RestClientImplementation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RequestNewAssetActivity extends AppCompatActivity {

    ImageView ivIncreement;
    ImageView ivDecreement;
    TextView tvQuantity;
    TextView tvAssetType;
    TextView bAdd;
    String assetType;
    String categoryType;
    int category=0;
    int categorySelected =0;
    int assetSelected;
    int assetTypeId;
    int selected = 0;
    int previousSelected = 0;
    NewAssetAdapter newAssetAdapter;
    RelativeLayout rlInitiateButton;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView newAssetRecyclerView;
    List<NewAssetEntity> newAssetEntityArrayList;
    Spinner assetTypeSpinner;
    Spinner categoryTypeSpinner;
    AlertDialog.Builder alertDialogBuilder = null;
    AlertDialog alertDialog = null;
    AssetTypeMiniEntity assetTypeMiniEntity;
    List<Integer> assetTypeIdList;
    List<String> assetTypeNameList;
    List<String> assetTypes;
    List<AssetTypeEntity> assetTypeEntities;
    int Quantity;
    int categoryId;
    RelativeLayout rlAssetDetails;
    LayoutInflater inflater;
    TextView listQuantity;
    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_new_asset);
        assetTypeMiniEntity = new AssetTypeMiniEntity();
        newAssetRecyclerView = (RecyclerView)findViewById(R.id.rvNewAsset);
        newAssetRecyclerView.hasFixedSize();
        listQuantity = (TextView)findViewById(R.id.listQuantity);
        layoutManager = new LinearLayoutManager(this);
        back = (ImageButton)findViewById(R.id.back);
        newAssetRecyclerView.setLayoutManager(layoutManager);
        rlAssetDetails = (RelativeLayout)findViewById(R.id.rlAssetDetails);
        bAdd = (TextView)findViewById(R.id.bAdd);
        tvAssetType = (TextView)findViewById(R.id.tvAssetType);
        ivIncreement = (ImageView)findViewById(R.id.ivIncreement);
        ivDecreement = (ImageView)findViewById(R.id.ivDecreement);
        tvQuantity = (TextView)findViewById(R.id.tvQuantity);
        rlInitiateButton = (RelativeLayout)findViewById(R.id.rlInitiateButton);
        categoryTypeSpinner = (Spinner)findViewById(R.id.spinnerCategoryType);
        Quantity = 0;
        newAssetEntityArrayList = new ArrayList<>();
        categorySelected =0;
        assetSelected = 4;
        previousSelected =0;
        assetTypeIdList = new ArrayList<>();
        assetTypeNameList = new ArrayList<>();

        alertDialogBuilder = new AlertDialog.Builder(RequestNewAssetActivity.this, R.style.AlertDialogBackground);

        assetTypeSpinner = (Spinner) findViewById(R.id.spinnerAssetType);
        assetTypeSpinner.setEnabled(false);
        ivIncreement.setEnabled(false);
        ivDecreement.setEnabled(false);
        categoryTypeSpinner.setSelection(0);
        String[] categoryTypeList ={"Select Category","Profile", "Facility"};
        final ArrayAdapter<String> categoryTypeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,categoryTypeList);
        categoryTypeSpinner.setAdapter(categoryTypeAdapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        rlAssetDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(categorySelected==0)
                {
                    Toast.makeText(RequestNewAssetActivity.this,"Select Asset category",Toast.LENGTH_SHORT).show();
                }
            }
        });

        categoryTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryType = parent.getItemAtPosition(position).toString();
                categorySelected = parent.getSelectedItemPosition();
                if(categorySelected == 2) {
                    category = 3002;
                    if(previousSelected == 0)
                    {
                        previousSelected = categorySelected;
                    }
                    else if(previousSelected != categorySelected){
                        alertDialogBuilder
                                .setMessage("Your data will be lost. Are you sure ? ")
                                .setCancelable(true)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                previousSelected = categorySelected;
                                                if(newAssetEntityArrayList.size()!=0){
                                                    newAssetEntityArrayList.clear();
                                                    newAssetAdapter.notifyDataSetChanged();
                                                }
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
                    categoryId = 3002;
                    assetTypeSpinner.setEnabled(true);
                    ivIncreement.setEnabled(true);
                    ivDecreement.setEnabled(true);
                    callAssetTypeApi();

                }
                else if(categorySelected ==1) {
                    category = 3001;
                    if(previousSelected == 0)
                    {
                        previousSelected = categorySelected;
                    }
                    else if(previousSelected != categorySelected){
                        alertDialogBuilder
                                .setMessage("Your data will be lost. Are you sure ? ")
                                .setCancelable(true)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                previousSelected = categorySelected;
                                                if(newAssetEntityArrayList.size()!=0){
                                                    newAssetEntityArrayList.clear();
                                                    newAssetAdapter.notifyDataSetChanged();
                                                    listQuantity.setText(0+"");
                                                }
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
                    categoryId = 3001;
                    assetTypeSpinner.setEnabled(true);
                    ivIncreement.setEnabled(true);
                    ivDecreement.setEnabled(true);
                    callAssetTypeApi();
                }
                else {
                    Toast.makeText(RequestNewAssetActivity.this, "Select Category type", Toast.LENGTH_SHORT).show();
                    assetTypeSpinner.setEnabled(false);
                    ivIncreement.setEnabled(false);
                    ivDecreement.setEnabled(false);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        callAssetTypeApi();




        ivDecreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Quantity<1){
                    Toast.makeText(RequestNewAssetActivity.this,"Quantity cannot be less than zero",Toast.LENGTH_SHORT);
                }
                else {
                    Quantity--;
                    tvQuantity.setText(String.valueOf(Quantity));

                }
            }
        });



        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Quantity == 0)
                {
                    Toast.makeText(RequestNewAssetActivity.this,"Select  Quantity",Toast.LENGTH_SHORT).show();

                }
                else if(assetTypeIdList.contains(assetTypeId))
                {
                    for(int i = 0 ; i< newAssetEntityArrayList.size(); i++)
                        if(assetType == newAssetEntityArrayList.get(i).getAssetType()){
                            int q = newAssetEntityArrayList.get(i).getQuantity();
                            int x = q + Quantity;
                            newAssetEntityArrayList.get(i).setQuantity(x);
                            newAssetAdapter = new NewAssetAdapter(RequestNewAssetActivity.this,newAssetEntityArrayList);
                            newAssetRecyclerView.setAdapter(newAssetAdapter);
                            newAssetAdapter.notifyDataSetChanged();

                            break;
                        }
                }


                else {
                    NewAssetEntity entity = new NewAssetEntity(assetType, Quantity);
                    newAssetEntityArrayList.add(entity);
                    assetTypeIdList.add(assetTypeId);
                    assetTypeNameList.add(assetType);
                    listQuantity.setText(assetTypeIdList.size()+"");
                    Log.d("idlist",assetTypeIdList+"");
                    Log.d("assetList",assetType);
                    newAssetAdapter = new NewAssetAdapter(RequestNewAssetActivity.this,newAssetEntityArrayList);
                    newAssetRecyclerView.setAdapter(newAssetAdapter);
                    newAssetAdapter.notifyDataSetChanged();
                }
            }
        });

       rlInitiateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newAssetEntityArrayList.size()==0)
                {
                    Toast.makeText(RequestNewAssetActivity.this,"No new Assets requested",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent newAssetSummaryIntent = new Intent(RequestNewAssetActivity.this,NewAssetSummaryActivity.class);
                    newAssetSummaryIntent.putExtra("AssetTypeId",(Serializable)assetTypeIdList);
                    newAssetSummaryIntent.putExtra("AssetNameList",(Serializable)assetTypeNameList);
                    Log.d("List",assetTypeNameList+"");
                    newAssetSummaryIntent.putExtra("NewAssetList",(Serializable)newAssetEntityArrayList);
                    newAssetSummaryIntent.putExtra("CategoryId",category);
                    startActivity(newAssetSummaryIntent);
                }
            }
        });


    }

    private void callAssetTypeApi() {
        RestClientImplementation.getAssetTypeApi(assetTypeMiniEntity, new AssetTypeMiniEntity.UltronRestClientInterface() {
            @Override
            public void onInitialize(AssetTypeMiniEntity assetTypeMiniEntity, VolleyError error) {
                if(error==null)
                {
                    if(assetTypeMiniEntity.getResponse()!=null)
                    {
                        assetTypes = new ArrayList<String>();
                        assetTypeEntities = assetTypeMiniEntity.getResponse();
                        for(int i =0; i<assetTypeEntities.size();i++)
                        {
                            assetTypes.add(assetTypeEntities.get(i).getName());
                        }
                       // assetTypes.add("Select Asset Type");
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(RequestNewAssetActivity.this,android.R.layout.simple_spinner_item,assetTypes);
                        assetTypeSpinner.setAdapter(dataAdapter);
                        assetTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                Log.d("ID",id+"");
                                Log.d("position",position+"");
                                selected = position;
                                assetType = parent.getItemAtPosition(position).toString();
                                assetSelected = parent.getSelectedItemPosition();
                                assetTypeId = assetTypeEntities.get(position).getId();
                                Log.d("ChangedAssetId",assetTypeId+"");
                                Quantity = 0;
                                tvQuantity.setText(String.valueOf(Quantity));

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        onContentChanged();
                    }
                    else
                    {
                        Log.d("Tag","Erroro in Response");
                    }
                }
                else
                {
                    if(assetTypeMiniEntity.getStatusCode()==401)
                    {
                        CommonFunctions.toastString("UnAuthorized",RequestNewAssetActivity.this);
                    }
                }
            }
        },RequestNewAssetActivity.this,categoryId);
        tvQuantity.setText(String.valueOf(Quantity));
        ivIncreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Quantity++;
                tvQuantity.setText(String.valueOf(Quantity));
            }
        });


    }


    public void deleteSelectedAsset(final int position, final int num, final String AssetType){
        final String assetType = newAssetEntityArrayList.get(position).getAssetType();
        alertDialogBuilder = new AlertDialog.Builder(RequestNewAssetActivity.this);
        LayoutInflater inflater = RequestNewAssetActivity.this.getLayoutInflater();
        View v = inflater.inflate(R.layout.quanity_change_alert_dialogue,null,false);
        final TextView tvNum = (TextView)v.findViewById(R.id.tvQ);
        final int[] q = {num};
        tvNum.setText(q[0] +"");
        ImageView ivPlus = (ImageView)v.findViewById(R.id.ivPlus);
        ImageView ivMinus = (ImageView)v.findViewById(R.id.ivMinus);
        alertDialogBuilder.setView(v)
                .setCancelable(true)
                .setPositiveButton("Delete",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                for(int i = 0 ; i<assetTypeEntities.size();i++)
                                {
                                    if (AssetType.equals(assetTypeEntities.get(i).getName())){
                                        assetTypeIdList.remove(assetTypeIdList.indexOf(assetTypeEntities.get(i).getId()));
                                        break;
                                    }
                                }
                                newAssetEntityArrayList.remove(newAssetEntityArrayList.get(position));
                                newAssetAdapter.notifyDataSetChanged();
                                alertDialog.dismiss();
                            }
                        })
                .setNegativeButton("Edit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if(q[0] == 0){
                                    for(int i = 0 ; i<assetTypeEntities.size();i++)
                                    {
                                        if (AssetType.equals(assetTypeEntities.get(i).getName())){
                                            assetTypeIdList.remove(assetTypeIdList.indexOf(assetTypeEntities.get(i).getId()));
                                            break;
                                        }
                                    }
                                    newAssetEntityArrayList.remove(newAssetEntityArrayList.get(position));
                                }
                                else {
                                    newAssetEntityArrayList.get(position).setQuantity(q[0]);
                                }
                                newAssetAdapter.notifyDataSetChanged();
                                alertDialog.dismiss();
                            }
                        });
        alertDialog = alertDialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        alertDialog.show();

        ivPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                q[0]++;
                tvNum.setText(q[0] +"");
            }
        });

        ivMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q[0]>0){
                    q[0]--;
                    tvNum.setText(q[0] +"");
                }
            }
        });
    }


    public void onBackPressed() {

        alertDialogBuilder
                .setMessage("Your data will be lost. Are you sure ? ")
                .setCancelable(true)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(RequestNewAssetActivity.this, AssetActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
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

}


