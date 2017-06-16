package com.ninja.ultron.entity;

import com.android.volley.VolleyError;

import java.util.List;

/**
 * Created by Sangameswaran on 15-06-2017.
 */

public class PostBulkLaboursAttendanceAPIEntity {
    List<LabourAttendanceMobileDTO> labours;
    int facilityId;
    private int shiftDetailId;
    private int shiftHours;
    private int userId;
    private int attendanceStatus;
    private int statusCode;
    private String message;

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

    public PostBulkLaboursAttendanceAPIEntity() {

    }

    public List<LabourAttendanceMobileDTO> getLabours() {
        return labours;
    }

    public void setLabours(List<LabourAttendanceMobileDTO> labours) {
        this.labours = labours;
    }

    public int getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(int facilityId) {
        this.facilityId = facilityId;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(int attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    public interface UltronRestClientInterface{
        public void postBulkAttendance(PostBulkLaboursAttendanceAPIEntity entity, VolleyError error);
    }
}
