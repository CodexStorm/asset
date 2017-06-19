package com.ninja.ultron.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ninja.ultron.R;
import com.ninja.ultron.activity.InitiateTransferSummaryActivity;
import com.ninja.ultron.adapter.TransferListRecyclerAdapter;
import com.ninja.ultron.entity.AssetMiniEntity;
import com.ninja.ultron.entity.CodeDecodeEntity;
import com.ninja.ultron.functions.CommonFunctions;
import com.ninja.ultron.functions.UserDetails;
import com.ninja.ultron.restclient.RestClientImplementation;

import java.util.ArrayList;
import java.util.List;


public class FacilityAssetTransferFragment extends Fragment {

    RecyclerView recyclerView;
    List<CodeDecodeEntity> myAssetList=new ArrayList<>();
    TransferListRecyclerAdapter adapter;
    RelativeLayout rlInitiateButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile_asset_transfer, container, false);
        recyclerView=(RecyclerView)v.findViewById(R.id.rvMyAssets);
        rlInitiateButton = (RelativeLayout)v.findViewById(R.id.rlInitiateButton);
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        callMyAssetListApi();
        Log.d("val", UserDetails.getMyAssetList(getActivity()));
        myAssetList = (new Gson()).fromJson(UserDetails.getMyProfileAssetList(getActivity()),new TypeToken<ArrayList<CodeDecodeEntity>>(){}.getType());
        rlInitiateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent assetTransferSummary = new Intent(getContext(), InitiateTransferSummaryActivity.class);
                assetTransferSummary.putExtra("category",2);
                startActivity(assetTransferSummary);

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
                        adapter=new TransferListRecyclerAdapter(myAssetList, getContext());
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
        },getActivity());
    }
}
