package com.hongbao.dal.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hongbao.dal.log.HbLogContextMgr;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hongbao.utils.LogUtils;
public class ParameterInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ((handler instanceof HandlerMethod) == false) {
            return true;
        }
        LogUtils.logRequest(request);
        return super.preHandle(request, response, handler);
    }
}
