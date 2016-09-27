package com.hongbao.service.appdownload.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hongbao.dal.mapper.AppDownloadMapper;
import com.hongbao.dal.model.AppDownload;
import com.hongbao.service.appdownload.AppDownloadService;

@Service("appDownloadService")
public class AppDownloadServiceImpl implements AppDownloadService {

	@Autowired
	private AppDownloadMapper appDownloadMapper;

	/**
	 * 获取最新一条 且 type =‘1’
	 * 
	 * @return
	 */
	public AppDownload getAppDownload() {
		AppDownload appDownload = appDownloadMapper.findNewDownload();
		return appDownload;
	}

}
