package com.hongbao.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class NumberUtil {
    public static final long MILLION = 1000000;

    public static String formatUserId(long id) {
        return new DecimalFormat("0000").format(id);
    }

    /**
     * 相加
     * @param num1
     * @param num2
     * @return
     */
    public static Integer add(Integer num1,Integer num2){
        if(num1 == null){
            num1 = 0;
        }
        if(num2 == null){
            num2 = 0;
        }
        return num1 + num2;
    }

    /**
     * 分转化成元
     * @param amount
     * @return
     */
    public static double fen2Yuan(Integer amount){
        if(amount == null){
            return 0.00;
        }else{
            return BigDecimal.valueOf(amount).divide(BigDecimal.valueOf(100)).doubleValue();
        }
    }

    /**
     * 分转化成元
     * @param amount
     * @return
     */
    public static double longFen2Yuan(Long amount){
        if(amount == null){
            return 0.00;
        }else{
            return BigDecimal.valueOf(amount).divide(BigDecimal.valueOf(100)).doubleValue();
        }
    }
}
