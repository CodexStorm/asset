package com.ninja.ultron.entity;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manoj on 23-06-2017.
 */

public class AssetAcceptEntity {
    int userId;
    List<Integer> assetIds;
    int facilityId;
    int assetRequestId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Integer> getAssetIds() {
        return assetIds;
    }

    public void setAssetIds(List<Integer> assetIds) {
        this.assetIds = new ArrayList<>();
        this.assetIds = assetIds;
    }

    public int getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(int facilityId) {
        this.facilityId = facilityId;
    }

    public int getAssetRequestId() {
        return assetRequestId;
    }

    public void setAssetRequestId(int assetRequestId) {
        this.assetRequestId = assetRequestId;
    }

    public interface UltronRestClientInterface {
        void onInitialize(AssetAcceptEntity assetAcceptEntity, VolleyError error);
    }

    public JSONObject getJsonObjectAsParams()
    {
        JSONObject jsonObject=null;
        Gson gson=new Gson();
        String objectString=gson.toJson(this);
        if(jsonObject==null)
        {
            try {
                jsonObject=new JSONObject(objectString);
                jsonObject.remove("code");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }
}
