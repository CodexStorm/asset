package com.ninja.ultron.entity;

import com.android.volley.VolleyError;

import java.util.List;

/**
 * Created by Prabhu Sivanandam on 23-May-17.
 */

public class TransferReasonsMiniEntity {

    int statusCode;
    String message;
    List<TransferReasonsEntity> response;

    public TransferReasonsMiniEntity(int statusCode, String message, List<TransferReasonsEntity> reponse) {
        this.statusCode = statusCode;
        this.message = message;
        this.response = response;
    }

    public TransferReasonsMiniEntity() {
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

    public List<TransferReasonsEntity> getReponse() {
        return response;
    }

    public void setReponse(List<TransferReasonsEntity> reponse) {
        this.response = reponse;
    }

    public interface UltronRestClientInterface {
        void onInitialize(TransferReasonsMiniEntity transferReasonsMiniEntity, VolleyError error);
    }

}
