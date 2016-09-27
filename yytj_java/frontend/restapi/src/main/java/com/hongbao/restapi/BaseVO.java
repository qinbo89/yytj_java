package com.hongbao.restapi;

import com.hongbao.dal.BaseEntity;

@SuppressWarnings("serial")
public abstract class BaseVO implements BaseEntity {

	private Long id;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
