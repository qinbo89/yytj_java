package com.hongbao.web.controller.tryapp;

import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hongbao.dal.model.User;
import com.hongbao.dal.model.UserApp;
import com.hongbao.service.UserService;
import com.hongbao.service.userapp.UserAppService;
import com.hongbao.utils.AES;
import com.hongbao.utils.Jaes;



@Controller
public class UserAppWebController {

    private static Logger logger = LoggerFactory
            .getLogger(UserAppWebController.class);
    
	@Autowired
	private UserService userService;
	@Autowired
	private UserAppService userAppService;

	/**
	 * getUserAppList
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/innerPage/getUserAppList")
	public String getUserAppList(Model model) {
		User user = userService.getCurrentUserNotUnauthorizedException();
		if (user != null) {
			List<UserApp> list = userAppService.getUserAppListByUserId(user.getId());
			model.addAttribute("userAppList", list);
			// if(list==null||list.size()<=0){ //当列表为空时要求为空
			// return "weixin/empty";
			// }
		}
		return "weixin/userAppTryList";
	}

	@RequestMapping(value = "/weixin/getUserAppNew")
	public String getUserAppNew(Model model) {
		User user = userService.getCurrentUserNotUnauthorizedException();
		if (user != null) {
			UserApp userApp = userAppService.getUserAppNewByUserId(user.getId());
			model.addAttribute("userApp", userApp);
		}
		return "weixin/singleUserApp";
	}

        	private String getHeader(String name, HttpServletRequest request) {
                Enumeration<String> headerNames = request.getHeaderNames();
                while (headerNames != null && headerNames.hasMoreElements()) {
                    String headerName = headerNames.nextElement();
                    if (StringUtils.isNotBlank(headerName) && headerName.equalsIgnoreCase(name)) {
                        return request.getHeader(headerName);
                    }
                }
                return "";
            }
         
         
        /**
         * 
         * @return
         */
        @RequestMapping(value = "/weixin/queryYoumiTaskList")
        public String queryYoumiTaskList(HttpServletRequest request) {
            User user = userService.getCurrentUserNotUnauthorizedException();
            String r="";

            String url = null;
            String ua = getHeader("user-agent", request);
            logger.warn("ua=" + ua);
            if(ua.toUpperCase().contains("IPHONE") || ua.toUpperCase().contains("IPAD")){
            try {
                     r = AES.encode("a39325505e330299", "f9dde45c34ca10a2", user.getOpenId(), "youmi");
                 } catch (Exception e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
                 }
                url = "http://w.ymapp.com/wx/ios/lists.html?r="+r;
            }else{
            	 try {
                     r = AES.encode("c4ddde3f61033ebc", "5e4d0744d0a19168", user.getOpenId(), "youmi");
                 } catch (Exception e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
                 }
                  url = "http://w.ymapp.com/wx/aos/index.html?r="+r;
            }
            logger.warn("url=" + url);
            if(user!=null){
                return "redirect:"+url;
            }
            return null;
        }

	@Value("${weixin.appid}")
	private String weixinAppid;

	@RequestMapping(value = "/weixin/queryQumiTaskList")
	public String queryQumiTaskList() {
		User user = userService.getCurrentUserNotUnauthorizedException();
		String r = "";
		try {
			String openid = user.getOpenId(); // 公众号“获取用户基本信息接口里面”的 openid 字段
												// (注意：不能对微信提供openid擅自做修改，否则统计不到数据)
			String sex = ""; // 公众号“获取用户基本信息接口里面”的 sex 字段
			String nickname = ""; // 公众号“获取用户基本信息接口里面”的 nickname 字段
			String headimgurl = ""; // 公众号“获取用户基本信息接口里面”的 headimgurl 字段
			String appId = "5960816e3ade638e"; // 趣米应用ID
			String appSecretkey = "313e0133717196a4"; // 趣米应用密钥

			String wxAppId = weixinAppid; // 微信AppId(应用Id)
											// 来自开发者中心(注意：该处填入的wxAppId
											// 必须和您在趣米后台绑定的微信appid一致，否则广告不能显示)

			String str = "openid=" + openid + "&app_id=" + appId + "&headimgurl=" + headimgurl + "&sex=" + sex
					+ "&wx=true&nickname=" + nickname + "&wxappid=" + wxAppId; // 构建打开微墙需要的字符串

			String paramUrl = Jaes.encodestr(str, appSecretkey);

			String flushCache = System.currentTimeMillis() + ""; // 经过测试微信会自动缓存，建议您这边设置此参数清空缓存

			r = "http://wx.mob.qumi.com/weixin/weixin/adList?r=" + URLEncoder.encode(paramUrl, "utf-8") + "&appcode="
					+ appId + "&appSecretkey=" + appSecretkey + "&fc=" + flushCache; // 得到微信墙的URL

			// 然后需要您用 response 跳转到wxurl指向的地址
		} catch (Exception e) {
			e.printStackTrace();
		}
		String url = r;
		if (user != null) {
			return "redirect:" + url;
		}
		return null;
	}
	
	/**
	 * Md5(type=1&ui d=5045&appi d=5060&wechatid=o1AFr s3TDxxVLiNbbTw Nqv0uas_8 &appuserid=123321123&key= DFAEADFDSAFSFEADFDSFA)

	 * @return
	 */

	@RequestMapping(value = "/weixin/queryJuzhangTaskList")
	public String queryJunzhangTaskList() {
		User user = userService.getCurrentUserNotUnauthorizedException();
		String url = "";
		try {
			String wechatid = user.getOpenId(); // 公众号“获取用户基本信息接口里面”的 openid 字段
			String uid = "1166"; 
			String appid = "5091"; 
			String appSecretkey = "DFAEADFDSAFSFEADFDSFA"; 
			Long appuserid = user.getId();
			String uri="type=1&uid="+uid+"&appid="+appid+"&wechatid="+wechatid+"&appuserid="+appuserid+"&key="+appSecretkey;
			String str = DigestUtils.md5Hex(uri);
			url= "http://api.9080app.com/WeChat/WeChatOut/AdWallList.aspx?"+uri+"&checksum="+str;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (user != null) {
			return "redirect:" + url;
		}
		return null;
	}
	

	@RequestMapping(value = "/weixin/queryDianruTaskList")
    public String queryDianruTaskList(HttpServletRequest req) {
	    

        User user = userService.getCurrentUserNotUnauthorizedException();
        String url = "";
        try {
            String openid = user.getOpenId(); // 公众号“获取用户基本信息接口里面”的 openid 字段
            String appSecretkey = "0000A52E2100002E";
            url = "http://weixin.mobile.dianru.com/ucenter/index.do?openid=" + openid + "&appkey=" + appSecretkey;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (user != null) {
            return "redirect:" + url;
        }
        return null;
    }
}
