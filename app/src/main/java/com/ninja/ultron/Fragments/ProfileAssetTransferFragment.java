package com.ninja.ultron.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ninja.ultron.R;
import com.ninja.ultron.activity.AssetDetailsActivity;
import com.ninja.ultron.activity.InitiateAssetTransferActivity;
import com.ninja.ultron.activity.InitiateTransferSummaryActivity;
import com.ninja.ultron.adapter.AssetListRecyclerAdapter;
import com.ninja.ultron.adapter.TransferListRecyclerAdapter;
import com.ninja.ultron.constant.Constants;
import com.ninja.ultron.entity.AssetMiniEntity;
import com.ninja.ultron.entity.CodeDecodeEntity;
import com.ninja.ultron.functions.CommonFunctions;
import com.ninja.ultron.functions.UserDetails;
import com.ninja.ultron.restclient.RestClientImplementation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProfileAssetTransferFragment extends Fragment {

    RecyclerView recyclerView;
    List<CodeDecodeEntity> myAssetList=new ArrayList<>();
    List<CodeDecodeEntity> selectedAssetList=new ArrayList<>();
    TransferListRecyclerAdapter adapter;
    RelativeLayout rlInitiateButton;
    Spinner spinnerRequestReason;
    String RequestReasonText;
    int RequestReasonId;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile_asset_transfer, container, false);
        recyclerView=(RecyclerView)v.findViewById(R.id.rvMyAssets);
        rlInitiateButton = (RelativeLayout)v.findViewById(R.id.rlInitiateButton);
        spinnerRequestReason = (Spinner)v.findViewById(R.id.spinnerRequestReason);
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        final String[] RequestReason = {"Select Reason","Changing Department"};
        final ArrayAdapter<String> requestReasonAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,RequestReason);
        spinnerRequestReason.setAdapter(requestReasonAdapter);

        spinnerRequestReason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                RequestReasonText = parent.getItemAtPosition(position).toString();
                RequestReasonId = parent.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        recyclerView.setLayoutManager(manager);
        callMyAssetListApi();
        Log.d("val",UserDetails.getMyAssetList(getActivity()));
        myAssetList = (new Gson()).fromJson(UserDetails.getMyProfileAssetList(getActivity()),new TypeToken<ArrayList<CodeDecodeEntity>>(){}.getType());
        rlInitiateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(RequestReasonId == 0) {
                    Toast.makeText(getContext(),"Invalid Request",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent assetTransferSummary = new Intent(getContext(), InitiateTransferSummaryActivity.class);
                    selectedAssetList = adapter.getSelectedAssetList();
                    assetTransferSummary.putExtra("category", 1);
                    assetTransferSummary.putExtra("RequestReason",RequestReasonText);
                    assetTransferSummary.putExtra("TransferTo","Admin");
                    assetTransferSummary.putExtra("TransferAssetList",(Serializable)selectedAssetList);
                    startActivity(assetTransferSummary);
                }
            }
        });
        return v;


    }

    private void callMyAssetListApi() {
        AssetMiniEntity assetMiniEntity = new AssetMiniEntity();
        RestClientImplementation.assetListApi(assetMiniEntity, new AssetMiniEntity.UltronRestClientInterface() {
            @Override//return call
            public void onInitialize(AssetMiniEntity assetMiniEntity, VolleyError error) {
                if(error == null) {
                    if(assetMiniEntity.getResponse() != null) {
                        Gson gs = new Gson();
                        myAssetList = assetMiniEntity.getResponse();
                        String myAssetListAsString = gs.toJson(myAssetList);
                        UserDetails.setMyAssetList(getContext(),myAssetListAsString);
                        adapter=new TransferListRecyclerAdapter (myAssetList, getContext(),1);
                        recyclerView.hasFixedSize();
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);
                    }else{
                        Log.d("","commited");
                    }
                } else {
                    if(assetMiniEntity.getStatusCode() == 401) {
                        CommonFunctions.toastString("Unauthorized",getActivity());
                    }
                }
            }
        },getActivity(), Constants.PROFILE_ASSET_LIST);
    }
}
