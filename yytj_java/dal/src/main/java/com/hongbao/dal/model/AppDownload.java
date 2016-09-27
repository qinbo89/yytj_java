package com.hongbao.dal.model;

import com.hongbao.dal.BaseEntityImpl;

/**
 * @author bes
 * 
 */
public class AppDownload extends BaseEntityImpl {
	private static final long serialVersionUID = -8888673395587732321L;
	private String downloadUrl;
	private String appName;

	/**
	 * @return the downloadUrl
	 */
	public String getDownloadUrl() {
		return downloadUrl;
	}

	/**
	 * @param downloadUrl
	 *            the downloadUrl to set
	 */
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	/**
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * @param appName
	 *            the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

}
