package com.hongbao.web.controller.base;

import com.hongbao.dal.base.controller.ResponseObject;
import com.hongbao.service.util.ServiceUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by shengshan.tang on 2015/11/25 at 18:57
 */
public class BaseController {

    protected final static Logger bisLog = org.slf4j.LoggerFactory.getLogger("bis");

    protected final static Logger log = org.slf4j.LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private ServiceUtil serviceUtil;

    @ExceptionHandler
    @ResponseBody
    public ResponseObject resolveException(HttpServletRequest request, Exception ex) {
        return serviceUtil.getRestResponse(ex);
    }

}
