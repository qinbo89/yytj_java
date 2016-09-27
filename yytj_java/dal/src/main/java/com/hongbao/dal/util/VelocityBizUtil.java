package com.hongbao.dal.util;


import com.hongbao.dal.enums.UserType;

public class VelocityBizUtil {
    
    public String userType(Integer type) {
        if (type == null) {
            return "";
        }
        UserType userType = UserType.getByCode(type);
        if (userType != null) {
            return userType.getDescription();
        }
        return "";
    }


}
