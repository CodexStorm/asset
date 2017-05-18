package com.ninja.ultron.entity;


import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class InitApiEntity {
    private long currentServerDate;
    private int code;
    private String message;
    private int appVersion;
    private String appName;
    private AsgardUser asgardUser;
    private int asgardId;
    private List<CodeDecodeEntity> myAssetList;
    private List<InitializeConfigDirect> initializeConfig;

    public List<InitializeConfigDirect> getInitializeConfig() {
        return initializeConfig;
    }

    public void setInitializeConfig(List<InitializeConfigDirect> initializeConfig) {
        this.initializeConfig = initializeConfig;
    }

    public long getCurrentServerDate() {
        return currentServerDate;
    }

    public void setCurrentServerDate(long currentServerDate) {
        this.currentServerDate = currentServerDate;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(int appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public AsgardUser getAsgardUser() {
        return asgardUser;
    }

    public void setAsgardUser(AsgardUser asgardUser) {
        this.asgardUser = asgardUser;
    }

    public int getAsgardId() {
        return asgardId;
    }

    public void setAsgardId(int asgardId) {
        this.asgardId = asgardId;
    }

    public List<CodeDecodeEntity> getMyAssetList() {
        return myAssetList;
    }

    public void setMyAssetList(List<CodeDecodeEntity> myAssetList) {
        this.myAssetList = myAssetList;
    }

    public static interface UltronRestClientInterface {
        public void onInitialize(InitApiEntity initApiEntity, VolleyError error);
    }

    public InitApiEntity(int appVersion, String appName, int asgardId) {
        this.appVersion = appVersion;
        this.appName = appName;
        this.asgardId = asgardId;

    }

    public JSONObject getInitAPIParams() {
        JSONObject initAPIJson = null;
        Gson gs = new Gson();
        String initApiString = gs.toJson(this);
        try {
            initAPIJson = new JSONObject(initApiString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initAPIJson.remove("code");
        initAPIJson.remove("currentServerDate");
        return initAPIJson;
    }

}
