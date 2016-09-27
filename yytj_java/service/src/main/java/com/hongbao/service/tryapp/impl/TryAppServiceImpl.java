package com.hongbao.service.tryapp.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hongbao.dal.mapper.TryAppMapper;
import com.hongbao.dal.mapper.UserAppMapper;
import com.hongbao.dal.model.TryApp;
import com.hongbao.dal.model.UserApp;
import com.hongbao.dal.page.PageInfo;
import com.hongbao.dal.vo.TryAppVo;
import com.hongbao.service.tryapp.TryAppService;

@Service("tryAppService")
public class TryAppServiceImpl implements TryAppService {

	@Autowired
	private TryAppMapper tryAppMapper;

	@Autowired
	private UserAppMapper userAppMapper;

	@Override
	public List<TryAppVo> queryTryApp(Long userId) {
		List<TryApp> list = tryAppMapper.queryOnlineTryAppList();
		List<UserApp> useAppList = userAppMapper.getUserAppListByUserId(userId);
		Set<Long> idSet = new HashSet<Long>();
		for (UserApp userApp : useAppList) {
			idSet.add(userApp.getAppId());
		}

		List<TryAppVo> myList = new ArrayList<TryAppVo>();
		for (TryApp app : list) {
			TryAppVo tryAppVo = new TryAppVo();
			BeanUtils.copyProperties(app, tryAppVo);
			if (idSet.contains(app.getId())) {
				tryAppVo.setTried(1);
			}
			myList.add(tryAppVo);
		}
		return myList;
	}

	@Override
	public List<TryAppVo> queryInvestTryApp(Long userId) {
		List<TryApp> list = tryAppMapper.queryTryAppList(userId, 1);
		List<TryAppVo> myList = new ArrayList<TryAppVo>();
		for (TryApp app : list) {
			TryAppVo tryAppVo = new TryAppVo();
			BeanUtils.copyProperties(app, tryAppVo);
			myList.add(tryAppVo);
		}
		return myList;
	}

	@Override
	public List<TryAppVo> queryNotTryApp(Long userId) {
		List<TryApp> list = tryAppMapper.queryNotStartTryAppList(userId);
		List<TryAppVo> myList = new ArrayList<TryAppVo>();
		for (TryApp app : list) {
			TryAppVo tryAppVo = new TryAppVo();
			BeanUtils.copyProperties(app, tryAppVo);
			myList.add(tryAppVo);
		}
		return myList;
	}

	@Override
	public TryApp queryTryAppById(Long id) {

		return tryAppMapper.queryTryAppById(id);
	}

	@Override
	public PageInfo<TryApp> getTryAppList(int pageNum, int rows,
			Map<String, Object> queryParam) {
		PageInfo<TryApp> page = new PageInfo<TryApp>(rows, pageNum);
		Integer count = tryAppMapper.getTryAppListCount(queryParam);
		page.setTotalCount(count);
		List<TryApp> list = tryAppMapper.getTryAppList(page, queryParam);
		page.setRecords(list);
		return page;
	}

	@Override
	public int insert(TryApp tryApp) {
		return tryAppMapper.insert(tryApp);
	}

	@Override
	public int updateTryApp(TryApp tryApp) {
		return tryAppMapper.updateTryApp(tryApp);
	}

	@Override
	public TryApp queryMyTryAppById(Long id) {
		return tryAppMapper.queryMyTryAppById(id);
	}
}
