package com.ninja.ultron.entity;

import com.android.volley.VolleyError;

import java.util.List;

/**
 * Created by Prabhu Sivanandam on 24-May-17.
 */

public class PendingRequestDetailsMiniEntity {

    int statusCode;
    List<PendingRequestDetailsEntity> response;
    String message;

    public PendingRequestDetailsMiniEntity(int statusCode, List<PendingRequestDetailsEntity> response, String message) {
        this.statusCode = statusCode;
        this.response = response;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public List<PendingRequestDetailsEntity> getResponse() {
        return response;
    }

    public void setResponse(List<PendingRequestDetailsEntity> response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public interface UltronRestClientInterface {
        void onInitialize(PendingRequestDetailsMiniEntity pendingRequestDetailsMiniEntity, VolleyError error);
    }

}
