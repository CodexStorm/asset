package com.ninja.ultron.entity;

/**
 * Created by omprakash on 18/5/17.
 */

public class Facility {
    private int id;
    private City city;
    private String name;
    private String address;
    private String contactNumber;

    public Facility(int id, City city, String name, String address, String contactNumber){
        this.id = id;
        this.city = city;
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
    }

    public Facility(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
