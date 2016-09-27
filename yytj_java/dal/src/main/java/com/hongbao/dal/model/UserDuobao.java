package com.hongbao.dal.model;

import com.hongbao.dal.BaseEntityImpl;

public class UserDuobao extends BaseEntityImpl {
	private static final long serialVersionUID = 2560250242912137270L;
	private Long userId;
	private Long duobaoId;
	private Integer score;
	private Integer win;
	
	private Duobao duobao;
	private User user;
	
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Duobao getDuobao() {
        return duobao;
    }
    public void setDuobao(Duobao duobao) {
        this.duobao = duobao;
    }
    public Integer getWin() {
        return win;
    }
    public void setWin(Integer win) {
        this.win = win;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getDuobaoId() {
        return duobaoId;
    }
    public void setDuobaoId(Long duobaoId) {
        this.duobaoId = duobaoId;
    }
    public Integer getScore() {
        return score;
    }
    public void setScore(Integer score) {
        this.score = score;
    }
}
