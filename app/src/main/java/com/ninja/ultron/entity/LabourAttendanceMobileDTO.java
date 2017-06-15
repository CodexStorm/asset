package com.ninja.ultron.entity;

/**
 * Created by Prabhu Sivanandam on 05-Jun-17.
 */


public class LabourAttendanceMobileDTO {
    private Number labourId;
    private String labourName;
    private String labourAgencyCode;
    private String shiftName;

    public Number getLabourId() {
        return labourId;
    }

    public void setLabourId(Number labourId) {
        this.labourId = labourId;
    }

    public String getLabourName() {
        return labourName;
    }

    public void setLabourName(String labourName) {
        this.labourName = labourName;
    }

    public String getLabourAgencyCode() {
        return labourAgencyCode;
    }

    public void setLabourAgencyCode(String labourAgencyCode) {
        this.labourAgencyCode = labourAgencyCode;
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }
}
