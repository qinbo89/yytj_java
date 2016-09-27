package com.hongbao.dal.model;

public class UserScoreTopTen {
	private Long userId;
	private String nickName;
	private String userImg;
	private Long totalScore;
	private Integer rank;
	

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}
	public Long getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(Long totalScore) {
		this.totalScore = totalScore;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}


}
