package com.hongbao.dal.model;

import com.hongbao.dal.BaseEntityImpl;

public class AppVersion extends BaseEntityImpl {

	private static final long serialVersionUID = 1L;

	private String name;

	private String msg;

	private int version;

	private int fileSize;

	private String md5;

	private String url;

	/** 是否强制更新 */
	private Integer forceUpdate;
	/** 手机系统，是Android还是IOS */
	private String os;
	/** 该系统最小版本号 */
	private String osMinVer;

	private String app;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Integer getForceUpdate() {
		return forceUpdate;
	}

	public void setForceUpdate(Integer forceUpdate) {
		this.forceUpdate = forceUpdate;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getOsMinVer() {
		return osMinVer;
	}

	public void setOsMinVer(String osMinVer) {
		this.osMinVer = osMinVer;
	}

	/**
	 * @return the app
	 */
	public String getApp() {
		return app;
	}

	/**
	 * @param app
	 *            the app to set
	 */
	public void setApp(String app) {
		this.app = app;
	}

}
