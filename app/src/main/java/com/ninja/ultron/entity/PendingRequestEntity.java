package com.ninja.ultron.entity;

/**
 * Created by Prabhu Sivanandam on 18-May-17.
 */

public class PendingRequestEntity {

    String status;
    int requestId;
    String requestType;
    int statusId;

    public PendingRequestEntity(String status, int requestId, String nomenclature, String categoryName) {
        this.status = status;
        this.requestId = requestId;
        this.requestType = categoryName;

    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }


    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
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
