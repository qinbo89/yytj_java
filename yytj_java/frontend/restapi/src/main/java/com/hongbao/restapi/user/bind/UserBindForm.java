package com.hongbao.restapi.user.bind;

import org.hibernate.validator.constraints.NotBlank;

import com.hongbao.dal.model.UserBind;


/**
 * 类UserBindForm.java的实现描述：TODO 类实现描述
 * 
 * @author tatos 2014年7月29日 上午11:40:55
 */
public class UserBindForm extends UserBind {

	@NotBlank(message = "{valid.notBlank.message}")
	private String openId;

	@NotBlank(message = "{valid.notBlank.message}")
	private String source;

	private String uuid;

	/**
	 * 
	 */
	private static final long serialVersionUID = -3847959600801690463L;

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

}
