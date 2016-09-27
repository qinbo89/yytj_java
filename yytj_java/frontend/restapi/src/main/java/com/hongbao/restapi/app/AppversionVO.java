package com.hongbao.restapi.app;

public class AppversionVO {
	/** 客户端版本 */
	private int clientVersion;
	/** 手机类型，android或者IOS */
	private String osType;
	/** 应用类型，目前有两个值：sk */
	private String appType;

	public int getClientVersion() {
		return clientVersion;
	}

	public void setClientVersion(int clientVersion) {
		this.clientVersion = clientVersion;
	}

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

}
