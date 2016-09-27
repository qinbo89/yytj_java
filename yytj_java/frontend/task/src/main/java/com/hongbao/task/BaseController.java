package com.hongbao.task;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hongbao.dal.base.controller.ResponseObject;
import com.hongbao.dal.base.exception.BizException;
import com.hongbao.dal.config.ApplicationConfig;
import com.hongbao.service.UserService;
import com.hongbao.utils.ExceptionUtils;
import com.hongbao.utils.JsonUtil;
import com.hongbao.utils.LogUtils;

public class BaseController {
    protected Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ApplicationConfig applicationConfig;

    
    @Autowired
    private UserService userService;

    
    @ExceptionHandler
    @ResponseBody
    public ResponseObject resolveException(HttpServletRequest request, Exception ex) {
        ResponseObject response = null;
        if (ex == null) {
            response = new ResponseObject();
            response.setSuccess(false);
            response.setMessage("系统错误.");
            log.error("Exception:"+ExceptionUtils.getPrintStackTrace(ex)  + "Request:"  +LogUtils.getRequestLog(request) + " CurrentUser:" + JsonUtil.object2Json(userService.getCurrentUser()) ,ex);
        } else {
            if (ex instanceof BizException) {
                BizException bizex = (BizException) ex;
                response = new ResponseObject();
                response.setSuccess(false);
                response.setResultCode(bizex.getResultCode());
                response.setMessage(ex.getMessage());
                response.setData(bizex.getData());

            } else {
                
                log.error("Exception:"+ExceptionUtils.getPrintStackTrace(ex)  + "Request:"  +LogUtils.getRequestLog(request) + " CurrentUser:" + JsonUtil.object2Json(userService.getCurrentUser()) ,ex);
                response = new ResponseObject();
                response.setSuccess(false);
                if (applicationConfig.isProd() == false) {
                    response.setMessage("Exception:"+ExceptionUtils.getPrintStackTrace(ex)  + "Request:"  +LogUtils.getRequestLog(request) + " CurrentUser:" + JsonUtil.object2Json(userService.getCurrentUser()) );
                } else {
                    response.setMessage("系统错误");
                }
            }
        }

        if (applicationConfig.isProd() == false) {
            LogUtils.logRequestWithResponse(request, response,null);
        }
        return response;
    }
}
