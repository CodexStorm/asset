package com.ninja.ultron.entity;

import java.io.Serializable;

public class CodeDecodeEntity implements Serializable {


    private int Id;
    private String nomenclature;
    private String assetMake;



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
