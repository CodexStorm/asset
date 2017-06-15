package com.ninja.ultron.entity;

import java.lang.ref.SoftReference;

/**
 * Created by Prabhu Sivanandam on 18-May-17.
 */

public class PendingRequestEntity {

    String assetMake;
    String status;
    int requestId;
    String nomenclature;

    public PendingRequestEntity(String assetMake, String status, int requestId,String nomenclature) {
        this.assetMake = assetMake;
        this.status = status;
        this.requestId = requestId;
        this.nomenclature = nomenclature;
    }

    public void setNomenclature(String nomenclature) {
        this.nomenclature = nomenclature;
    }

    public String getNomenclature() {

        return nomenclature;
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

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

}
