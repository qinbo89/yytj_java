package com.hongbao.utils;

import org.apache.commons.lang3.StringUtils;
import org.xerial.snappy.Snappy;

/**
 * Created by shengshan.tang on 9/24/2015 at 10:54 AM
 */
public class CompressUtils {

    /**
     * 压缩
     * @param data
     * @return
     */
    public static byte [] compress(String data){
        byte [] result = null;
        if(StringUtils.isEmpty(data)){
            return result;
        }
        try{
            result = Snappy.compress(data.getBytes("UTF-8"));
        }catch (Exception e){
        }
        return result;
    }

    /**
     * 解压缩
     * @param data
     * @return
     */
    public static String uncompress(byte [] data){
        String result = null;
        if(data == null){
            return result;
        }
        try{
            byte rbyte [] = Snappy.uncompress(data);
            result = new String(rbyte,"UTF-8");
        }catch (Exception e){
        }
        return result;

    }

    public static void main(String[] args) {
        try{
            String input = "Hello snappy-java! Snappy-java is a JNI-based wrapper of "
                    + "Snappy, a fast compresser/decompresser.";
            byte[] compressed = Snappy.compress(input.getBytes("UTF-8"));
            byte[] uncompressed = Snappy.uncompress(compressed);
            String result = new String(uncompressed, "UTF-8");
            System.out.println(compressed.length);
            System.out.println(uncompressed.length);
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
