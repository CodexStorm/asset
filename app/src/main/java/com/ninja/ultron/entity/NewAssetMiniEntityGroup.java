package com.ninja.ultron.entity;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manoj on 22-06-2017.
 */

public class NewAssetMiniEntityGroup {
    int statusCode;
    String message;
    NewAssetEntityGroup response;

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

    public NewAssetEntityGroup getResponse() {
        return response;
    }

    public void setResponse(NewAssetEntityGroup response) {
        this.response = response;
    }

    public interface UltronRestClientInterface {
        void onInitialize(NewAssetMiniEntityGroup newAssetMiniEntityGroup, VolleyError error);
    }
}
