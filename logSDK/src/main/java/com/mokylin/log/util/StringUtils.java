package com.mokylin.log.util;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Administrator on 2015/9/15.
 */
public class StringUtils {

    private StringUtils(){
    }

    public static String join(Object[] array, String sep) {
        return join((Object[])array, sep, (String)null);
    }

    public static String join(Collection list, String sep) {
        return join((Collection)list, sep, (String)null);
    }

    public static String join(Collection list, String sep, String prefix) {
        Object[] array = list == null?null:list.toArray();
        return join(array, sep, prefix);
    }

    public static String join(Object[] array, String sep, String prefix) {
        if(array == null) {
            return "";
        } else {
            int arraySize = array.length;
            if(arraySize == 0) {
                return "";
            } else {
                if(sep == null) {
                    sep = "";
                }

                if(prefix == null) {
                    prefix = "";
                }

                StringBuilder buf = new StringBuilder(prefix);

                for(int i = 0; i < arraySize; ++i) {
                    if(i > 0) {
                        buf.append(sep);
                    }

                    buf.append(array[i] == null?"":array[i]);
                }

                return buf.toString();
            }
        }
    }

    public static String jsonJoin(String[] array) {
        int arraySize = array.length;
        int bufSize = arraySize * (array[0].length() + 3);
        StringBuilder buf = new StringBuilder(bufSize);

        for(int i = 0; i < arraySize; ++i) {
            if(i > 0) {
                buf.append(',');
            }

            buf.append('\"');
            buf.append(array[i]);
            buf.append('\"');
        }

        return buf.toString();
    }

    public static boolean isNullOrEmpty(String s) {
        return s == null || "".equals(s);
    }

    public static boolean inStringArray(String s, String[] array) {
        String[] var2 = array;
        int var3 = array.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String x = var2[var4];
            if(x.equals(s)) {
                return true;
            }
        }

        return false;
    }

    public static byte[] utf8Bytes(String data) {
        return data.getBytes(ConstantsUtils.UTF_8);
    }

    public static String utf8String(byte[] data) {
        return new String(data, ConstantsUtils.UTF_8);
    }

    public static boolean isLong(String value){
        try{
            long _v = Long.parseLong(value);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }

    public static String getNowDate(){
        SimpleDateFormat ymdStrFormat = new SimpleDateFormat("yyyyMMddHHmmss");
         return ymdStrFormat.format(new Date());
    }

    public static String getUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

}



