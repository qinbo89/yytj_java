package com.hongbao.dal.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hongbao.dal.model.TryApp;
import com.hongbao.dal.page.PageInfo;

/**
 * @author bes
 * 
 */
public interface TryAppMapper {

	List<TryApp> queryTryAppList(@Param("userId") Long userId,
			@Param("appType") int appType);

	TryApp queryTryAppById(@Param("id") Long id);

	Integer getTryAppListCount(@Param("map") Map<String, Object> queryParam);

	List<TryApp> getTryAppList(@Param("page") PageInfo pageInfo,
			@Param("map") Map<String, Object> queryParam);

	int insert(TryApp tryApp);

	int updateTryApp(TryApp tryApp);

	/**
	 * 在线的app
	 * 
	 * @return
	 */
	List<TryApp> queryOnlineTryAppList();

	/**
	 * 
	 * @param id
	 * @return
	 */
	TryApp queryMyTryAppById(@Param("id") Long id);

	/**
	 * 
	 * @param userId
	 * @return
	 */
	List<TryApp> queryNotStartTryAppList(@Param("userId") Long userId);
}
