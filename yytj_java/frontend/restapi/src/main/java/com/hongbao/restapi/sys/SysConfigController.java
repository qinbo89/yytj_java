package com.hongbao.restapi.sys;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hongbao.dal.base.controller.ResponseObject;


@Controller
public class SysConfigController {

	@RequestMapping(value = "/sys/config")
	@ResponseBody
	public ResponseObject<Map<String, Object>> queryConfigList() {
		ResponseObject<Map<String, Object>> responseObject = new ResponseObject<Map<String, Object>>();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isInitAppList", "1");
		responseObject.setData(result);
		return responseObject;
	}
}
