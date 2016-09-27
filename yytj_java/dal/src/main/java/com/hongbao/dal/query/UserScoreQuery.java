package com.hongbao.dal.query;

/**
 * Created by shengshan.tang on 2015/12/1 at 22:12
 */
public class UserScoreQuery extends  BaseQuery {

    private Long userId;

    private String type;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
