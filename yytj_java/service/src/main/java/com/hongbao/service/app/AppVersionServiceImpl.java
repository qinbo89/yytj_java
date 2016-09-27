package com.hongbao.service.app;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hongbao.dal.mapper.AppVersionMapper;
import com.hongbao.dal.model.AppVersion;
import com.hongbao.service.impl.BaseServiceImpl;
import com.hongbao.vo.PageVO;



@Service("appVersionService")
public class AppVersionServiceImpl extends BaseServiceImpl implements
		AppVersionService {
	private static Logger logger = LoggerFactory
			.getLogger(AppVersionServiceImpl.class);

	@Autowired
	private AppVersionMapper appVersionMapper;

	@Override
	public AppVersion findCurrentVersion(String osType, String appType) {
		return appVersionMapper.findCurrentVersion(osType, appType);
	}

	@Override
	public int insert(AppVersion appVersion) {
		try {
			return appVersionMapper.addAppVersion(appVersion);
		} catch (Exception e) {
			logger.error("新增应用版本信息出错" + e.getMessage());
			return 0;
		}
	}

	@Override
	public AppVersion load(Long id) {
		return appVersionMapper.getById(id);
	}

	@Override
	public PageVO<AppVersion> getAppVersionsByParam(int page, int rows) {
		PageVO<AppVersion> pageVO = new PageVO<AppVersion>();
		pageVO.setPage(page);
		Integer count = appVersionMapper.getAppVersionListCount();
		if (count == null || count == 0) {
			pageVO.setTotal(0);
			pageVO.setRows(new ArrayList<AppVersion>());
		}
		pageVO.setTotal(count);
		page = (page <= 1) ? 1 : page;
		Pageable pageable = new PageRequest(page - 1, rows);

		List<AppVersion> feedbacks = appVersionMapper
				.getAppVersionList(pageable);
		pageVO.setRows(feedbacks);
		return pageVO;
	}

	@Override
	public int updateAppVersion(AppVersion appVersion) {
		try {
			return appVersionMapper.updateAppVersion(appVersion);
		} catch (Exception e) {
			logger.error("修改应用版本信息出错" + e.getMessage());
			return 0;
		}
	}


}