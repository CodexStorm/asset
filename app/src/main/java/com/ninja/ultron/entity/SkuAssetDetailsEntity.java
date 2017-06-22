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
    String details[];

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public List<String> getList() {
        //Log.d("size",list.size()+"");
        for(int i = 0 ; i<list.size() ; i++)
        {
            details = new String[list.get(i).split("\\@").length];
            details = list.get(i).split("\\@");
            Log.d("Details",details[0]+"   "+details[1]);
            modifiedList.add(i,details[0]+"        "+details[1]);
        }
        return modifiedList;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
