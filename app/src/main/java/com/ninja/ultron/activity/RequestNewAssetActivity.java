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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ninja.ultron.R;
import com.ninja.ultron.adapter.NewAssetAdapter;
import com.ninja.ultron.entity.NewAssetEntity;

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
    int categorySelected;
    int assetSelected;
    NewAssetAdapter newAssetAdapter;
    RelativeLayout rlInitiateButton;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView newAssetRecyclerView;
    List<NewAssetEntity> newAssetEntityArrayList;
    Spinner assetTypeSpinner;
    Spinner categoryTypeSpinner;
    AlertDialog.Builder alertDialogBuilder = null;
    AlertDialog alertDialog = null;

    int Quantity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_new_asset);
        newAssetRecyclerView = (RecyclerView)findViewById(R.id.rvNewAsset);
        newAssetRecyclerView.hasFixedSize();
        layoutManager = new LinearLayoutManager(this);
        newAssetRecyclerView.setLayoutManager(layoutManager);
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

        final String[] profileTypeList ={"Select Asset Type","Laptop", "Chair", "Table", "Mobile"};
        final String[] facilityTypeList = {"Select Asset Type","Printer","Scanner"};


        assetTypeSpinner = (Spinner) findViewById(R.id.spinnerAssetType);
        final ArrayAdapter<String> profileTypeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, profileTypeList);
        final ArrayAdapter<String> facilityTypeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, facilityTypeList);
        assetTypeSpinner.setEnabled(false);
        ivIncreement.setEnabled(false);
        ivDecreement.setEnabled(false);
        categoryTypeSpinner.setSelection(0);
        String[] categoryTypeList ={"Select Category","Profile", "Facility"};
        ArrayAdapter<String> categoryTypeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,categoryTypeList);
        categoryTypeSpinner.setAdapter(categoryTypeAdapter);

        categoryTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryType = parent.getItemAtPosition(position).toString();
                categorySelected = parent.getSelectedItemPosition();
                if(categorySelected == 2) {
                    assetTypeSpinner.setAdapter(facilityTypeAdapter);
                    assetTypeSpinner.setEnabled(true);
                    ivIncreement.setEnabled(true);
                    ivDecreement.setEnabled(true);
                }
                else if(categorySelected ==1) {
                    assetTypeSpinner.setAdapter(profileTypeAdapter);
                    assetTypeSpinner.setEnabled(true);
                    ivIncreement.setEnabled(true);
                    ivDecreement.setEnabled(true);
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


        assetTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                assetType = parent.getItemAtPosition(position).toString();
                assetSelected = parent.getSelectedItemPosition();
                Quantity = 0;
                tvQuantity.setText(String.valueOf(Quantity));
                if(assetSelected != 0)
                {
                    ivIncreement.setEnabled(true);
                    ivDecreement.setEnabled(true);

                }
                else
                {
                    ivIncreement.setEnabled(false);
                    ivDecreement.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(RequestNewAssetActivity.this,"Select Asset type",Toast.LENGTH_SHORT);
            }
        });

        tvQuantity.setText(String.valueOf(Quantity));
        ivIncreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Quantity++;
                tvQuantity.setText(String.valueOf(Quantity));
            }
        });

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
                if(Quantity == 0) {
                    Toast.makeText(RequestNewAssetActivity.this,"Invalid Request",Toast.LENGTH_SHORT).show();
                }
                else {
                    NewAssetEntity entity = new NewAssetEntity(assetType, Quantity);
                    newAssetEntityArrayList.add(entity);
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
                    newAssetSummaryIntent.putExtra("NewAssetList",(Serializable)newAssetEntityArrayList);
                    newAssetSummaryIntent.putExtra("CategoryId",categorySelected);
                    startActivity(newAssetSummaryIntent);
                }
            }
        });
    }

    public void deleteSelectedAsset(final int position){
        String assetType = newAssetEntityArrayList.get(position).getAssetType();
        alertDialogBuilder = new AlertDialog.Builder(RequestNewAssetActivity.this, R.style.AlertDialogBackground);
        alertDialogBuilder
                .setMessage("Do you want to delete this (" + assetType +") ? ")
                .setCancelable(true)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                newAssetEntityArrayList.remove(newAssetEntityArrayList.get(position));
                                newAssetAdapter.notifyDataSetChanged();
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


}
