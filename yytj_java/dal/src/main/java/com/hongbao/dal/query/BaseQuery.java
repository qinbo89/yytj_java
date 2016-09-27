package com.hongbao.dal.query;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * Created by shengshan.tang on 2015/6/8 0008 at 19:06
 */
public class BaseQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    private int pageSize = 20;  //default 20

    private int pageNum = 1;   //第几页

    private String from;  //来源（menu：菜单）

    private String tableName;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
