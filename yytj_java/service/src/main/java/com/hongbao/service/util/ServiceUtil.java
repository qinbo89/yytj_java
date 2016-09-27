package com.hongbao.service.util;

import java.lang.reflect.Method;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hongbao.dal.base.annotation.ConcurrencyControl;
import com.hongbao.dal.base.annotation.NeedLogin;
import com.hongbao.dal.base.annotation.NotNeedLogin;
import com.hongbao.dal.base.consts.ResultCode;
import com.hongbao.dal.base.controller.ResponseObject;
import com.hongbao.dal.base.exception.BizException;
import com.hongbao.dal.config.ApplicationConfig;
import com.hongbao.dal.enums.UserType;
import com.hongbao.dal.log.HbLogContextMgr;
import com.hongbao.dal.model.User;
import com.hongbao.dal.redis.JedisUtil;
import com.hongbao.service.RequestUtilService;
import com.hongbao.service.UserService;
import com.hongbao.service.support.ResponseHolder;
import com.hongbao.service.user.impl.WeixinService;
import com.hongbao.utils.DateUtil;
import com.hongbao.utils.ExceptionUtils;
import com.hongbao.utils.LogUtils;

@Component(value = "serviceUtil")
public class ServiceUtil {

    @Autowired
    private ApplicationConfig  applicationConfig;

    @Autowired
    private JedisUtil          jedisUtil;

    private final Logger       log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RequestUtilService requestUtilService;

    @Autowired
    private UserService        userService;

    @Autowired
    private WeixinService      weixinService;

    public Object aroundController(final ProceedingJoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        User user = userService.getCurrentUser();
        HttpServletResponse response = (HttpServletResponse) ResponseHolder.get();
        if (user != null) {
            request.setAttribute("currentUser", user);
            requestUtilService.setCookie("_current_user_id", user.getId() + "", response);
        } else {
            requestUtilService.setCookie("_current_user_id", "", response);
        }
        String loginId = requestUtilService.getCookie(UserService.KEY_LOGIN_ID);
        if (StringUtils.isNotBlank(loginId)) {
            requestUtilService.setCookie(UserService.KEY_LOGIN_ID, loginId, response);
        }
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();

        Object returnObj = null;

        try {
            setCurrentUser();
            // 权限检查
            
            checkAuth(method);

            // 并发控制
            checkConcurrency(method);

            returnObj = joinPoint.proceed();
        } catch (Throwable ex) {
            log.error(ex.getMessage(), ex);

            returnObj = getReturnObj(method, ex);
        }finally{
        	userService.clearSloginId();
        }
        if (applicationConfig.isProd() == false || true) {
            LogUtils.logRequestWithResponse(request, returnObj, response);
        }
        return returnObj;
    }

    private void checkAuth(Method method) {
        NotNeedLogin notNeedLogin = method.getAnnotation(NotNeedLogin.class);
        if (notNeedLogin != null) {
            // 不需要登录
            return;
        }

        boolean success = false;
        User user = userService.getCurrentUser();
        NeedLogin needLogin = method.getAnnotation(NeedLogin.class);
        // add by tangshengshan
        if (notNeedLogin == null && needLogin == null) {
            return;
        }
        // end

        if (user == null) {
            throw new BizException(ResultCode.NOT_LOGIN);
        } else {
            UserType userType = UserType.getByCode(user.getType());
            UserType[] accessUserType = needLogin.accessUserType();
            for (UserType t : accessUserType) {
                if (t == userType || t == UserType.ALL) {
                    success = true;
                    break;
                }
            }
            if (success == false) {
                throw new BizException(ResultCode.ACCESS_LIMITED);
            }
        }
    }

    private void checkConcurrency(Method method) {

        HttpServletResponse response = (HttpServletResponse) ResponseHolder.get();
        if (response == null) {
            return;
        }
        String accessControlId = requestUtilService.getCookie(UserService.ACCESS_CONTROL_ID);
        String accessControlVal = requestUtilService.getCookie(UserService.ACCESS_CONTROL_VAL);

        User user = userService.getCurrentUser();

        if ((StringUtils.isBlank(accessControlId) || accessControlId.length() < 10) && user == null) {
            accessControlId = initCheckConcurrency(response);
        }

        // 开始校验
        int expectVal = NumberUtils.toInt(accessControlVal) + 1;
        int actualVal = jedisUtil.increase(accessControlId).intValue();

        // 直接覆盖VAL
        Cookie accessControlValCookie = new Cookie(UserService.ACCESS_CONTROL_VAL, actualVal + "");
        accessControlValCookie.setMaxAge(DateUtil.MONTH_SECONDS);
        accessControlValCookie.setPath("/");
        response.addCookie(accessControlValCookie);

        if (method.getAnnotation(ConcurrencyControl.class) != null) {
            if (expectVal != actualVal) {
                log.warn("accessControlId=" + accessControlId + ",expectVal=" + expectVal + ",but actualVal=" + actualVal);
                initCheckConcurrency(response);
                throw new BizException(ResultCode.SYSTEM_BUSY);
            }
        }
    }

