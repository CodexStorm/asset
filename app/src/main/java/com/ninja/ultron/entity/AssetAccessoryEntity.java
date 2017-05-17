package com.ninja.ultron.entity;

/**
 * Created by manoj on 17-05-2017.
 */

public class AssetAccessoryEntity {
    private String name;
    private String id;

    public AssetAccessoryEntity(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
