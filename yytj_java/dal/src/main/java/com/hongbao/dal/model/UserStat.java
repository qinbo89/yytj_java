package com.hongbao.dal.model;

import com.hongbao.dal.BaseEntityImpl;

public class UserStat extends BaseEntityImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3596967375073787631L;

	private String uuid;

	private Long userId;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
