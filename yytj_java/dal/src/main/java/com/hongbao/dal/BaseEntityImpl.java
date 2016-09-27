package com.hongbao.dal;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class BaseEntityImpl implements BaseEntity {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Date createdAt;// 插入逻辑在mysql实现，只查询
	@JsonIgnore
	private Date updatedAt;// 更新逻辑在mysql实现，只查询
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public Date getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
