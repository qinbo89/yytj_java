package com.hongbao.keepalive;

public class ParseDataUtil {

	public static String cmd_sw_front_back = "02";

	public static String cmd_sw_back_front = "03";

	public static String keepAliveData = "01";

	public static String getUserId(String data) {
		String[] dataArry = data.split("_");
		if (dataArry == null || dataArry.length != 3) {
			return "-1";
		}
		return dataArry[0];

	}

	public static String getSigin(String data) {
		String[] dataArry = data.split("_");
		if (dataArry == null || dataArry.length != 3) {
			return "-1";
		}
		return dataArry[1];

	}

	public static String getBzdata(String data) {
		String[] dataArry = data.split("_");
		if (dataArry == null || dataArry.length != 3) {
			return "-1";
		}
		return dataArry[2];
	}

	public static void main(String[] args) {
		System.out.println(ParseDataUtil.getBzdata("11_1111_02"));
	}
}
