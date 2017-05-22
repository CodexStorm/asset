package com.ninja.ultron.entity;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ompra on 5/4/2017.
 */

public class LoginEntity {
    private String userName;
    private String password;
    private AsgardUser asgardUser;
    private int code;
    private String message;

    public AsgardUser getAsgardUser() {
        return asgardUser;
    }

    public void setAsgardUser(AsgardUser asgardUser) {
        this.asgardUser = asgardUser;
    }

    public LoginEntity(String userName, String password) {
        this.userName = userName;
        this.password = password;
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

    public String getUserName() {

        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public interface RestClientInterface{
        void onLogin(LoginEntity loginEntity, VolleyError error);
    }

    public JSONObject getJsonObjectAsParams(){
        JSONObject loginEntityJson = null;
        Gson gs = new Gson();
        String loginString = gs.toJson(this);
        try{
            loginEntityJson = new JSONObject(loginString);
        }catch (Exception e){

        }
        loginEntityJson.remove("code");
        return loginEntityJson;
    }
}
