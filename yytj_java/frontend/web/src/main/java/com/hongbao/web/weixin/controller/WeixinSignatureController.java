package com.hongbao.web.weixin.controller;

import java.security.MessageDigest;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.hongbao.dal.base.annotation.NotNeedLogin;
import com.hongbao.dal.base.controller.ResponseObject;
import com.hongbao.dal.redis.JedisUtil;
import com.hongbao.service.user.impl.WeixinService;
import com.hongbao.utils.JsonUtils;



@Controller
public class WeixinSignatureController {

	public static Logger logger = LoggerFactory
			.getLogger(WeixinSignatureController.class);

	@Value("${weixin.appid}")
	private String weixin_appid;

	@Value("${weixin.secret}")
	private String weixin_secret;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private JedisUtil accessTokenCacheShike;

	@Autowired
	private JedisUtil ticketCache;
	
	@Autowired
	private WeixinService weixinService ;

	@ResponseBody
	@RequestMapping("/wx/getWebSignature")
	@NotNeedLogin
	public ResponseObject<Map<String, String>> getWebSignature(String url,
			String timestamp, String noncestr) {
		String accessToken = "";
		String ticket = "";
		accessToken = accessTokenCacheShike.getData("accessToken");
		if (StringUtils.isBlank(accessToken)) {
			accessToken = weixinService.getAccessToken();
			accessTokenCacheShike.setData("accessToken", accessToken);
		}
		ticket = ticketCache.getData("ticket");
		if (StringUtils.isBlank(ticket)) {
			ticket = generateTicket(accessToken);
			ticketCache.setData("ticket", ticket);
		}
		String toSign = "jsapi_ticket=" + ticket + "&noncestr=" + noncestr
				+ "&timestamp=" + timestamp + "&url=" + url;
		MessageDigest crypt = null;
		try {
			crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(toSign.getBytes("UTF-8"));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		String signature = byteToHex(crypt.digest());
		Map<String, String> map = new HashMap<String, String>();
		map.put("Signature", signature);
		ResponseObject<Map<String, String>> responseObject = new ResponseObject<Map<String, String>>();
		responseObject.setData(map);
		return responseObject;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	private String generateTicket(String accessToken) {
		String access_token = "";
		try {
			String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="
					+ accessToken + "&type=jsapi";
			String result = restTemplate.getForObject(url, String.class);
			Map<String, String> map = JsonUtils.json2Object(result,
					HashMap.class);
			String ticket = map.get("ticket");
			return ticket;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "";
		}

	}
	
	


}
