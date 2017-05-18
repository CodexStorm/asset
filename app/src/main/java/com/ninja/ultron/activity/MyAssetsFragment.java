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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ninja.ultron.R;
import com.ninja.ultron.adapter.AssetRecyclerAdapter;
import com.ninja.ultron.entity.AssetMiniEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Prabhu Sivanandam on 18-May-17.
 */

public class MyAssetsFragment extends Fragment {

    private final String URL="http://10.0.0.46:8080/api/web/assets?userId=1";
    ArrayList<AssetMiniEntity> assetList=new ArrayList<>();
    RecyclerView recyclerView;
    AssetRecyclerAdapter adapter;
    AssetMiniEntity assetMini;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.my_assets,container,false);
        assetList=new ArrayList<>();
        RequestQueue queue= Volley.newRequestQueue(getContext());
        recyclerView=(RecyclerView)v.findViewById(R.id.rvMyAssets);
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("sds",response.toString());
                    Gson gson=new Gson();
                    JSONArray array=response.getJSONArray("response");
                    Log.d("array",array.toString());
                    Type T=new TypeToken<ArrayList<AssetMiniEntity>>(){}.getType();
                    assetList=gson.fromJson(array.toString(),T);
                    adapter=new AssetRecyclerAdapter(assetList);
                    recyclerView.setAdapter(adapter);
                    recyclerView.hasFixedSize();
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
        adapter=new AssetRecyclerAdapter(assetList);
        recyclerView.setAdapter(adapter);
        recyclerView.hasFixedSize();
        adapter.notifyDataSetChanged();

        return v;
    }
}
