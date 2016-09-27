package com.hongbao.service.impl;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hongbao.dal.redis.JedisUtil;
import com.hongbao.service.RequestUtilService;
import com.hongbao.service.UserService;
import com.hongbao.utils.DateUtil;
import com.hongbao.utils.WebUtils;

@Service("requestUtilService")
public class RequestUtilServiceImpl implements RequestUtilService {

    protected Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private JedisUtil jedisUtil;
    @Autowired
    private UserService userService;


    @Override
    public boolean isFromHongbaoApp() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
         String reqFrom = getHeader("reqFrom",request);
        return "hongbaoApp".equals(reqFrom);
    }

    private String getHeader(String name, HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames != null && headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            if (StringUtils.isNotBlank(headerName) && headerName.equalsIgnoreCase(name)) {
                return request.getHeader(headerName);
            }
        }
        return "";
    }

    @Override
    public String getCookie(String key) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        return getCookie(request.getCookies(), key);
    }

    private String getCookie(Cookie[] cookies, String key) {

        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (key.equalsIgnoreCase(cookie.getName()) && StringUtils.isNotBlank(cookie.getValue())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public void getSetParam() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        Enumeration<String> parameterNames = request.getParameterNames();
        String parameterName = null;
        String parameterValue = null;
        while (parameterNames.hasMoreElements()) {
            parameterName = parameterNames.nextElement();
            parameterValue = request.getParameter(parameterName);
            request.setAttribute(parameterName, parameterValue);
        }
    }

    
    @Override
    public void setCookie(String key, String value, HttpServletResponse response) {
        if (response == null) {
            return;
        }
        if(StringUtils.isEmpty(key)){
            return;
        }
        try{
            Cookie cookie = new Cookie(key, value);
            cookie.setMaxAge(DateUtil.MONTH_SECONDS);
            cookie.setPath("/");
            response.addCookie(cookie);
        }catch (Exception e){
        }

    }
}
