package com.hongbao.service.tryapp.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hongbao.dal.mapper.ClickLogMapper;
import com.hongbao.dal.model.ClickLog;
import com.hongbao.service.tryapp.ClickLogService;



@Service("clickLogService")
public class ClickLogServiceImpl implements ClickLogService {

	@Autowired
	private ClickLogMapper clickLogMapper;

	@Override
	public void insert(ClickLog clickLog) {
		clickLogMapper.insert(clickLog);
	}

	@Override
	public List<ClickLog> queryClickLogByIdfa(ClickLog clickLog) {
		List<ClickLog> list = clickLogMapper.queryClickLogByIdfa(clickLog);
		return list;
	}

	@Override
	public int updateClickLog(ClickLog clickLog) {
		int row = clickLogMapper.updateClickLog(clickLog);
		return row;
	}

}
