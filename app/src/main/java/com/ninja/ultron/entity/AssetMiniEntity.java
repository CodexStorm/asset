package com.ninja.ultron.entity;

/**
 * Created by Prabhu Sivanandam on 17-May-17.
 */

public class AssetMiniEntity {

        String Name;
        String Id;

        public AssetMiniEntity(String name, String id) {
            Name = name;
            Id = id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }
}
