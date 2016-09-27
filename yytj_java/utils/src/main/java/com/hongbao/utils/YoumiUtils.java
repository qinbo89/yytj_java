/*
 * Copyright 2010-2013 idonoo.com All right reserved. This software is the
 * confidential and proprietary information of idonoo.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with idonoo.com.
 */
package com.hongbao.utils;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * 类YoumiUtil.java的实现描述：TODO 类实现描述
 * 
 * @author lv 2014年8月27日 下午1:42:56
 */
public class YoumiUtils {

	/**
	 * 签名生成算法
	 * 
	 * @param HashMap
	 *            <String,String> params 请求参数集，所有参数必须已转换为字符串类型
	 * @param String
	 *            dev_server_secret 开发者在有米后台设置的密钥
	 * @return 签名
	 * @throws IOException
	 */
	public static String getSignature(Map<String, String> params,
			String dev_server_secret) throws IOException {
		// 先将参数以其参数名的字典序升序进行排序
		Map<String, String> sortedParams = new TreeMap<String, String>(params);

		Set<Map.Entry<String, String>> entrys = sortedParams.entrySet();
		// 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
		StringBuilder basestring = new StringBuilder();
		for (Map.Entry<String, String> param : entrys) {
			basestring.append(param.getKey()).append("=")
					.append(param.getValue());
		}
		basestring.append(dev_server_secret);
		// System.out.println(basestring.toString());
		// 使用MD5对待签名串求签
		byte[] bytes = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			bytes = md5.digest(basestring.toString().getBytes("UTF-8"));
		} catch (GeneralSecurityException ex) {
			throw new IOException(ex);
		}
		// 将MD5输出的二进制结果转换为小写的十六进制
		StringBuilder sign = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() == 1) {
				sign.append("0");
			}
			sign.append(hex);
		}
		return sign.toString();
	}
}
