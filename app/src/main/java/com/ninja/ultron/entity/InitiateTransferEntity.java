package com.ninja.ultron.entity;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prabhu Sivanandam on 22-May-17.
 */

public class InitiateTransferEntity {

    int userId;
    List<Integer> assetList;
    int assetIssueTypeId;
    int assetSkuCategoryId;
    String transferTo;
    int facilityId;
    int transferReasonId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Integer> getAssetList() {
        return assetList;
    }

    public void setAssetList(List<Integer> assetList) {
        this.assetList = assetList;
    }

    public int getAssetIssueTypeId() {
        return assetIssueTypeId;
    }

    public void setAssetIssueTypeId(int assetIssueTypeId) {
        this.assetIssueTypeId = assetIssueTypeId;
    }

    public int getAssetSkuCategoryId() {
        return assetSkuCategoryId;
    }

    public void setAssetSkuCategoryId(int assetSkuCategoryId) {
        this.assetSkuCategoryId = assetSkuCategoryId;
    }

    public String getTransferTo() {
        return transferTo;
    }

    public void setTransferTo(String transferTo) {
        this.transferTo = transferTo;
    }

    public int getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(int facilityId) {
        this.facilityId = facilityId;
    }

    public int getTransferReasonId() {
        return transferReasonId;
    }

    public void setTransferReasonId(int transferReasonId) {
        this.transferReasonId = transferReasonId;
    }

    public interface UltronRestClientInterface {
        void onInitialize(InitiateTransferEntity initiateTransferEntity, VolleyError error);
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
