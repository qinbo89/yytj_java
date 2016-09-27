package com.hongbao.utils;

/**
 * Created by shengshan.tang on 2015/6/9 0009 at 15:40
 */
public class KeyUtils {

    public static String regMobileKey(String mobile, Integer type) {
        return "regMobile_code_" + mobile+"_type_"+type;
    }

    public static String sendSmsCodeForForgetPwdKey(String mobile, Integer type) {
        return "sendSmsCodeForForgetPwd_code_" + mobile+"_type_"+type;
    }
}
