package com.hongbao.dal.mapper;

import java.util.List;

import com.hongbao.dal.model.ClickLog;


/**
 * 
 * @author sairong
 *
 */
public interface ClickLogMapper {
	
	/**
	 * 
	 * @param clickLog
	 * @return
	 */
	 int  insert(ClickLog clickLog);

	 /**
	  * 
	  * @param clickLog
	  * @return
	  */
	 int updateClickLog(ClickLog clickLog);
	 
	 /**
	  * 
	  * @return
	  */
	 List<ClickLog> queryClickLogList();
	 
	 /**
	  * 
	  * @param clickLog
	  * @return
	  */
	 List<ClickLog> queryClickLogByIdfa(ClickLog clickLog);
}
