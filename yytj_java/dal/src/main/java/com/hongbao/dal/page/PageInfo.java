package com.hongbao.dal.page;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class PageInfo<T> implements Serializable{

    private static final long serialVersionUID = 1L;

    public static final Integer DEFAULT_PAGE_SIZE = 20;

    private Integer pageNum = 1;

    private Integer pageSize = DEFAULT_PAGE_SIZE;

    private Integer totalCount;

    private Integer totalPage;

    private List<T> records;

    @JsonIgnore
    private Integer offset;

    public PageInfo(Integer pageSize, Integer pageNum) {
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.offset = pageSize * (pageNum - 1);
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
        this.totalPage = (totalCount == 0 ? 1 : (totalCount % pageSize == 0) ? (totalCount / pageSize) : (totalCount / pageSize + 1));
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Map buildFlexGrid() {
          Map map = new HashMap();
          map.put("page", this.pageNum);
          map.put("total", this.totalCount);
          map.put("rows", this.records);
        return map;
    }
}
