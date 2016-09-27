package com.hongbao.utils;

import java.text.DecimalFormat;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class PriceUtil {
    public static String feng2DotYuan(Integer price) {
        if(price==null){
            price = 0;
        }
        return new DecimalFormat("0.00").format(price * 0.01);
    }
    public static String feng2DotYuan(String price) {
        if(StringUtils.isBlank(price)){
            price = "";
        }
        return new DecimalFormat("0.00").format(NumberUtils.toInt(price) * 0.01);
    }
}
