package com.ninja.ultron.entity;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Prabhu Sivanandam on 05-Jun-17.
 */

public class LabourAttendanceTrackerEntity {
    private int labourAttendanceTrackerId;
    private int facilityId;
    private int labourId;
    private int shiftDetailId;
    private int shiftHours;
    private int overTimeHours;
    private byte deleted;

    private int code;
    private String message;

    public int getLabourAttendanceTrackerId() {
        return labourAttendanceTrackerId;
    }

    public void setLabourAttendanceTrackerId(int labourAttendanceTrackerId) {
        this.labourAttendanceTrackerId = labourAttendanceTrackerId;
    }

    public int getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(int facilityId) {
        this.facilityId = facilityId;
    }

    public int getLabourId() {
        return labourId;
    }

    public void setLabourId(int labourId) {
        this.labourId = labourId;
    }

    public int getShiftDetailId() {
        return shiftDetailId;
    }

    public void setShiftDetailId(int shiftDetailId) {
        this.shiftDetailId = shiftDetailId;
    }

    public int getShiftHours() {
        return shiftHours;
    }

    public void setShiftHours(int shiftHours) {
        this.shiftHours = shiftHours;
    }

    public int getOverTimeHours() {
        return overTimeHours;
    }

    public void setOverTimeHours(int overTimeHours) {
        this.overTimeHours = overTimeHours;
    }

    public byte getDeleted() {
        return deleted;
    }

    public void setDeleted(byte deleted) {
        this.deleted = deleted;
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

    public  interface FlashRestClientInterface {
        void onLabourAttendanceTracker(LabourAttendanceTrackerEntity labourAttendanceTrackerEntity, VolleyError error);
    }

    public JSONObject getJsonObjectAsParams() {
        JSONObject labourAttendanceTrackerJSON = null;
        Gson gs = new Gson();
        String labourAttendanceTrackerString = gs.toJson(this);
        try {
            labourAttendanceTrackerJSON = new JSONObject(labourAttendanceTrackerString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        labourAttendanceTrackerJSON.remove("code");
        return labourAttendanceTrackerJSON;
    }

}
