package com.ninja.ultron.entity;

import com.android.volley.VolleyError;

import java.util.List;

/**
 * Created by Prabhu Sivanandam on 05-Jun-17.
 */

public class LabourShiftDetailAPI {

    private List<LabourShiftDetailEntity> labourShiftDetailEntityList;
    private int code;
    private String message;

    public List<LabourShiftDetailEntity> getLabourShiftDetailEntityList() {
        return labourShiftDetailEntityList;
    }

    public void setLabourShiftDetailEntityList(List<LabourShiftDetailEntity> labourShiftDetailEntityList) {
        this.labourShiftDetailEntityList = labourShiftDetailEntityList;
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

    public static interface FlashRestClientInterface {
        public void onLabourShiftDetail(LabourShiftDetailAPI labourShiftDetailAPI, VolleyError error);
    }
}
