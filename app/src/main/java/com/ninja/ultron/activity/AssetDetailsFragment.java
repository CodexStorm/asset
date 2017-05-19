package com.ninja.ultron.activity;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ninja.ultron.R;
import com.ninja.ultron.adapter.AssetAccessoryAdapter;
import com.ninja.ultron.entity.AssetAccessoryEntity;
import com.ninja.ultron.entity.AssetDetailsEntity;
import com.ninja.ultron.entity.AssetDetailsMiniEntity;
import com.ninja.ultron.entity.AssetMiniEntity;
import com.ninja.ultron.functions.UserDetails;
import com.ninja.ultron.restclient.RestClientImplementation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AssetDetailsFragment extends Fragment {


    private final String URL ="http://10.0.0.46:8080/api/web/details/asset?assetId=4";
    TextView tvName1;
    TextView tvId1;
    TextView tvCategory1;
    TextView tvType1;
    TextView tvMaker1;
    TextView tvSpecifiaction1;
    ListView lvAccessories;
    List<AssetAccessoryEntity> assetAccessoryList = new ArrayList<>();
    AssetAccessoryEntity assetAccessory;
    List<AssetDetailsEntity> assetDetailsList;
    AssetAccessoryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_asset_details,container,false);
        tvName1 = (TextView)view.findViewById(R.id.tvName1);
        tvId1 = (TextView)view.findViewById(R.id.tvId1);
        tvCategory1 = (TextView)view.findViewById(R.id.tvCategory1);
        tvType1 = (TextView) view.findViewById(R.id.tvType1);
        tvMaker1 = (TextView)view.findViewById(R.id.tvMaker1);
        tvSpecifiaction1 = (TextView)view.findViewById(R.id.tvSpecification1);
        lvAccessories = (ListView) view.findViewById(R.id.lvAccessories);

        callAssetDetailsApi();
        return view;
    }

    private void callAssetDetailsApi() {
        AssetDetailsMiniEntity assetDetailsMiniEntity = new AssetDetailsMiniEntity();
        RestClientImplementation.assetDetailsApi(assetDetailsMiniEntity, new AssetDetailsMiniEntity.UltronRestClientInterface() {
            @Override
            public void onInitialize(AssetDetailsMiniEntity assetDetailsMiniEntity, VolleyError error) {
                if(error == null) {
                    if(assetDetailsMiniEntity.getResponse() != null) {
                        Gson gs = new Gson();
                        assetDetailsList = assetDetailsMiniEntity.getResponse();
                        AssetDetailsEntity assetDetailsEntity = assetDetailsList.get(0);
                        tvId1.setText(""+assetDetailsEntity.getAssetId());
                        tvName1.setText(assetDetailsEntity.getAssetName());
                        tvCategory1.setText(assetDetailsEntity.getAssetCategory());
                        tvMaker1.setText(assetDetailsEntity.getAssetMaker());
                        tvType1.setText(assetDetailsEntity.getAssetType());
                        tvSpecifiaction1.setText(assetDetailsEntity.getAssetSpecification());
                        assetAccessoryList = assetDetailsEntity.getAssetAccessory();
                        String myAssetAccessoryListAsString = gs.toJson(assetAccessoryList);
                        UserDetails.setAssetAccessoryList(getContext(),myAssetAccessoryListAsString);
                        assetAccessoryList = gs.fromJson(assetDetailsEntity.getAssetAccessory().toString(),new TypeToken<ArrayList<AssetAccessoryEntity>>(){}.getType());
                        adapter= new AssetAccessoryAdapter(getContext(),assetAccessoryList);
                        lvAccessories.setAdapter(adapter);
                    }
                } else {

                }
            }
        },getActivity());
    }
}
