package com.ninja.ultron.entity;

import java.util.List;

/**
 * Created by manoj on 21-06-2017.
 */

public class SkuAssetDetailsEntity {
    String skuName;
    List<String> list;

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
