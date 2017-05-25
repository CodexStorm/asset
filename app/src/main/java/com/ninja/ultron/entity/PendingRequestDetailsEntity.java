package com.ninja.ultron.entity;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Prabhu Sivanandam on 24-May-17.
 */

public class PendingRequestDetailsEntity {

    String assetName,status,reason;
    int assetId,assetRequestId,requestTo;
    List<PendingRequestsCommentsEntity> comment;
    String dateOfRequest;

    public PendingRequestDetailsEntity(String assetName, String status, String reason, int assetId, int assetRequestId, int requestTo, List<PendingRequestsCommentsEntity> comment,String dateOfRequest) {
        this.assetName = assetName;
        this.status = status;
        this.reason = reason;
        this.assetId = assetId;
        this.assetRequestId = assetRequestId;
        this.requestTo = requestTo;
        this.comment = comment;
        this.dateOfRequest = dateOfRequest;
    }

    public PendingRequestDetailsEntity() {

    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getAssetId() {
        return assetId;
    }

    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }

    public int getAssetRequestId() {
        return assetRequestId;
    }

    public void setAssetRequestId(int assetRequestId) {
        this.assetRequestId = assetRequestId;
    }

    public int getRequestTo() {
        return requestTo;
    }

    public void setRequestTo(int requestTo) {
        this.requestTo = requestTo;
    }

    public List<PendingRequestsCommentsEntity> getComment() {
        return comment;
    }

    public void setComment(List<PendingRequestsCommentsEntity> comment) {
        this.comment = comment;
    }

    public String getDateOfRequest() {
        StringBuilder builder=new StringBuilder(dateOfRequest);
        builder.deleteCharAt(10);
        builder.deleteCharAt(23);
        return builder.toString();
    }

    public void setDateOfRequest(String dateOfRequest) {
        this.dateOfRequest = dateOfRequest;
    }
}
