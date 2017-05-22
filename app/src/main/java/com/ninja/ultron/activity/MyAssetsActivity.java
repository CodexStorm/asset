package com.ninja.ultron.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

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
 * Created by Prabhu Sivanandam on 17-May-17.
 */

public class MyAssetsActivity extends AppCompatActivity{
    private final String URL="http://10.0.0.46:8080/api/web/assets?userId=1";
    ArrayList<AssetMiniEntity> assetList=new ArrayList<>();
    RecyclerView recyclerView;
    AssetRecyclerAdapter adapter;
    AssetMiniEntity assetMini;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_assets);
        assetList=new ArrayList<>();
        RequestQueue queue= Volley.newRequestQueue(MyAssetsActivity.this);
        recyclerView=(RecyclerView)findViewById(R.id.rvMyAssets);
        LinearLayoutManager manager=new LinearLayoutManager(this);
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
    }

    public void onNextClick(View v)
    {
        startActivity(new Intent(MyAssetsActivity.this,AssetDetailsActivity.class));
    }

    public void onCardClick(View v)
    {
        startActivity(new Intent(MyAssetsActivity.this,AssetDetailsActivity.class));
    }
}
