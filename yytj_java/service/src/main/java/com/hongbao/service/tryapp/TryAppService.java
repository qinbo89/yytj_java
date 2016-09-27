package com.hongbao.service.tryapp;

import java.util.List;
import java.util.Map;

import com.hongbao.dal.model.TryApp;
import com.hongbao.dal.page.PageInfo;
import com.hongbao.dal.vo.TryAppVo;

public interface TryAppService {

	/**
	 * 
	 * @param userId
	 * @return
	 */
	List<TryAppVo> queryTryApp(Long userId);

	TryApp queryTryAppById(Long id);

	PageInfo<TryApp> getTryAppList(int page, int rows,
			Map<String, Object> queryParam);

	int insert(TryApp tryApp);

	int updateTryApp(TryApp tryApp);

	TryApp queryMyTryAppById(Long id);

	List<TryAppVo> queryNotTryApp(Long userId);

	List<TryAppVo> queryInvestTryApp(Long userId);

}
