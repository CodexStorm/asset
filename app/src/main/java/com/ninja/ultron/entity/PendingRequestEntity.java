package com.ninja.ultron.entity;

/**
 * Created by Prabhu Sivanandam on 18-May-17.
 */

public class PendingRequestEntity {

    String assetMakeName;
    String requestStatus;
    String requestRaisedDate;

    public PendingRequestEntity(String assetMakeName, String requestStatus, String requestRaisedDate) {
        this.assetMakeName = assetMakeName;
        this.requestStatus = requestStatus;
        this.requestRaisedDate = requestRaisedDate;
    }

    public String getAssetMakeName() {
        return assetMakeName;
    }

    public void setAssetMakeName(String assetMakeName) {
        this.assetMakeName = assetMakeName;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getRequestRaisedDate() {
        return requestRaisedDate;
    }

    public void setRequestRaisedDate(String requestRaisedDate) {
        this.requestRaisedDate = requestRaisedDate;
    }

}
