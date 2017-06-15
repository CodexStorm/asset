package com.ninja.ultron.entity;

/**
 * Created by Prabhu Sivanandam on 23-May-17.
 */

public class TransferReasonsEntity {

    int Id;
    String Name;

    public TransferReasonsEntity(int id, String name) {
        Id = id;
        Name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
