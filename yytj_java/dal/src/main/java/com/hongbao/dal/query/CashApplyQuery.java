package com.hongbao.dal.query;

/**
 * Created by shengshan.tang on 2015/6/9 0009 at 18:20
 */
public class CashApplyQuery extends BaseQuery {


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
