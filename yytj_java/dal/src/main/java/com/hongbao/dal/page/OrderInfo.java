package com.hongbao.dal.page;

import java.io.Serializable;


public class OrderInfo implements Serializable{

    private static final long serialVersionUID = 1L;
    private String order;
    private String sort;
    
    public OrderInfo(String order, String sort) {
        super();
        this.order = order;
        this.sort = sort;
    }
    public String getOrder() {
        return order;
    }
    public void setOrder(String order) {
        this.order = order;
    }
    public String getSort() {
        return sort;
    }
    public void setSort(String sort) {
        this.sort = sort;
    }
    
}
