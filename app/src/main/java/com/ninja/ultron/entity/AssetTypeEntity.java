package com.ninja.ultron.entity;

/**
 * Created by manoj on 20-06-2017.
 */

public class AssetTypeEntity {
    int Id;
    String Name;

    public AssetTypeEntity(int id, String name) {
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
