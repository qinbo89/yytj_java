package com.hongbao.dal.mapper;

import com.hongbao.dal.model.AppCtr;

public interface AppCtrMapper {
	
	/**
	 * 找到最近的一次数量控制
	 * @param appId
	 * @return
	 */
	public AppCtr getLastAppCtr(Long appId);

	
	/**
	 * 插入
	 * @param appId
	 * @return
	 */
	public AppCtr insert(Long appId);
}
