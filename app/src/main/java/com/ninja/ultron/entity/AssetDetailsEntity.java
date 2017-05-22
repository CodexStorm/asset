package com.ninja.ultron.entity;

import java.util.ArrayList;

/**
 * Created by manoj on 17-05-2017.
 */

public class AssetDetailsEntity {
    private String assetName;
    private String assetId;
    private String assetSpecification;
    private String assetCategory;
    private String assetType;
    private String assetMaker;
    private ArrayList<AssetAccessoryEntity> assetAccessory;

    public AssetDetailsEntity(String assetName, String assetId, String assetSpecification, String assetCategory, String assetType, String assetMaker, ArrayList<AssetAccessoryEntity> assetAccessory) {
        this.assetName = assetName;
        this.assetId = assetId;
        this.assetSpecification = assetSpecification;
        this.assetCategory = assetCategory;
        this.assetType = assetType;
        this.assetMaker = assetMaker;
        this.assetAccessory = assetAccessory;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getAssetSpecification() {
        return assetSpecification;
    }

    public void setAssetSpecification(String assetSpecification) {
        this.assetSpecification = assetSpecification;
    }

    public String getAssetCategory() {
        return assetCategory;
    }

    public void setAssetCategory(String assetCategory) {
        this.assetCategory = assetCategory;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getAssetMaker() {
        return assetMaker;
    }

    public void setAssetMaker(String assetMaker) {
        this.assetMaker = assetMaker;
    }

    public ArrayList<AssetAccessoryEntity> getAssetAccessory() {
        return assetAccessory;
    }

    public void setAssetAccessory(ArrayList<AssetAccessoryEntity> assetAccessory) {
        this.assetAccessory = assetAccessory;
    }
}
