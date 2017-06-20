package com.ninja.ultron.entity;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by manoj on 21-06-2017.
 */

public class NewAssetInitateRequestEntity {
    int userId;
    String comment;
    List<RequestNewAssetEntity> assetMakeDetails;
    int assetSkuCategoryId;
    int facilityId;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<RequestNewAssetEntity> getAssetMakeDetails() {
        return assetMakeDetails;
    }
    public int getAssetSkuCategoryId() {
        return assetSkuCategoryId;
    }

    public void setAssetSkuCategoryId(int assetSkuCategoryId) {
        this.assetSkuCategoryId = assetSkuCategoryId;
    }

    public int getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(int facilityId) {
        this.facilityId = facilityId;
    }
    public void setAssetMakeDetails(List<RequestNewAssetEntity> assetMakeDetails) {
        this.assetMakeDetails = assetMakeDetails;
    }
    public interface UltronRestClientInterface{
        void onInitialize(NewAssetInitateRequestEntity newAssetInitateRequestEntity, VolleyError error);
    }

    public JSONObject getJsonObjectAsParams(){
        JSONObject jsonObject = null;
        Gson gson = new Gson();
        String objectString = gson.toJson(this);
        if(jsonObject==null)
        {
            try{
                jsonObject = new JSONObject(objectString);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return jsonObject;
    }
}
