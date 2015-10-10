package com.mokylin.log.util;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/9/15.
 */
public class StringMap {
    private Map<String, Object> map;

    public StringMap() {
        this(new HashMap());
    }

    public StringMap(Map<String, Object> map) {
        this.map = map;
    }

    public StringMap put(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

    public StringMap putNotEmpty(String key, String value) {
        if(!StringUtils.isNullOrEmpty(value)) {
            this.map.put(key, value);
        }

        return this;
    }

    public StringMap putNotNull(String key, Object value) {
        if(value != null) {
            this.map.put(key, value);
        }

        return this;
    }

    public StringMap putWhen(String key, Object val, boolean when) {
        if(when) {
            this.map.put(key, val);
        }

        return this;
    }

    public StringMap putAll(Map<String, Object> map) {
        this.map.putAll(map);
        return this;
    }

    public StringMap putAll(StringMap map) {
        this.map.putAll(map.map);
        return this;
    }

    public Object get(String key) {
        return this.map.get(key);
    }

    public String getMapJson(){
        Gson gson = new Gson();
        String jsonStr = gson.toJson(map);
        return jsonStr;
    }
}
