package com.mokylin.log.logdisruptor;

import com.mokylin.log.util.StringMap;

import java.util.List;

/**
 * Created by Administrator on 2015/10/10.
 */
public class DataEvent{
    private List<StringMap> stringMapList;

    public List<StringMap> getStringMapList() {
        return stringMapList;
    }

    public void setStringMapList(List<StringMap> stringMapList) {
        this.stringMapList = stringMapList;
    }
}
