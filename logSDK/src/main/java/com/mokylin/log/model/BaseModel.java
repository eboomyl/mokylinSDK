package com.mokylin.log.model;

import com.mokylin.log.util.StringUtils;
import org.json.simple.JSONObject;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/13.
 */
public class BaseModel {
    public JSONObject getDtoToJson() {
        JSONObject objectDto = new JSONObject();
        setData(objectDto);
        return objectDto;

    }

    public Map<String, Object> getDtoToStringMap() {
        Map<String, Object> map = new HashMap<>();
        setData(map);
        return map;

    }

    private void setData(Object obj){
        try {
            for (Field dd : this.getClass().getFields()) {
                if (!StringUtils.isNullOrEmpty((String) dd.get(this))) {
                    if(obj instanceof Map){
                        ((Map<String, Object>)obj).put(dd.getName(), dd.get(this));
                    }else if (obj instanceof JSONObject){
                        ((JSONObject)obj).put(dd.getName(), dd.get(this));
                    }

                }
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
