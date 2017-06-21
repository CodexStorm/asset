package com.ninja.ultron.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.AtomicFile;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
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
import com.ninja.ultron.activity.InitiateTransferSummaryActivity;
import com.ninja.ultron.adapter.TransferAssetAdapter;
import com.ninja.ultron.adapter.TransferListRecyclerAdapter;
import com.ninja.ultron.constant.Constants;
import com.ninja.ultron.entity.AssetMiniEntity;
import com.ninja.ultron.entity.CodeDecodeEntity;
import com.ninja.ultron.entity.TransferReasonsEntity;
import com.ninja.ultron.entity.TransferReasonsMiniEntity;
import com.ninja.ultron.functions.CommonFunctions;
import com.ninja.ultron.functions.UserDetails;
import com.ninja.ultron.restclient.RestClientImplementation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class FacilityAssetTransferFragment extends Fragment {

    RecyclerView recyclerView;
    List<CodeDecodeEntity> myAssetList=new ArrayList<>();
    List<CodeDecodeEntity> selectedAssetList=new ArrayList<>();
    List<Integer> selectedAssetId = new ArrayList<>();
    TransferListRecyclerAdapter adapter;
    RelativeLayout rlInitiateButton;
    Spinner spinnerTransferTo;
    Spinner spinnerRequestReason;
    String TransferToText;
    String RequestReasonText;
    int TransferToId;
    int RequestReasonId;
    TransferReasonsMiniEntity transferReasonsMiniEntity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_facility_asset_transfer, container, false);
        recyclerView=(RecyclerView)v.findViewById(R.id.rvMyAssets);
        rlInitiateButton = (RelativeLayout)v.findViewById(R.id.rlInitiateButton);
        spinnerRequestReason=(Spinner)v.findViewById(R.id.spinnerRequestReason);
        spinnerTransferTo=(Spinner)v.findViewById(R.id.spinnerTransferTo);
        transferReasonsMiniEntity = new TransferReasonsMiniEntity();
        RequestReasonId =0;
        TransferToId = 0;
        final String[] TransferTo = {"Select To","ADMIN","Reporting Manager"};
        final ArrayAdapter<String> transferToAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,TransferTo);
        spinnerTransferTo.setAdapter(transferToAdapter);

        RestClientImplementation.getTransferReasonsApi(transferReasonsMiniEntity, new TransferReasonsMiniEntity.UltronRestClientInterface() {
            @Override
            public void onInitialize(TransferReasonsMiniEntity transferReasonsMiniEntity, VolleyError error) {
                if(error==null)
                {
                    if(transferReasonsMiniEntity.getReponse()!=null)
                    {
                        final List<String> reasons = new ArrayList<String>();
                        reasons.add("Select Reason");
                        final List<TransferReasonsEntity> transferReasonsEntities = transferReasonsMiniEntity.getReponse();
                        for(int i = 0; i<transferReasonsEntities.size();i++)
                        {
                            reasons.add(transferReasonsEntities.get(i).getName());
                        }
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,reasons);
                        spinnerRequestReason.setAdapter(dataAdapter);
                        spinnerRequestReason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                RequestReasonText = parent.getItemAtPosition(position).toString();
                                int pos =0;
                                for(int i = 0; i<transferReasonsEntities.size(); i++)
                                {
                                    if(RequestReasonText == transferReasonsEntities.get(i).getName())
                                    {
                                        pos = i;
                                        break;
                                    }
                                }
                                RequestReasonId = transferReasonsEntities.get(pos).getId();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                    else
                    {
                        Log.d("Tag","error in response");
                    }
                }
                else
                {
                    if(transferReasonsMiniEntity.getStatusCode()==401)
                    {
                        CommonFunctions.toastString("Unauthorized",getContext());
                    }
                }
            }
        },getContext());

        spinnerTransferTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TransferToText = parent.getItemAtPosition(position).toString();
                TransferToId = parent.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        callMyAssetListApi();
        Log.d("val", UserDetails.getMyAssetList(getActivity()));
        myAssetList = (new Gson()).fromJson(UserDetails.getMyProfileAssetList(getActivity()),new TypeToken<ArrayList<CodeDecodeEntity>>(){}.getType());
        rlInitiateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TransferToId==0||RequestReasonId==0){
                    Toast.makeText(getContext(),"Invalid Request",Toast.LENGTH_SHORT).show();
                }
                else {

                    Intent assetTransferSummary = new Intent(getContext(), InitiateTransferSummaryActivity.class);
                    selectedAssetList = adapter.getSelectedAssetList();
                    selectedAssetId = adapter.getSelectedAssetId();
                    assetTransferSummary.putExtra("RequestReasonId",RequestReasonId);
                    assetTransferSummary.putExtra("category", Constants.FACILIIY_ASSET_TYPE);
                    assetTransferSummary.putExtra("RequestReason",RequestReasonText);
                    assetTransferSummary.putExtra("TransferTo",TransferToText);
                    assetTransferSummary.putExtra("SelectedAssetId",(Serializable)selectedAssetId);
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
                        adapter=new TransferListRecyclerAdapter(myAssetList, getContext(),2);
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
        },getActivity(), Constants.FACILITY_ASSET_LIST);
    }
}
