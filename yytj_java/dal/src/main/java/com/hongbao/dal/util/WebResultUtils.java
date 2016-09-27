package com.hongbao.dal.util;

import com.hongbao.dal.base.controller.ResponseObject;

/**
 * Created by shengshan.tang on 2015/12/2 at 16:08
 */
public class WebResultUtils {

    public static<T> ResponseObject buildResult(T data){
        ResponseObject ret = new ResponseObject(data);
        return ret;
    }
    public static<T> ResponseObject buildResult(){
        ResponseObject ret = new ResponseObject();
        return ret;
    }
}
