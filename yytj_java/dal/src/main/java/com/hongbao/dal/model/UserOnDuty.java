package com.hongbao.dal.model;

import java.util.Date;

import com.hongbao.dal.Archivable;
import com.hongbao.dal.BaseEntityImpl;



public class UserOnDuty extends BaseEntityImpl implements Archivable {

	private static final long serialVersionUID = 1L;

	private String userId;

	private Boolean archive;

	private long score;

	private Date dutyTime;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
	}

	public Date getDutyTime() {
		return dutyTime;
	}

	public void setDutyTime(Date dutyTime) {
		this.dutyTime = dutyTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public Boolean getArchive() {
		return archive;
	}

	@Override
	public void setArchive(Boolean archive) {
		this.archive = archive;
	}

}
