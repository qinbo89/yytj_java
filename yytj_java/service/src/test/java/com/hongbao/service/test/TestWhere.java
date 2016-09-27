package com.hongbao.service.test;

import org.apache.commons.lang3.StringUtils;


public class TestWhere {
 
    public static void main(String[] args) {
        String s = " id,shopBuyHongbaoId,payOrderId,totalCost,cost,totalAmont,userId,createdAt,updatedAt";
        for ( String ss : s.split(",")){
            if(StringUtils.isNotBlank(StringUtils.trim(ss))){
                System.out.println("<if test=\"" + StringUtils.strip(StringUtils.trim(ss),"`") + " != null\">and  " + StringUtils.trim(ss) + " = #{" + StringUtils.strip(StringUtils.trim(ss),"`") + "} </if>");
            }
        }
    }
}
