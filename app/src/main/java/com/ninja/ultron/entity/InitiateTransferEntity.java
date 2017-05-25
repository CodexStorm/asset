package com.ninja.ultron.entity;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Prabhu Sivanandam on 22-May-17.
 */

public class InitiateTransferEntity {

    int assetIssueTypeId,assetId,requestedBy,approver, transferReasonId,code;
    String dateOfRequest;
    CommentEntity comment;

    public InitiateTransferEntity(int assetIssueTypeId, int assetId, int requestedBy, int approver, String dateOfRequest, CommentEntity comment, int transferReasonId) {
        this.assetIssueTypeId = assetIssueTypeId;
        this.assetId = assetId;
        this.requestedBy = requestedBy;
        this.approver = approver;
        this.dateOfRequest = dateOfRequest;
        this.comment = comment;
        this.transferReasonId = transferReasonId;
    }

    public InitiateTransferEntity() {
    }

    public int getAssetIssueTypeId() {
        return assetIssueTypeId;
    }

    public void setAssetIssueTypeId(int assetIssueTypeId) {
        this.assetIssueTypeId = assetIssueTypeId;
    }

    public int getAssetId() {
        return assetId;
    }

    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }

    public int getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(int requestedBy) {
        this.requestedBy = requestedBy;
    }

    public int getApprover() {
        return approver;
    }

    public void setApprover(int approver) {
        this.approver = approver;
    }

    public String getDateOfRequest() {
        return dateOfRequest;
    }

    public void setDateOfRequest(String dateOfRequest) {
        this.dateOfRequest = dateOfRequest;
    }

    public CommentEntity getComment() {
        return comment;
    }

    public void setComment(CommentEntity comment) {
        this.comment = comment;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public interface UltronRestClientInterface {
        void onInitialize(InitiateTransferEntity initiateTransferEntity, VolleyError error);
    }

    public int getTransferReasonId() {
        return transferReasonId;
    }

    public void setTransferReasonId(int transferReasonId) {
        this.transferReasonId = transferReasonId;
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
