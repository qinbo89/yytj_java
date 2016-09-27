package com.hongbao.service.user.vo;

import java.util.Date;


/**
 * @author bes
 * 
 */
public class ApprenticeVO  {
	private Long userId;
	private String userName;
	private String imgUrl;
	private Integer score;
	private Date relationTime;
	private Integer totalScore;  //徒弟总金额
	private Integer eithholding; //可预提金额
	private Integer yesterdayIncome; //昨日收入


	
	/**
	 * @return the totalScore
	 */
	public Integer getTotalScore() {
		return totalScore;
	}

	/**
	 * @param totalScore the totalScore to set
	 */
	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}

	/**
	 * @return the eithholding
	 */
	public Integer getEithholding() {
		return eithholding;
	}

	/**
	 * @param eithholding the eithholding to set
	 */
	public void setEithholding(Integer eithholding) {
		this.eithholding = eithholding;
	}

	/**
	 * @return the yesterdayIncome
	 */
	public Integer getYesterdayIncome() {
		return yesterdayIncome;
	}

	/**
	 * @param yesterdayIncome the yesterdayIncome to set
	 */
	public void setYesterdayIncome(Integer yesterdayIncome) {
		this.yesterdayIncome = yesterdayIncome;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the imgUrl
	 */
	public String getImgUrl() {
		return imgUrl;
	}

	/**
	 * @param imgUrl
	 *            the imgUrl to set
	 */
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	/**
	 * @return the score
	 */
	public Integer getScore() {
		return score;
	}

	/**
	 * @param score
	 *            the score to set
	 */
	public void setScore(Integer score) {
		this.score = score;
	}

	/**
	 * @return the relationTime
	 */
	public Date getRelationTime() {
		return relationTime;
	}

	/**
	 * @param relationTime
	 *            the relationTime to set
	 */
	public void setRelationTime(Date relationTime) {
		this.relationTime = relationTime;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	

}
