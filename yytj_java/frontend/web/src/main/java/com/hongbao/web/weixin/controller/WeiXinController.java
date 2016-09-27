package com.hongbao.web.weixin.controller;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.hongbao.dal.base.annotation.NotNeedLogin;
import com.hongbao.dal.model.User;
import com.hongbao.dal.model.UserBind;
import com.hongbao.dal.model.UserLocus;
import com.hongbao.dal.model.UserRelation;
import com.hongbao.dal.redis.JedisUtil;
import com.hongbao.service.UserService;
import com.hongbao.service.user.UserBindService;
import com.hongbao.service.user.UserLocusService;
import com.hongbao.service.user.UserRelationService;
import com.hongbao.service.user.impl.WeixinService;
import com.hongbao.service.userapp.UserAppService;
import com.hongbao.utils.JsonUtils;
import com.hongbao.utils.WeixinSignUtil;

/**
 * 
 */
@Controller
public class WeiXinController {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Value("${weixin.secret}")
	private String weixin_secret;

	@Value("${weixin.appid}")
	private String weixin_appid;

	@Value("${site.web.host.name}")
	private String host;
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private UserBindService userBindService;

	@Autowired
	private UserLocusService userLocusService;

	@Autowired
	private WeixinService weixinService;

	@Autowired
	private JedisUtil qrTicketUserIdCache;

	@Autowired
	private UserRelationService userRelationService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserAppService userAppService;

	/**
	 * 微信关注-信息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/weiXinAttention", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	@NotNeedLogin
	public String dispose(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 判断是否需要验证
		boolean isCheck = false;
		/* 消息的接收、处理、响应 */
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String signature = request.getParameter("signature");
		String echostr = request.getParameter("echostr");

		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		if (isCheck) {
			if (WeixinSignUtil.checkSignature(signature, timestamp, nonce)) {
				return (echostr == null) ? "" : echostr;
			} else {
				log.info("不是微信服务器发来的请求,请小心!");
				return "";
			}
		}
		// 获取接收来的信息
		Map<String, String> requestMap = parseXml(request);
		log.info(requestMap.get("Event"));
		if (StringUtils.equals("subscribe", requestMap.get("Event"))) { // 关注
			// 用户绑定登录
			String openId = requestMap.get("FromUserName");
			User queryUser = userService.loadUserByOpenId(openId);

			User user = login(request, response, requestMap.get("FromUserName"));
			log.info("openId=" + openId);
			buildRelation(requestMap, user, openId);
			String dev_user_id = "gh_17afdc2832ba";
			String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ weixin_appid+ "&redirect_uri=http%3a%2f%2fwww.yingyongtaojin.com%2fweixin%2fportal&response_type=code&scope=snsapi_base&state=bdx#wechat_redirect";
			String msg = String.format(getMessageXML(), openId, dev_user_id,System.currentTimeMillis(), "news", 1,"应用淘金！“首次关注立即拿5元红包”", "hi,点击这里赚钱吧！", host+ "/_resources/images/welcome.jpg", url);

			if (queryUser == null) {
				userAppService.addUserSocore(user.getOpenId(), user.getId(),
						500, "首次关注立即拿5元红包");
			}

			return msg;
		}

