package com.ninja.ultron.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ninja.ultron.R;
import com.ninja.ultron.activity.AssetDetailsActivity;
import com.ninja.ultron.activity.InitiateAssetTransferActivity;
import com.ninja.ultron.activity.RequestNewAssetActivity;
import com.ninja.ultron.adapter.AssetListRecyclerAdapter;
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
    AssetListRecyclerAdapter adapter;
    BottomNavigationView bottomNavigationView;
    ProgressBar centreProgressBar;
    RelativeLayout rlProgress;
    SwipeRefreshLayout swipe;
    TextView tvRefresh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_my_assets,container,false);
        recyclerView=(RecyclerView)v.findViewById(R.id.rvMyAssets);
        swipe = (SwipeRefreshLayout)v.findViewById(R.id.swipe);
        tvRefresh = (TextView)v.findViewById(R.id.tvRefresh);
        rlProgress = (RelativeLayout)v.findViewById(R.id.rlProgress);
        centreProgressBar = (ProgressBar)v.findViewById(R.id.centreProgressBar);
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        bottomNavigationView = (BottomNavigationView)v.findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_request:
                        Intent initatAssetTransferIntent = new Intent(getActivity(),InitiateAssetTransferActivity.class);
                        startActivity(initatAssetTransferIntent);
                        //initiateTransferFragment.selectedToName = "Admin";
                        break;
                    case R.id.request_asset:
                        Intent requestNewAssetIntent = new Intent(getActivity(), RequestNewAssetActivity.class);
                        startActivity(requestNewAssetIntent);

                        break;

                }
                return true;
            }
        });

        tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CommonFunctions.isNetworkAvailable(getActivity())) {
                    callMyAssetListApi();
                    tvRefresh.setVisibility(View.GONE);
                    rlProgress.setVisibility(View.GONE);
                }
                else
                {
                    Toast.makeText(getContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }
            }
        });

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(CommonFunctions.isNetworkAvailable(getActivity())) {
                    callMyAssetListApi();
                    tvRefresh.setVisibility(View.VISIBLE);
                    swipe.setRefreshing(false);
                }
                else
                {
                    Toast.makeText(getContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                    swipe.setRefreshing(false);
                    tvRefresh.setVisibility(View.GONE);
                }
            }
        });
        if(CommonFunctions.isNetworkAvailable(getActivity())) {
            callMyAssetListApi();
        }
        else
        {
            Toast.makeText(getContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
            rlProgress.setVisibility(View.VISIBLE);
            tvRefresh.setVisibility(View.VISIBLE);
        }
        Log.d("val",UserDetails.getMyAssetList(getActivity()));
        myAssetList = (new Gson()).fromJson(UserDetails.getMyAssetList(getActivity()),new TypeToken<ArrayList<CodeDecodeEntity>>(){}.getType());
        return v;
    }

    private void callMyAssetListApi() {
        AssetMiniEntity assetMiniEntity = new AssetMiniEntity();
        rlProgress.setVisibility(View.VISIBLE);
        centreProgressBar.setVisibility(View.VISIBLE);

        RestClientImplementation.assetListApi(assetMiniEntity, new AssetMiniEntity.UltronRestClientInterface() {
            @Override//return call
            public void onInitialize(AssetMiniEntity assetMiniEntity, VolleyError error) {
                if(error == null) {
                    if(assetMiniEntity.getResponse() != null) {
                        Gson gs = new Gson();
                        centreProgressBar.setVisibility(View.GONE);
                        rlProgress.setVisibility(View.GONE);
                        myAssetList = assetMiniEntity.getResponse();
                        Log.d("Check",myAssetList.toString());
                        String myAssetListAsString = gs.toJson(myAssetList);
                        Log.d("Check",myAssetListAsString);
                        UserDetails.setMyAssetList(getContext(),myAssetListAsString);
                        adapter=new AssetListRecyclerAdapter(myAssetList, getContext(), new AssetListRecyclerAdapter.CallBack() {
                            @Override
                            public void CallAssetDetailsFragment(int id,String name,String toName) {
                                Log.d("dascsa",name+"  "+toName+"  "+id);
                                //AssetDetailsFragment fragment = new AssetDetailsFragment();
                                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.rlMyAssetList,fragment).addToBackStack(null).commit();
                                Intent intent=new Intent(getActivity(), AssetDetailsActivity.class);
                                startActivity(intent);
                            }
                        });
                        recyclerView.hasFixedSize();
                        adapter.notifyDataSetChanged();
                        Log.d("Check",myAssetList.size()+"");
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
        },getActivity(),"");
    }

}
