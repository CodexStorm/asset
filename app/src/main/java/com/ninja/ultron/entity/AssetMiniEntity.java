package com.ninja.ultron.entity;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Prabhu Sivanandam on 17-May-17.
 */

public class AssetMiniEntity {

    int stausCode;

    public AssetMiniEntity() {
    }

    public int getStausCode() {
        return stausCode;
    }

    public void setStausCode(int stausCode) {
        this.stausCode = stausCode;
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

    List<CodeDecodeEntity> response;
    String message;

    public interface UltronRestClientInterface {
        void onInitialize(AssetMiniEntity assetMiniEntity, VolleyError error);
    }

}
