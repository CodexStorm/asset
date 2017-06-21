package com.ninja.ultron.entity;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manoj on 21-06-2017.
 */

public class SkuAssetDetailsEntity {
    String skuName;
    List<String> list;
    List<String> modifiedList = new ArrayList<>();
    String details[] = new String[3];

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public List<String> getList() {

        for(int i = 0 ; i<list.size() ; i++)
        {
            details = list.get(i).split("\\@");
            Log.d("Details",details[0]+"   "+details[1]+"  "+details[2]);
            modifiedList.add(i,details[0]+"        "+details[1]+"  "+details[2]);
        }
        return modifiedList;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
