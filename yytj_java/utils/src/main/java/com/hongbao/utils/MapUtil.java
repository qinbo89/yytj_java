package com.hongbao.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class MapUtil {
    
    public static Map copy(Map origin){
        Iterator<Map.Entry>   it = origin.entrySet().iterator();
        Map map = new LinkedHashMap();
        Map.Entry<Object, Object> next = null;
        while(it.hasNext()){
            next =  it.next();
            map.put(next.getKey(), next.getValue());
        }
        return map;
    }
    
    public static String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }
}
