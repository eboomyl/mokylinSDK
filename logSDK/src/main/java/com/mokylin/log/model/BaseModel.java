package com.mokylin.log.model;

import com.mokylin.log.util.StringUtils;
import org.json.simple.JSONObject;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2015/10/13.
 */
public class BaseModel {
    public JSONObject getDtoToJson() {
        JSONObject objectDto = new JSONObject();
        try {
            for (Field dd : this.getClass().getFields()) {
                if (!StringUtils.isNullOrEmpty((String) dd.get(this))) {
                    objectDto.put(dd.getName(), dd.get(this));
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return objectDto;

    }

    public JSONObject getDtoToStringMap() {
        JSONObject objectDto = new JSONObject();
        try {
            for (Field dd : this.getClass().getFields()) {
                if (!StringUtils.isNullOrEmpty((String) dd.get(this))) {
                    objectDto.put(dd.getName(), dd.get(this));
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return objectDto;

    }


}
