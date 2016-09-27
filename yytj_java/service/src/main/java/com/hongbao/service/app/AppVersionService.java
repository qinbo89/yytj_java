package com.hongbao.service.app;

import com.hongbao.dal.model.AppVersion;
import com.hongbao.vo.PageVO;

public interface AppVersionService  {

	/**
	 * 获取app当前版本号
	 * 
	 * @param userVersion
	 */
	AppVersion findCurrentVersion(String osType, String appType);

	PageVO<AppVersion> getAppVersionsByParam(int page, int rows);

	int updateAppVersion(AppVersion appVersion);
	
	int insert(AppVersion appVersion);

}
