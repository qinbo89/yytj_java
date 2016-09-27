package com.hongbao.utils;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

public class StringUtil {

    public static final String ABC123[] = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
            "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

    public static final String NUM[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

    public static String randomNum(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append(NUM[RandomUtils.nextInt(0, NUM.length)]);
        }
        return sb.toString();
    }

    public static String randomAbc123(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append(ABC123[RandomUtils.nextInt(0, ABC123.length)]);
        }
        return sb.toString();
    }

    /**
     * 字符串转换unicode
     */
    public static String string2Unicode(String string) {

        if(StringUtils.isBlank(string)){
            return "";
        }
        
        StringBuffer unicode = new StringBuffer();

        for (int i = 0; i < string.length(); i++) {

            // 取出每一个字符
            char c = string.charAt(i);

            // 转换为unicode
            unicode.append("\\u" + Integer.toHexString(c));
        }

        return unicode.toString();
    }

    /**
     * unicode 转字符串
     */
    public static String unicode2String(String unicode) {
        if(StringUtils.isBlank(unicode)){
            return "";
        }
        StringBuffer string = new StringBuffer();

        String[] hex = unicode.split("\\\\u");

        for (int i = 1; i < hex.length; i++) {

            // 转换出每一个代码点
            int data = Integer.parseInt(hex[i], 16);

            // 追加成string
            string.append((char) data);
        }

        return string.toString();
    }

    // 转化字符串为十六进制编码
    public static String toHexString(String s) {
        if(StringUtils.isBlank(s)){
            return "";
        }
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }

    // 转化十六进制编码为字符串
    public static String toStringHex(String s) {
        if(StringUtils.isBlank(s)){
            return "";
        }
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "utf-8");// UTF-16le:Not
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }
    
    public static String emptyToEmpty(Object o) {
        if (o == null || StringUtils.isBlank(o.toString())) {
            return "";
        }
        return o.toString();
    }    
}