    private String initCheckConcurrency(HttpServletResponse response) {
        String accessControlId = UUID.randomUUID().toString();
        Cookie accessControlIdCookie = new Cookie(UserService.ACCESS_CONTROL_ID, accessControlId);
        accessControlIdCookie.setMaxAge(DateUtil.MONTH_SECONDS);
        accessControlIdCookie.setPath("/");
        response.addCookie(accessControlIdCookie);

        Cookie accessControlValCookie = new Cookie(UserService.ACCESS_CONTROL_VAL, "0");
        accessControlValCookie.setMaxAge(DateUtil.MONTH_SECONDS);
        accessControlValCookie.setPath("/");
        response.addCookie(accessControlValCookie);

        log.warn("initCheckConcurrency accessControlId=" + accessControlId);

        return accessControlId;
    }

    public String getErrorMesssage(Throwable ex) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // log.error("Exception:" + ExceptionUtils.getPrintStackTrace(ex) +
        // "Request:" + LogUtils.getRequestLog(request));

        String errorMessage = null;
        if (ex != null && ex.getCause() instanceof BizException) {
            ex = ex.getCause();
        }
        if (ex instanceof BizException) {
            errorMessage = ex.getMessage();
        } else {
            if (applicationConfig.isProd() == false) {
                errorMessage = ExceptionUtils.getPrintStackTrace(ex);// ex.getMessage();//
            } else {
                errorMessage = "系统错误";
            }
        }
        if (StringUtils.isBlank(errorMessage)) {
            errorMessage = "系统错误";
        }
        return errorMessage;
    }

    private Object getReturnObj(Method method, Throwable ex) {
        Object returnObj;
        ResponseBody responseBody = method.getAnnotation(ResponseBody.class);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (responseBody != null) {
            returnObj = getRestResponse(ex);
        } else {
            returnObj = getH5Response(ex);
        }
        return returnObj;
    }

    public Object getH5Response(Throwable ex) {
        Object returnObj;

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        returnObj = request.getServletPath().replace(".html", "");

        request.setAttribute("errorMessage", getErrorMesssage(ex));

        if (ex instanceof BizException) {
            BizException bizEx = (BizException) ex;
            if (bizEx.getResultCode() != null && bizEx.getResultCode() == ResultCode.NOT_LOGIN) {
                HttpServletResponse response = (HttpServletResponse) ResponseHolder.get();
                if (response != null) {
                    try {
                        String queryString = StringUtils.trimToEmpty(request.getQueryString());
                        response.sendRedirect(request.getContextPath() + NeedLogin.FORWARDURL_LOGIN_H5 + "?returl=" + request.getRequestURL().toString()
                                              + (StringUtils.isBlank(queryString) ? "" : "?" + queryString));
                        returnObj = null;
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                }
            }
        }
        return returnObj;
    }

    public ResponseObject getRestResponse(Throwable ex) {
        HbLogContextMgr.errorLog(ex);
        ResponseObject ret = new ResponseObject();
        ret.setSuccess(false);
        ret.setMessage(getErrorMesssage(ex));
        if (ex instanceof BizException) {
            ret.setResultCode(((BizException) ex).getResultCode());
        }
        return ret;
    }

    private void setCurrentUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String url = request.getRequestURI();
        String userId = request.getParameter("userId");

        if (!StringUtils.isBlank(userId) && StringUtils.startsWith(url, "/app/login")) {// 登陆
            Long _userId = new Long(userId);
            User user = userService.load(_userId);
            userService.setCurrentUser(user);
            return;
        }
        
        if (!StringUtils.isBlank(userId) && StringUtils.startsWith(url, "/app/login")) {// 登陆
            Long _userId = new Long(userId);
            User user = userService.load(_userId);
            userService.setCurrentUser(user);
            return;
        }
        String code = request.getParameter("code");
        if (!StringUtils.isBlank(userId) && StringUtils.startsWith(url, "/weixin") && StringUtils.isBlank(code)) {// 登陆
            Long _userId = new Long(userId);
            User user = userService.load(_userId);
            userService.setCurrentUser(user);
            return;
        }

        

        if (StringUtils.startsWith(url, "/weixin")) {
            if (!StringUtils.isBlank(code)) {
                String openId = weixinService.getOpenId(code);
                User user = userService.loadUserByOpenId(openId);
                if (!StringUtils.isBlank(openId)) {
                    userService.setCurrentUser(user);
                }
            }
        }
    }

}
