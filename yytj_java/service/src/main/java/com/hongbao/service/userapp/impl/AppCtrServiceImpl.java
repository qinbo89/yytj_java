package com.hongbao.service.userapp.impl;

import com.hongbao.dal.model.TryApp;
import com.hongbao.dal.page.PageInfo;
import com.hongbao.dal.query.TryAppQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hongbao.dal.mapper.AppCtrMapper;
import com.hongbao.dal.model.AppCtr;
import com.hongbao.service.userapp.AppCtrService;

@Service("appCtrService")
public class AppCtrServiceImpl implements AppCtrService {
	
	@Autowired
	private AppCtrMapper   appCtrMapper ;

	@Override
	public AppCtr getLastAppCtr(Long appId) {
		AppCtr appCtr = appCtrMapper.getLastAppCtr(appId);
		return appCtr;
	}

}
