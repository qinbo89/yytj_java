package com.hongbao.dal.enums;

import com.hongbao.dal.base.exception.BizException;


public enum UserType {
    NORMAL(1, "普通用户"),
    ADMIN(1, "普通用户"),
    ALL(0, "全部用户");


    private int code;
    private String description;

    private UserType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static UserType getByCode(int code) {
        for (UserType s : UserType.values()) {
            if (s.getCode() == code) {
                return s;
            }
        }
        return null;
    }
    public static void checkCode(int code){
        UserType userType =   getByCode(code);
        if(userType==null){
            throw new BizException("请检查用户类型");
        }
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
