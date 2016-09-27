package com.hongbao.restapi.signin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SigninController {

	@ResponseBody
	@RequestMapping(value = "signin")
	public Map<String, String> signin() {
		Map<String, String> obj = new HashMap<String, String>();
		obj.put("status", "1");
		return obj;
	}
}
