package com.ninja.ultron.entity;

import com.android.volley.VolleyError;

import java.util.List;

/**
 * Created by Sangameswaran on 17-06-2017.
 */

public class GetPenaltyApiEntity {
    List<PenaltyEntity> response;
    int statusCode;
    String message;

    public List<PenaltyEntity> getResponse() {
        return response;
    }

    public void setResponse(List<PenaltyEntity> response) {
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
        void onGetPenalties(GetPenaltyApiEntity getPenaltyApiEntity, VolleyError error);
    }
}
