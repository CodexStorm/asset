package com.ninja.ultron.entity;

public class CodeDecodeEntity {


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
