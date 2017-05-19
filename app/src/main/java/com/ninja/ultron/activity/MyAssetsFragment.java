package com.ninja.ultron.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ninja.ultron.R;
import com.ninja.ultron.adapter.AssetRecyclerAdapter;
import com.ninja.ultron.entity.AssetMiniEntity;
import com.ninja.ultron.entity.CodeDecodeEntity;
import com.ninja.ultron.functions.CommonFunctions;
import com.ninja.ultron.functions.UserDetails;
import com.ninja.ultron.restclient.RestClientImplementation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prabhu Sivanandam on 18-May-17.
 */

public class MyAssetsFragment extends Fragment {


    List<CodeDecodeEntity> myAssetList=new ArrayList<>();
    RecyclerView recyclerView;
    AssetRecyclerAdapter adapter;
    AssetMiniEntity assetMini;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_my_assets,container,false);
        recyclerView=(RecyclerView)v.findViewById(R.id.rvMyAssets);
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        callMyAssetListApi();
        Log.d("val",UserDetails.getMyAssetList(getActivity()));
        myAssetList = (new Gson()).fromJson(UserDetails.getMyAssetList(getActivity()),new TypeToken<ArrayList<CodeDecodeEntity>>(){}.getType());
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
                        myAssetList = (new Gson()).fromJson(UserDetails.getMyAssetList(getActivity()),new TypeToken<ArrayList<CodeDecodeEntity>>(){}.getType());
                        adapter=new AssetRecyclerAdapter(myAssetList);
                        recyclerView.setAdapter(adapter);
                        recyclerView.hasFixedSize();
                        adapter.notifyDataSetChanged();

                    }else{
                        Log.d("","commited om");
                    }
                } else {
                    if(assetMiniEntity.getStausCode() == 401) {
                        CommonFunctions.toastString("Unauthorized",getActivity());
                    }
                }
            }
        },getActivity());
    }
}
