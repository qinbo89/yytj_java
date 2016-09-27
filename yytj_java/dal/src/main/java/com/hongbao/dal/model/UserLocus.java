package com.hongbao.dal.model;

import java.util.Date;

import com.hongbao.dal.BaseEntityImpl;



/**
 * 记录用户登录轨迹的实体类
 * 
 * @author 于东伟
 * 
 */
public class UserLocus extends BaseEntityImpl {

	private static final long serialVersionUID = -4461276734357171464L;
	/** 用户编号 */
	private Long userId;
	/** 设备类型 */
	private String deviceType;
	/** 设备编号 */
	private String deviceId;

	/**
	 * 当前登陆的签名
	 */
	private String sigin;

	/** 登录时间 */
	private Date loginTime;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getSigin() {
		return sigin;
	}

	public void setSigin(String sigin) {
		this.sigin = sigin;
	}

}
