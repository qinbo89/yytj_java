package com.hongbao.utils;

import java.util.HashMap;
import java.util.Map;

public class MapBuilder {
    
    public static MapBuilder builder() {
        return new MapBuilder();
    }
    
    private Map<String, Object> map;

    public MapBuilder() {
        super();
        map = new HashMap<String, Object>();
    }

    public MapBuilder put(String key, Object value) {
        map.put(key, value);
        return this;
    }

    public MapBuilder put(String key, Object value,boolean putCondition) {
        if(putCondition){
            map.put(key, value);
        }
        return this;
    }
    
    public Map<String, Object> build() {
        return map;
    }
}
