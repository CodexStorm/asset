package com.ninja.ultron.entity;

import java.lang.ref.SoftReference;

/**
 * Created by Prabhu Sivanandam on 18-May-17.
 */

public class PendingRequestEntity {

    String status;
    int requestId;
    public PendingRequestEntity(String status, int requestId,String nomenclature) {
        this.status = status;
        this.requestId = requestId;
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
