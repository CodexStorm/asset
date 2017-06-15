package com.ninja.ultron.entity;

/**
 * Created by omprakash on 18/5/17.
 */

public class AsgardUserPropertyMap {
    private int id;
    private City city;
    private Facility facility;
    private IDAndNameEntity localityCluster;

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

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public IDAndNameEntity getLocalityCluster() {
        return localityCluster;
    }

    public void setLocalityCluster(IDAndNameEntity localityCluster) {
        this.localityCluster = localityCluster;
    }
}
