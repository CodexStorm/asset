package com.ninja.ultron.entity;

import com.android.volley.VolleyError;

import java.util.List;

/**
 * Created by Sangameswaran on 17-06-2017.
 */

public class LabourTimeEntity {
    Number labourId;
   List<TimeEntity> response;
    int statusCode;
    String message;

    public List<TimeEntity> getResponse() {
        return response;
    }

    public void setResponse(List<TimeEntity> response) {
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

    public Number getLabourId() {
        return labourId;
    }

    public void setLabourId(Number labourId) {
        this.labourId = labourId;
    }


    public interface UltronRestClientInterface{
        public void onGetTimeEntity(LabourTimeEntity entity, VolleyError error);
    }
}
