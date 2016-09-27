package com.hongbao.web.controller.base;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hongbao.dal.base.controller.ResponseObject;
import com.hongbao.dal.config.ApplicationConfig;
import com.hongbao.service.UserService;
import com.hongbao.service.util.ServiceUtil;

public class BaseController {
    protected Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ApplicationConfig applicationConfig;

    
    @Autowired
    protected UserService userService;

    @Autowired
    private ServiceUtil serviceUtil;
    
    @ExceptionHandler
    @ResponseBody
    public ResponseObject resolveException(HttpServletRequest request, Exception ex) {
        return serviceUtil.getRestResponse(ex);
    }
}
