package com.hongbao.service.user.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import com.hongbao.dal.redis.JedisUtil;
import com.hongbao.utils.HttpRequestUtil;
import com.hongbao.utils.JsonUtils;
import com.tencent.WXPay;
import com.tencent.common.config.WeiXinConfigure;
import com.tencent.protocol.mch_pay_protocol.MchPayReqData;
import com.tencent.protocol.mch_pay_protocol.MchPayResData;

@Service("weixinService")
public class WeixinService {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private JedisUtil accessTokenCacheShike;

	@Value("${weixin.secret}")
	private String weixin_secret;

	@Value("${weixin.appid}")
	private String weixin_appid;
	
	
	@Autowired
	private RestOperations restTemplate;

	@SuppressWarnings("unchecked")
	public String getAccessToken() {
		String access_token = accessTokenCacheShike.getData("accessToken");
		if (!StringUtils.isBlank(access_token)) {
			log.warn("access_token=" + access_token);
			return access_token;
		}
		try {
			String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + weixin_appid
					+ "&secret=" + weixin_secret;
			RestTemplate restTemplate = new RestTemplate();
			String result = restTemplate.getForObject(url, String.class);
			Map<String, String> map = JsonUtils.json2Object(result, HashMap.class);
			access_token = map.get("access_token");
			accessTokenCacheShike.setData("accessToken", access_token);
			log.warn("access_token=" + access_token);
			return access_token;
		} catch (Exception e) {
			log.error(e.getMessage());
			return "";
		}

	}

	public String getOpenId(String code) {
		String openid = "";

		if (!StringUtils.isBlank(code)) {
			try {
				String weixinAuth = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + weixin_appid
						+ "&secret=" + weixin_secret + "&code=" + code + "&grant_type=authorization_code";
				String result = restTemplate.getForObject(weixinAuth, String.class);
				Map<String, String> map = JsonUtils.json2Object(result, HashMap.class);
				openid = map.get("openid");
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}
		}
		return openid;
	}

	public MchPayResData doMchPayBusiness(WeiXinConfigure weiXinConfigure, MchPayReqData mchPayReqData) {
		try {
			return WXPay.doMchPayBusiness(weiXinConfigure, mchPayReqData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			// throw new RuntimeException(e);
			return null;
		}
	}

	public String sendWeiXinMessage(String openId, String message) {
		String text = "{ \"touser\": \"%s\", \"msgtype\": \"text\",  \"text\": {\"content\": \"%s\"}} \"";
		String content = String.format(text, openId, message);
		String accessToken = this.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken;
		String body = HttpRequestUtil.sendPost(url, content);
		return body;
	}

	public static void main(String args[]) {

		new WeixinService().sendWeiXinMessage("oBx9es6KnnXDevPwj898Vg9AL4bY", "你好码");

	}
}
