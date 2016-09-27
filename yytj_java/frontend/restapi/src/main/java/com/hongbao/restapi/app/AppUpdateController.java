package com.hongbao.restapi.app;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hongbao.dal.base.annotation.NotNeedLogin;
import com.hongbao.dal.base.controller.ResponseObject;
import com.hongbao.dal.model.AppVersion;
import com.hongbao.service.app.AppVersionService;

@Controller
public class AppUpdateController {

	@Autowired
	private AppVersionService appVersionService;

	/**
	 * @return 若需要升级，则返回最新版本信息
	 */
	@ResponseBody
	@RequestMapping("/update/check")
	@NotNeedLogin
	public ResponseObject<AppVersion> checkVersion(
			@ModelAttribute AppversionVO appversionVO) {
		// 为了和老版本兼容，默认为有礼
		if (StringUtils.isBlank(appversionVO.getAppType())) {
			appversionVO.setAppType("yytj");
		}
		AppVersion curVer = appVersionService.findCurrentVersion(
				appversionVO.getOsType(), appversionVO.getAppType());
		return new ResponseObject<AppVersion>(
				curVer != null
						&& curVer.getVersion() > appversionVO
								.getClientVersion() ? curVer : null);
	}

	@ResponseBody
	@RequestMapping("/checkConfig")
	@NotNeedLogin
	public ResponseObject<Map<String, Object>> checkConfig() {
		Map<String, Object> map = new HashMap<String, Object>();
		ResponseObject<Map<String, Object>> responseObject = new ResponseObject<Map<String, Object>>();
		responseObject.setData(map);
		return responseObject;
	}
}