		return "";
	}

	private void buildRelation(Map<String, String> requestMap, User user,
			String openId) {
		String ticket = requestMap.get("Ticket");
		log.info("ticket=" + ticket);
		if (ticket != null) {
			Long parentuserId = new Long(qrTicketUserIdCache.getData(ticket));
			log.info("parentuserId=" + parentuserId);
			if (parentuserId != null) {
				User pUser = userService.load(parentuserId);
				UserRelation userRelation = new UserRelation();
				userRelation.setParentUserId(parentuserId);
				userRelation.setUserId(user.getId());
				userRelation.setOpenId(openId);
				userRelation.setParentOpenId(pUser.getOpenId());
				userRelationService.insert(userRelation);
			}
		}
	}

	private User login(HttpServletRequest request,
			HttpServletResponse response, String openId) {
		UserBind userBind = new UserBind();
		// userBind.setUuid(uuid);
		userBind.setSource("weixin");
		userBind.setOpenId(openId);
		if (StringUtils.isBlank(openId)) {
			return null;
		}
		User userInfo = getWeiXinUserInfo(openId);
		if (userInfo == null) { // 如果没有回去到用户信息直接返回null
			return null;
		}
		userBind.setUnionId(userInfo.getUnionid());
		userInfo.setOpenId(openId);
		User user = userBindService.bindOpenUserForLogin(userBind, userInfo);
		// userService.setCurrentUser(user);

		UserLocus userLocus = new UserLocus();
		userLocus.setUserId(user.getId());
		// userLocus.setDeviceId(uuid);
		userLocus
				.setDeviceId((StringUtils.isBlank(userInfo.getUnionid())) ? openId
						: userInfo.getUnionid()); // 首选Unionid
													// 或openid
		userLocus.setLoginTime(new Date());
		userLocus.setDeviceType("weixin_gz"); // 用于关注记录
		userLocusService.addUserLocus(userLocus);
		return user;

	}

	/**
	 * 获取用户基本信息
	 * 
	 * @param openid
	 * @return
	 */
	@RequestMapping("/getWeiXinUserInfo")
	public User getWeiXinUserInfo(String openid) {
		String accessToken = "";
		accessToken = weixinService.getAccessToken();
		Map<String, Object> userMap = getWeixinUserInfoUnionID(openid,
				accessToken);
		// if(userMap.get("errcode")==null||(Integer)userMap.get("errcode")-1!=0){
		// return null;
		// }

		User user = new User();
		user.setAdmin(false);
		// subscribe 如果是1 说明关注了
		// Integer subscribe = (Integer)userMap.get("subscribe");

		user.setNickname((String) userMap.get("nickname"));

		String sex;
		try {
			sex = (String) userMap.get("sex");
		} catch (Exception e) {
			sex = "1";
		}
		user.setSex(sex);

		user.setPicUri((String) userMap.get("headimgurl"));
		user.setCity((String) userMap.get("country") + "_"
				+ (String) userMap.get("province") + "_"
				+ (String) userMap.get("city"));
		user.setUnionid((String) userMap.get("unionid"));
		return user;
	}

	/**
	 * 获取用户信息
	 * 
	 * @param model
	 * @param code
	 * @param openId
	 * @param taskContentUrl
	 * @param RETURN_URL
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getWeixinUserInfo(String openid,
			String access_token) {
		Map<String, Object> userMap = new HashMap<String, Object>();
		try {
			String weixinAuth = "https://api.weixin.qq.com/sns/userinfo?access_token='"
					+ access_token + "'&openid='" + openid + "'&lang=zh_CN";
			String result = restTemplate.getForObject(weixinAuth, String.class);
			result = new String(result.getBytes("ISO-8859-1"), "utf-8");
			userMap = JsonUtils.json2Object(result, HashMap.class);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new HashMap<String, Object>();
		}
		return userMap;
	}

	/**
	 * 获取用户信息和UnionID { "subscribe": 1, "openid":
	 * "o6_bmjrPTlm6_2sgVt7hMZOPfL2M", "nickname": "Band", "sex": 1, "language":
	 * "zh_CN", "city": "广州", "province": "广东", "country": "中国", "headimgurl":
	 * "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0"
	 * , "subscribe_time": 1382694957, "unionid":
	 * " o6_bmasdasdsad6_2sgVt7hMZOPfL" }
	 * 
	 * @param model
	 * @param code
	 * @param openId
	 * @param taskContentUrl
	 * @param RETURN_URL
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getWeixinUserInfoUnionID(String openid,
			String access_token) {
		Map<String, Object> userMap = new HashMap<String, Object>();
		try {
			// https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
			String weixinAuth = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="
					+ access_token + "&openid=" + openid + "&lang=zh_CN";
			String result = restTemplate.getForObject(weixinAuth, String.class);
			log.error("result=" + result);
			result = new String(result.getBytes("ISO-8859-1"), "utf-8");
			userMap = JsonUtils.json2Object(result, HashMap.class);
		} catch (Exception e) {
			log.error(e.getMessage());
			return userMap;
		}
		return userMap;
	}

	/**
	 * 解析微信发来的请求（XML）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private static Map<String, String> parseXml(HttpServletRequest request)
			throws Exception {
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();

		// 从request中取得输入流
		InputStream inputStream = request.getInputStream();
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();

		// 遍历所有子节点
		for (Element e : elementList)
			map.put(e.getName(), e.getText());

		// 释放资源
		inputStream.close();
		inputStream = null;
		return map;
	}

	private String getMessageXML() {
		String msg = "<xml><ToUserName><![CDATA[%s]]></ToUserName>"
				+ "<FromUserName><![CDATA[%s]]></FromUserName>"
				+ "<CreateTime>%s</CreateTime>"
				+ "<MsgType><![CDATA[%s]]></MsgType>"
				+ "<ArticleCount>%s</ArticleCount>" + "<Articles>" + "<item>"
				+ "<Title><![CDATA[%s]]></Title>"
				+ "<Description><![CDATA[%s]]></Description>"
				+ "<PicUrl><![CDATA[%s]]></PicUrl>"
				+ "<Url><![CDATA[%s]]></Url>" + "</item>" + "</xml>";
		return msg;
	}

}
