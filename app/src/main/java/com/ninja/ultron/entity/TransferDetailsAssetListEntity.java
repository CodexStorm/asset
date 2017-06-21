package com.ninja.ultron.entity;

import java.util.List;

/**
 * Created by manoj on 21-06-2017.
 */

public class TransferDetailsAssetListEntity {
    private int requestId;
    private String categoryName;
    private String status;
    private String requestType;
    private List<SkuAssetDetailsEntity> assetDetails;

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public List<SkuAssetDetailsEntity> getAssetDetails() {
        return assetDetails;
    }

    public void setAssetDetails(List<SkuAssetDetailsEntity> assetDetails) {
        this.assetDetails = assetDetails;
    }



}
