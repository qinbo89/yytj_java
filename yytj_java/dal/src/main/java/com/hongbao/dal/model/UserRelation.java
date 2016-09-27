package com.hongbao.dal.model;

import com.hongbao.dal.Archivable;
import com.hongbao.dal.BaseEntityImpl;

/**
 * 用户师徒关系
 * 
 * @author ThinkPad
 *
 */
public class UserRelation extends BaseEntityImpl implements Archivable {
	private static final long serialVersionUID = 3612309188465004087L;
	/** 用户编号 */
	private Long userId;
	/** 用户微信openId */
	private String openId;
	/** 师父的编号 */
	private Long parentUserId;
	/** 师父的微信openId */
	private String parentOpenId;
	private Boolean archive;

	

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getParentUserId() {
		return parentUserId;
	}

	public void setParentUserId(Long parentUserId) {
		this.parentUserId = parentUserId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	

	public String getParentOpenId() {
		return parentOpenId;
	}

	public void setParentOpenId(String parentOpenId) {
		this.parentOpenId = parentOpenId;
	}

	public Boolean getArchive() {
		return archive;
	}

	public void setArchive(Boolean archive) {
		this.archive = archive;
	}

}
