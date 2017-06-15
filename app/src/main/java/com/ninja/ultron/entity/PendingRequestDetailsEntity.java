package com.ninja.ultron.entity;

import java.util.List;

/**
 * Created by Prabhu Sivanandam on 24-May-17.
 */

public class PendingRequestDetailsEntity {

    String assetMake,status,reason,nomenclature;
    int assetId,assetRequestId,requestTo;
    List<PendingRequestsCommentsEntity> comment;
    String dateOfRequest;


    public PendingRequestDetailsEntity(String assetMake, String status, String reason, String nomenclature, int assetId, int assetRequestId, int requestTo, List<PendingRequestsCommentsEntity> comment, String dateOfRequest) {
        this.assetMake = assetMake;
        this.status = status;
        this.reason = reason;
        this.nomenclature = nomenclature;
        this.assetId = assetId;
        this.assetRequestId = assetRequestId;
        this.requestTo = requestTo;
        this.comment = comment;
        this.dateOfRequest = dateOfRequest;
    }


    public PendingRequestDetailsEntity() {

    }

    public String getNomenclature() {
        return nomenclature;
    }

    public void setNomenclature(String nomenclature) {
        this.nomenclature = nomenclature;
    }
    public String getAssetMake() {
        return assetMake;
    }

    public void setAssetMake(String assetMake) {
        this.assetMake = assetMake;
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
        String date=dateOfRequest.substring(0,10);
        return date;
    }

    public void setDateOfRequest(String dateOfRequest) {
        this.dateOfRequest = dateOfRequest;
    }
}
