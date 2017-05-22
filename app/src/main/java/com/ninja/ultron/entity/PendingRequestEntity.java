package com.ninja.ultron.entity;

/**
 * Created by Prabhu Sivanandam on 18-May-17.
 */

public class PendingRequestEntity {

    String assetName;
    String status;
    int requestId;

    public PendingRequestEntity(String assetName, String status, int requestId) {
        this.assetName = assetName;
        this.status = status;
        this.requestId = requestId;
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

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

}
