package com.ninja.ultron.entity;

import com.android.volley.VolleyError;

import java.util.List;

/**
 * Created by manoj on 21-06-2017.
 */

public class TranferDetailsAssetListMiniEntity {
    int statusCode;
    EntityGroup response;
    String message;

    public EntityGroup getResponse() {
        return response;
    }

    public void setResponse(EntityGroup response) {
        this.response = response;
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

    public interface UltronRestClientInterface{
        void onInitialize(TranferDetailsAssetListMiniEntity tranferDetailsAssetListMiniEntity, VolleyError error);
    }
}
