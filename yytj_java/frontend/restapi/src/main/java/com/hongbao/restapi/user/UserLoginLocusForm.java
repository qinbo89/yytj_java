package com.hongbao.restapi.user;

import org.hibernate.validator.constraints.NotBlank;

public class UserLoginLocusForm {
	/** 用户编号 */
	@NotBlank(message = "{valid.notBlank.message}")
	private String userId;
	/** 设备编号 */
	@NotBlank(message = "{valid.notBlank.message}")
	private String uuid;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}


}
