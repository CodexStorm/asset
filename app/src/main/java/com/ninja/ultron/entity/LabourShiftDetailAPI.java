package com.ninja.ultron.entity;

import com.android.volley.VolleyError;

import java.util.List;

/**
 * Created by Prabhu Sivanandam on 05-Jun-17.
 */

public class LabourShiftDetailAPI {

    private List<LabourShiftDetailEntity> response;
    private int statusCode;
    private String message;

    public List<LabourShiftDetailEntity> getResponse() {
        return response;
    }

    public void setResponse(List<LabourShiftDetailEntity> response) {
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

    public static interface FlashRestClientInterface {
        public void onLabourShiftDetail(LabourShiftDetailAPI labourShiftDetailAPI, VolleyError error);
    }
}
