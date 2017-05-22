package com.ninja.ultron.entity;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prabhu Sivanandam on 20-May-17.
 */

public class PendingRequestMiniEntity {

    int statusCode;
    String message;
    List<PendingRequestEntity> response=new ArrayList<>();

    public PendingRequestMiniEntity(int statusCode, String message, List<PendingRequestEntity> response) {
        this.statusCode = statusCode;
        this.message = message;
        this.response = response;
    }

    public PendingRequestMiniEntity() {

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

    public List<PendingRequestEntity> getResponse() {
        return response;
    }

    public void setResponse(List<PendingRequestEntity> response) {
        this.response = response;
    }

    public interface UltronRestClientInterface {
        void onInitialize(PendingRequestMiniEntity pendingRequestMiniEntity, VolleyError error);
    }

}
