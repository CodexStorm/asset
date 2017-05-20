package com.ninja.ultron.entity;

import com.android.volley.VolleyError;

import java.util.List;

/**
 * Created by Prabhu Sivanandam on 17-May-17.
 */

public class AssetMiniEntity {

    int statusCode;
    List<CodeDecodeEntity> response;
    String message;

    public AssetMiniEntity() {
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public List<CodeDecodeEntity> getResponse() {
        return response;
    }

    public void setResponse(List<CodeDecodeEntity> response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



    public interface UltronRestClientInterface {
        void onInitialize(AssetMiniEntity assetMiniEntity, VolleyError error);
    }

}
