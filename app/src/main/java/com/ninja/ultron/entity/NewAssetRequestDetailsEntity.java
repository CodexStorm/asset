package com.ninja.ultron.entity;

import java.util.List;

/**
 * Created by manoj on 22-06-2017.
 */

public class NewAssetRequestDetailsEntity {
        String requestType;
        String status;
        String categoryName;
        int requestId;
        List<NewAssetTypeDetailsEntity> assetTypeDetails;

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

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public int getRequestId() {
            return requestId;
        }

        public void setRequestId(int requestId) {
            this.requestId = requestId;
        }

        public List<NewAssetTypeDetailsEntity> getAssetTypeDetails() {
            return assetTypeDetails;
        }

        public void setAssetTypeDetails(List<NewAssetTypeDetailsEntity> assetTypeDetails) {
            this.assetTypeDetails = assetTypeDetails;
        }
}

