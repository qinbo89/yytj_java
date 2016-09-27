package com.hongbao.service.tryapp;

import java.util.List;

import com.hongbao.dal.model.ClickLog;


public interface ClickLogService {
	

	/**
	 * 插入点击记录
	 * @param clickLog
	 */
	void insert(ClickLog clickLog);
	
	/**
	 * 
	 * @param clickLog
	 * @return
	 */
	List<ClickLog> queryClickLogByIdfa(ClickLog clickLog);
	
	/**
	 * 
	 * @param clickLog
	 * @return
	 */
	int updateClickLog(ClickLog clickLog);

}
