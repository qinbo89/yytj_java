package com.hongbao.utils;

import java.util.HashMap;
import java.util.Map;

public class MapStringBuilder {
    
    public static MapStringBuilder builder() {
        return new MapStringBuilder();
    }
    
    private Map<String, String> map;

    public MapStringBuilder() {
        super();
        map = new HashMap<String, String>();
    }

    public MapStringBuilder put(String key, Object value) {
        map.put(key, value+"");
        return this;
    }

    public Map<String, String> build() {
        return map;
    }
}
