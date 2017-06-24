package com.ninja.ultron.entity;

import com.android.volley.VolleyError;

import java.util.List;

/**
 * Created by manoj on 24-06-2017.
 */

public class AssetUserRecievalMiniEntity {
    int statusCode;
    List<AssetUserRecievalEntity> response;
    String message;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public List<AssetUserRecievalEntity> getResponse() {
        return response;
    }

    public void setResponse(List<AssetUserRecievalEntity> response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public interface UltronRestClientInterface {
        void onInitialize(AssetUserRecievalMiniEntity assetUserRecievalMiniEntity, VolleyError error);
    }
}
