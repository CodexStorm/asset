package com.ninja.ultron.entity;

import java.io.Serializable;

/**
 * Created by manoj on 16-06-2017.
 */

public class NewAssetEntity implements Serializable{
    String assetType;
    int Quantity;

    public NewAssetEntity(String assetType, int quantity) {
        this.assetType = assetType;
        Quantity = quantity;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int  quantity) {
        Quantity = quantity;
    }
}
