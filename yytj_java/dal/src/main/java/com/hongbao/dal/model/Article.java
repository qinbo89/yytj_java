package com.hongbao.dal.model;

import com.hongbao.dal.BaseEntityImpl;

public class Article extends BaseEntityImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String title;

	private String creator;

	private Integer viewCount;

	private Integer forwardCount;

	private Integer score;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public Integer getForwardCount() {
		return forwardCount;
	}

	public void setForwardCount(Integer forwardCount) {
		this.forwardCount = forwardCount;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	
	

}
