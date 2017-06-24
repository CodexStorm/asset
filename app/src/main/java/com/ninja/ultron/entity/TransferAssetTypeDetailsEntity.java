package com.ninja.ultron.entity;

/**
 * Created by manoj on 22-06-2017.
 */

public class TransferAssetTypeDetailsEntity {
    String assetType;
    String assetMake;
    int assetId;
    String nomenclature;

    public TransferAssetTypeDetailsEntity(String assetType, String nomenclature,int assetId) {
        this.assetType = assetType;
     //   this.assetMake = assetMake;
        this.nomenclature = nomenclature;
        this.assetId = assetId;
    }

    public String getAssetMake() {
        return assetMake;
    }

    public void setAssetMake(String assetMake) {
        this.assetMake = assetMake;
    }

    public int getAssetId() {
        return assetId;
    }

    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

   /* public String getAssetMake() {
        return assetMake;
    }

    public void setAssetMake(String assetMake) {
        this.assetMake = assetMake;
    }
*/
    public String getNomenclature() {
        return nomenclature;
    }

    public void setNomenclature(String nomenclature) {
        this.nomenclature = nomenclature;
    }
}
