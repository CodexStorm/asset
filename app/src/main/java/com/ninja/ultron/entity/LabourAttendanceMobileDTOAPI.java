package com.ninja.ultron.entity;

import com.android.volley.VolleyError;

import java.util.List;

/**
 * Created by Prabhu Sivanandam on 05-Jun-17.
 */

public class LabourAttendanceMobileDTOAPI {
    private List<LabourAttendanceMobileDTO> labourAttendanceMobileDTOList;
    private int shiftDetailId;
    private int offset;
    private int limit;
    private int code;
    private String message;
    private int labourId;
    private int labourAgencyId;

    public LabourAttendanceMobileDTOAPI() {
    }

    public LabourAttendanceMobileDTOAPI(int shiftDetailId, int offset, int limit) {
        this.shiftDetailId = shiftDetailId;
        this.offset = offset;
        this.limit = limit;
    }

    public LabourAttendanceMobileDTOAPI(int shiftDetailId) {
        this.shiftDetailId = shiftDetailId;
    }

    public LabourAttendanceMobileDTOAPI(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    public LabourAttendanceMobileDTOAPI(int labourAgencyId, int labourId, int offset, int limit) {
        this.labourAgencyId = labourAgencyId;
        this.labourId = labourId;
        this.offset = offset;
        this.limit = limit;
    }

    public List<LabourAttendanceMobileDTO> getLabourAttendanceMobileDTOList() {
        return labourAttendanceMobileDTOList;
    }

    public void setLabourAttendanceMobileDTOList(List<LabourAttendanceMobileDTO> labourAttendanceMobileDTOList) {
        this.labourAttendanceMobileDTOList = labourAttendanceMobileDTOList;
    }

    public int getShiftDetailId() {
        return shiftDetailId;
    }

    public void setShiftDetailId(int shiftDetailId) {
        this.shiftDetailId = shiftDetailId;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
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

    public int getLabourId() {
        return labourId;
    }

    public void setLabourId(int labourId) {
        this.labourId = labourId;
    }

    public int getLabourAgencyId() {
        return labourAgencyId;
    }

    public void setLabourAgencyId(int labourAgencyId) {
        this.labourAgencyId = labourAgencyId;
    }

    public static interface FlashRestClientInterface {
        public void onLabourAttendanceMobileDTO(LabourAttendanceMobileDTOAPI labourAttendanceMobileDTOAPI, VolleyError error);
    }
}
