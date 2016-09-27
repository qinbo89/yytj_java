package com.hongbao.dal.model;

import com.hongbao.dal.Archivable;
import com.hongbao.dal.BaseEntityImpl;

/**
 * 类UserBind.java的实现描述：TODO 类实现描述
 * 
 * @author lv 2014年7月28日 下午7:56:16
 */
public class UserBind extends BaseEntityImpl implements Archivable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8805867864527991295L;

	private Long userId;

	private String uuid;

	private String deviceCode;

	private String deviceType;

	private String source;

	private String openId;

	private String nickName;

	private String gender;

	private String picUrl;

	private Boolean archive;

	private String unionId;

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * @param uuid
	 *            the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * @return the archive
	 */
	public Boolean getArchive() {
		return archive;
	}

	/**
	 * @param archive
	 *            the archive to set
	 */
	public void setArchive(Boolean archive) {
		this.archive = archive;
	}

	/**
	 * @return the deviceCode
	 */
	public String getDeviceCode() {
		return deviceCode;
	}

	/**
	 * @param deviceCode
	 *            the deviceCode to set
	 */
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	/**
	 * @return the deviceType
	 */
	public String getDeviceType() {
		return deviceType;
	}

	/**
	 * @param deviceType
	 *            the deviceType to set
	 */
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source
	 *            the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return the openId
	 */
	public String getOpenId() {
		return openId;
	}

	/**
	 * @param openId
	 *            the openId to set
	 */
	public void setOpenId(String openId) {
		this.openId = openId;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * @param nickName
	 *            the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the picUrl
	 */
	public String getPicUrl() {
		return picUrl;
	}

	/**
	 * @param picUrl
	 *            the picUrl to set
	 * 
	 */
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

}
