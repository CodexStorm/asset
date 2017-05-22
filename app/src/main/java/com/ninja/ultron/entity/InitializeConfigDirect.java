package com.ninja.ultron.entity;

public class InitializeConfigDirect {
    private int id;
    private int minAndroidAppVersion;
    private int mandatoryUpdateFlag;
    private String customerCareNumber;
    private String customerCareEmail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMinAndroidAppVersion() {
        return minAndroidAppVersion;
    }

    public void setMinAndroidAppVersion(int minAndroidAppVersion) {
        this.minAndroidAppVersion = minAndroidAppVersion;
    }

    public int getMandatoryUpdateFlag() {
        return mandatoryUpdateFlag;
    }

    public void setMandatoryUpdateFlag(int mandatoryUpdateFlag) {
        this.mandatoryUpdateFlag = mandatoryUpdateFlag;
    }

    public String getCustomerCareNumber() {
        return customerCareNumber;
    }

    public void setCustomerCareNumber(String customerCareNumber) {
        this.customerCareNumber = customerCareNumber;
    }

    public String getCustomerCareEmail() {
        return customerCareEmail;
    }

    public void setCustomerCareEmail(String customerCareEmail) {
        this.customerCareEmail = customerCareEmail;
    }
}
