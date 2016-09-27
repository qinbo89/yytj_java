package com.hongbao.dal.model;


import java.util.Date;

import com.hongbao.dal.BaseEntityImpl;

public class AppCtr  extends BaseEntityImpl {



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date startTime;

	private Long appId;

	private Date endTime;
	
	private int num;
	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}
	
	


}
