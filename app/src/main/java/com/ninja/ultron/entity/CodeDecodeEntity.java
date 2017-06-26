package com.ninja.ultron.entity;

import java.io.Serializable;

public class CodeDecodeEntity implements Serializable {


    private int Id;
    private String nomenclature;
    private String assetMake;
    private String status;
    private String typeName;
    private String categoryName;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    public void setAssetMake(String assetMake) {
        this.assetMake = assetMake;
    }

    public String getAssetMake() {

        return assetMake;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getNomenclature() {
        return nomenclature;
    }

    public void setNomenclature(String nomenclature) {
        this.nomenclature = nomenclature;
    }

}
