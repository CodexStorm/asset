package com.ninja.ultron.entity;

import com.android.volley.VolleyError;

import java.util.List;

/**
 * Created by omprakash on 19/5/17.
 */

public class AssetDetailsMiniEntity {
    int statusCode;
    List<AssetDetailsEntity> response;
    String message;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public List<AssetDetailsEntity> getResponse() {
        return response;
    }

    public void setResponse(List<AssetDetailsEntity> response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public interface UltronRestClientInterface {
        void onInitialize(AssetDetailsMiniEntity assetDetailsMiniEntity, VolleyError error);
    }

}
