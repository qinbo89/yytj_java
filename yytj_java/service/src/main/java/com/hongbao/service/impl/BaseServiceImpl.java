package com.hongbao.service.impl;



import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hongbao.dal.model.AppVersion;


public abstract class BaseServiceImpl<T> {
    protected Logger log = LoggerFactory.getLogger(getClass());

   
    protected abstract T load(Long id);
 
    
    protected T checkAndGetSingle(List<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        if (list.size() > 1) {
            throw new TooManyResultsException("系统查询错误");
        }
        return list.get(0);
    }
    
    
}
