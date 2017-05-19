package com.ninja.ultron.activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AssetDetailsActivity extends AppCompatActivity {


    private final String URL ="http://10.0.0.46:8080/api/web/details/asset?assetId=4";
    TextView tvName1;
    TextView tvId1;
    TextView tvCategory1;
    TextView tvType1;
    TextView tvMaker1;
    TextView tvSpecifiaction1;
    ListView lvAccessories;
    ArrayList<AssetAccessoryEntity> accessoryItems = new ArrayList<>();



    ArrayList<AssetDetailsEntity> assetList = new ArrayList<>();
    AssetAccessoryEntity assetAccessory;
    AssetDetailsEntity details;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_asset_details);
        tvName1 = (TextView)findViewById(R.id.tvName1);
        tvId1 = (TextView)findViewById(R.id.tvId1);
        tvCategory1 = (TextView)findViewById(R.id.tvCategory1);
        tvType1 = (TextView) findViewById(R.id.tvType1);
        tvMaker1 = (TextView)findViewById(R.id.tvMaker1);
        tvSpecifiaction1 = (TextView) findViewById(R.id.tvSpecification1);
        lvAccessories = (ListView) findViewById(R.id.lvAccessories);


        RequestQueue queue = Volley.newRequestQueue(AssetDetailsActivity.this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray assetArray = null;
                try {
                    assetArray = response.getJSONArray("response");
                    Gson gson = new Gson();
                    Type T = new TypeToken<ArrayList<AssetDetailsEntity>>(){}.getType();
                    assetList = gson.fromJson(assetArray.toString(),T);
                    details=assetList.get(0);
                    tvName1.setText(":  "+details.getAssetName());
                    tvId1.setText(":  "+details.getAssetId());
                    tvCategory1.setText(":  "+details.getAssetCategory());
                    tvType1.setText(":  "+details.getAssetType());
                    tvMaker1.setText(":  "+details.getAssetMaker());
                    tvSpecifiaction1.setText(":  "+details.getAssetSpecification());

                    ArrayList<AssetAccessoryEntity> accessoryItems = new ArrayList<>();
                    accessoryItems = details.getAssetAccessory();
                    AssetAccessoryAdapter adapter = new AssetAccessoryAdapter(AssetDetailsActivity.this, accessoryItems);
                    lvAccessories.setAdapter(adapter);

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


    }

}
