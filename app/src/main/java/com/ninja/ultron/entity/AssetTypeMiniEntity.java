package com.ninja.ultron.entity;

import com.android.volley.VolleyError;

import java.util.List;

/**
 * Created by manoj on 20-06-2017.
 */

public class AssetTypeMiniEntity {
    int statusCode;
    String message;
    List<AssetTypeEntity> response;

    public AssetTypeMiniEntity(int statusCode, String message, List<AssetTypeEntity> response) {
        this.statusCode = statusCode;
        this.message = message;
        this.response = response;
    }

    public AssetTypeMiniEntity(){

    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<AssetTypeEntity> getResponse() {
        return response;
    }

    public void setResponse(List<AssetTypeEntity> response) {
        this.response = response;
    }

    public interface UltronRestClientInterface{
        void onInitialize(AssetTypeMiniEntity assetTypeMiniEntity, VolleyError error);
    }
}
