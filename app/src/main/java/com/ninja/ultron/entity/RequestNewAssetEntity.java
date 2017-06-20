package com.ninja.ultron.entity;

/**
 * Created by manoj on 21-06-2017.
 */

public class RequestNewAssetEntity {
    int skuTypeId;
    int skuQuantity;


    public int getSkuTypeId() {
        return skuTypeId;
    }

    public void setSkuTypeId(int skuTypeId) {
        this.skuTypeId = skuTypeId;
    }

    public int getSkuQuantity() {
        return skuQuantity;
    }

    public void setSkuQuantity(int skuQuantity) {
        this.skuQuantity = skuQuantity;
    }
}
