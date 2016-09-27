package com.hongbao.utils;


import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

public class MD5Util {

    
    public static String MD5Encode(String key) {
        if(StringUtils.isBlank(key)){
            key = "";
        }
        return DigestUtils.md5Hex(key).toUpperCase();
    }
    
    public static String MD5EncodeForPwd(String key, String pwd) {
        if(StringUtils.isBlank(key)){
            key = "";
        }
        if(StringUtils.isBlank(pwd)){
            pwd = "";
        }
        return DigestUtils.md5Hex(key+ "_" + pwd).toUpperCase();
    }

    public static String MD5Encode(byte data[]) {
        return DigestUtils.md5Hex(data).toUpperCase();
    }
    public static void main(String[] args) {
    	System.out.println(System.getProperty("user.home"));
    	String ss="http://user.dianler.com/callback/guoying.php?gameid=7&cid=141&app=1008041119&idfa={idfa}".replaceAll("\\{idfa\\}", "1");
    	System.out.println(ss);
    }
}
