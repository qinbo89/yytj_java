package com.hongbao.service;

import javax.servlet.http.HttpServletResponse;


public interface RequestUtilService {

    boolean isFromHongbaoApp();

    String getCookie(String key);
    
    void setCookie(String key,String value,HttpServletResponse response);
    
    void getSetParam();

}
