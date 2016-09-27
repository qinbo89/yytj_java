package com.hongbao.web.controller.relation;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hongbao.dal.base.annotation.NeedLogin;
import com.hongbao.dal.base.annotation.NotNeedLogin;
import com.hongbao.dal.enums.UserType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.hongbao.dal.model.User;
import com.hongbao.dal.redis.JedisUtil;
import com.hongbao.service.UserService;
import com.hongbao.service.user.UserRelationService;
import com.hongbao.service.user.impl.WeixinService;
import com.hongbao.service.user.vo.ApprenticeVO;
import com.hongbao.utils.JsonUtils;
import com.hongbao.utils.LogUtils;



@Controller
public class UserRelationWebController {

	private Logger log = LoggerFactory.getLogger(UserRelationWebController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private UserRelationService userRelationService;

	@Value("${baseSkUrl}")
	private String baseSkUrl = "";

	@Value("${profiles.active}")
	private String prod;

	@Value("${weixin.secret}")
	private String weixin_secret;

	@Value("${weixin.appid}")
	private String weixin_appid;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private JedisUtil qrCodeTicketCache;

	@Autowired
	private JedisUtil qrTicketUserIdCache;
	
	@Autowired
	private WeixinService weixinService;

	@RequestMapping("/innerPage/inviteFriend")
	@NeedLogin(accessUserType=UserType.ALL)
	public String inviteFriend(Model model, HttpServletRequest request) throws UnsupportedEncodingException {
		User user = userService.getCurrentUser();
		String currentRequestURI = baseSkUrl + request.getContextPath() + request.getRequestURI() ;
        String queryString = request.getQueryString()!=null?request.getQueryString():"";
        if(!StringUtils.isBlank(queryString)){
        	currentRequestURI+="?"+queryString;
        }
		String weiXinShareUrl = baseSkUrl + request.getContextPath()+"/outPage/inviteViewFriend?userId="+user.getId();

		log.info(currentRequestURI);
		model.addAttribute("currentRequestURI", currentRequestURI);
		model.addAttribute("inviteFriendTitle", "邀请好友,一起来帮助试玩app，尝新鲜，还赚钱");
		model.addAttribute("appId", weixin_appid);
		String qrcode = "/outPage/qrcode?userId="+user.getId();
		model.addAttribute("qrcode", qrcode);
		model.addAttribute("weiXinShareUrl", weiXinShareUrl);
		return "weixin/inviteFriend";
	}

	

	@RequestMapping("/outPage/qrcode")
	public String qrcode(Model model, HttpServletRequest request, Long userId)
			throws UnsupportedEncodingException {
		String ticket = qrCodeTicketCache.getData(userId+"");
		if (ticket == null) {

			String token =  weixinService.getAccessToken();
			String param = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \""
					+ userId + "\"}}}";
			HashMap result = restTemplate.postForObject(
					"https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + token, param, HashMap.class);
			ticket = (String) result.get("ticket");
			String qrUrl = (String) result.get("url");
			qrCodeTicketCache.setData(userId+"", ticket);
			qrTicketUserIdCache.setData(ticket, userId+"");
		}
		String url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket;
		return "redirect:" + url;
	}

	/**
	 *
	 * @param model
	 * @param request
	 * @param openId
	 * @param userId
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/weixin/myQrcode")
	public String myQrcode(Model model, HttpServletRequest request, String openId, String userId)
			throws UnsupportedEncodingException {
		User user = userService.getCurrentUserNotUnauthorizedException();
		if(user!=null){
		    String currentRequestURI = "/outPage/qrcode?userId="+user.getId();
		    model.addAttribute("currentRequestURI", currentRequestURI);
		}
		model.addAttribute("message", "一起来应用淘金赚钱");
		return "weixin/myQrcode";
	}


	@RequestMapping("/outPage/inviteViewFriend")
	@NotNeedLogin
	public String inviteViewFriend(String code, String openId, Long userId,Model model) throws UnsupportedEncodingException {
		// User user = userService.getCurrentUserNotUnauthorizedException();
		String levelOpenId = getOpenId(code);
		// URLDecoder.decode(currentRequestURI,"UTF-8")
		// model.addAttribute("currentRequestURI",currentRequestURI );
		// model.addAttribute("inviteFriendTitle", "邀请好友");
		if (userId!=null) {
			userRelationService.userRelation(userId, openId, levelOpenId);
			 String qrcode = "/outPage/qrcode?userId="+userId;
					 //getWeiXinShareUrl(openId,userId);
			 model.addAttribute("qrcode", qrcode);
		}
		return "weixin/invitedFriend";
	}

	@RequestMapping("/innerPage/inviteRecord")
	@NeedLogin(accessUserType=UserType.ALL)
	public String getAllRelation(Model model,HttpServletRequest request) {
		User user = userService.getCurrentUser();
		List<ApprenticeVO> inviteFriendList = userRelationService.getAllApprenticeByPuserId(user.getId());
		model.addAttribute("inviteFriendList", inviteFriendList);
		return "weixin/inviteRecord";
	}

	private void modifyUserName(ApprenticeVO vo) {
		// String userName = vo.getUserName();
		// if ((!StringUtils.isBlank(userName)) && userName.length() > 7) {
		// String newName = userName.substring(0, 3) + "*****"
		// + userName.substring(userName.length() - 3);
		// vo.setUserName(newName);
		// }
	}

	@RequestMapping("/innerPage/inviteRecordNew")
	public String inviteRecordNew(Model model,HttpServletRequest request) {
	    
        logger.error(LogUtils.getRequestLog(request));
	    
		User user = userService.getCurrentUserNotUnauthorizedException();
		if (user != null) {
			ApprenticeVO friend = userRelationService.getApprenticeNewByPuserId(user.getId());
			modifyUserName(friend);
			model.addAttribute("friend", friend);
		}else{
            logger.error("null user");
        }
		return "weixin/singleInviteRecord";
	}

	// 取得微信的url
	// https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx36a2a4777d11d9f4
	// &redirect_uri=[/weixin/inviteViewFriend?openid=xxx]&response_type=code&scope=snsapi_base
	// &state=bdx#wechat_redirect
	private String getWeiXinShareUrl(String openId, Long userId) {
		StringBuilder skUrlBuilder = new StringBuilder();
		skUrlBuilder.append(baseSkUrl);
		skUrlBuilder.append("/outPage/inviteViewFriend?openId=");
		skUrlBuilder.append(openId);
		skUrlBuilder.append("&userId=").append(userId);
		// skUrlBuilder.append("&currentRequestURI=").append(MD5Util.MD5Encode(currentRequestURI,"UTF-8"));
		String skUtl = "";
		String snsapi_base = "snsapi_base";
		try {
			skUtl = URLEncoder.encode(skUrlBuilder.toString(), "utf-8");
		} catch (UnsupportedEncodingException e) {

		}
		String weixinUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + weixin_appid + "&"
				+ "redirect_uri=" + skUtl + "&response_type=code&scope=" + snsapi_base + "&state=bdx#wechat_redirect";
		return weixinUrl;
	}

	/**
	 * 
	 * @param model
	 * @param code
	 * @param openId
	 * @param taskContentUrl
	 * @param RETURN_URL
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getOpenId(String code) {
		String openid = "";
		if (!StringUtils.isBlank(code)) {
			try {
				String weixinAuth = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + weixin_appid
						+ "&secret=" + weixin_secret + "&code=" + code + "&grant_type=authorization_code";
				String result = restTemplate.getForObject(weixinAuth, String.class);
				Map<String, String> map = JsonUtils.json2Object(result, HashMap.class);
				openid = map.get("openid");
			} catch (Exception e) {
				log.error(e.getMessage());
				return "";
			}
		}
		return openid;
	}

}
