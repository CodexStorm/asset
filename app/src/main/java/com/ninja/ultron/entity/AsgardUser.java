package com.ninja.ultron.entity;

/**
 * Created by omprakash on 18/5/17.
 */

public class AsgardUser {
    private int id;
    private String userName;
    private long contactNumber;
    private String email;
    private String password;
    private String externalUserId;
    private String roles;
    private AsgardUserPropertyMap asgardUserPropertyMap;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(long contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public AsgardUserPropertyMap getAsgardUserPropertyMap() {
        return asgardUserPropertyMap;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getExternalUserId() {
        return externalUserId;
    }

    public void setExternalUserId(String externalUserId) {
        this.externalUserId = externalUserId;
    }

    public void setAsgardUserPropertyMap(AsgardUserPropertyMap asgardUserPropertyMap) {
        this.asgardUserPropertyMap = asgardUserPropertyMap;
    }
}
