package com.hongbao.dal.base.annotation;

import com.hongbao.dal.enums.UserType;

@java.lang.annotation.Documented
@java.lang.annotation.Target(value = { java.lang.annotation.ElementType.METHOD })
@java.lang.annotation.Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface NeedLogin {
    public static final String FORWARDURL_LOGIN = "/toLogin";
    public static final String FORWARDURL_SYSTEM_BUSY = "/systemBusy";
    public static final String FORWARDURL_ACCESS_LIMITED = "/accessLimited";
    

    public static final String FORWARDURL_LOGIN_H5 = "/login.html";
    public static final String FORWARDURL_SYSTEM_BUSY_H5 = "/systemBusy.html";
    public static final String FORWARDURL_ACCESS_LIMITED_H5 = "/accessLimited.html";
    
    /**
     * 没有登录的时候转发的地址
     * @return
     */
    public String forwardUrl() default FORWARDURL_LOGIN;

    public UserType[] accessUserType() default { UserType.ALL };
}
