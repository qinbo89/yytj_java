package com.hongbao.restapi.task;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestOperations;

import com.hongbao.dal.base.controller.ResponseObject;
import com.hongbao.dal.model.ClickLog;
import com.hongbao.dal.model.TryApp;
import com.hongbao.dal.model.User;
import com.hongbao.dal.model.UserApp;
import com.hongbao.error.GlobalErrorCode;
import com.hongbao.service.UserService;
import com.hongbao.service.tryapp.ClickLogService;
import com.hongbao.service.tryapp.TryAppService;
import com.hongbao.service.userapp.UserAppService;
import com.hongbao.utils.JsonUtils;
import com.hongbao.utils.SigUtil;

@Controller
@RequestMapping("/thirdThirdPart")
public class ThirdPartController {

	private Logger log = LoggerFactory.getLogger(ThirdPartController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private UserAppService userAppService;

	@Autowired
	private TryAppService tryAppService;

	@Autowired
	private ClickLogService clickLogService;

	@Autowired
	private RestOperations restTemplate;

	/**
	 * 更新任务状态
	 * 
	 * @param userAppId
	 * @param status
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/youmi")
	public ResponseObject<Map<String, Object>> youmi(HttpServletRequest req) {
		ResponseObject<Map<String, Object>> responseObject = new ResponseObject<Map<String, Object>>();
		String ad = req.getParameter("ad");
		String openid = req.getParameter("user");
		String price = req.getParameter("price");
		String points = req.getParameter("points");
		String dev_server_secret = "1cb2e1b8466b38ca";
		String query = req.getQueryString();
		if (!SigUtil.checkUrlSignature(query, dev_server_secret)) {

			responseObject = new ResponseObject<Map<String, Object>>(
					GlobalErrorCode.NOT_FOUND);
			return responseObject;
		}
		User user = userService.loadUserByOpenId(openid);
		UserApp userApp = new UserApp();
		userApp.setAppName(ad);
		userApp.setAppId(-1l);
		userApp.setAppAppId("-1");
		userApp.setAppImg("qn|yybl|5N8WEV~CXLFPW6~AGFPE87C.jpg");
		userApp.setScore(new Integer(points));
		userApp.setArchive(false);
		userApp.setStatus("2");
		userApp.setSuccessStatus("3");
		userApp.setUserId(user.getId());
		userAppService.updateYoumiTaskSatus(userApp);
		responseObject = new ResponseObject<Map<String, Object>>(
				GlobalErrorCode.SUCESS);
		return responseObject;

	}

	@ResponseBody
	@RequestMapping("/youmiAndroid")
	public ResponseObject<Map<String, Object>> youmiAndroid(
			HttpServletRequest req) {
		ResponseObject<Map<String, Object>> responseObject = new ResponseObject<Map<String, Object>>();
		String ad = req.getParameter("ad");
		String openid = req.getParameter("user");
		String price = req.getParameter("price");
		String points = req.getParameter("points");
		String dev_server_secret = "e3e679d3b916a3be";
		String query = req.getQueryString();
		String order = req.getParameter("order");
		String app = req.getParameter("app");
		String chn = req.getParameter("chn");
		String sig = req.getParameter("sig");

		String siggen = DigestUtils.md5Hex(
				dev_server_secret + "||" + order + "||" + app + "||" + openid
						+ "||" + chn + "||" + ad + "||" + points).substring(12,
				20);
		if (StringUtils.equals(siggen, sig) == false) {
			responseObject = new ResponseObject<Map<String, Object>>(
					GlobalErrorCode.NOT_FOUND);
			log.error("sig error siggen=" + siggen);
			return responseObject;
		}
		log.error("sig success");
		/*
		 * if (!SigUtil.checkUrlSignature(query, dev_server_secret)) {
		 * 
		 * responseObject = new ResponseObject<Map<String,
		 * Object>>(GlobalErrorCode.NOT_FOUND); return responseObject; }
		 */
		User user = userService.loadUserByOpenId(openid);
		UserApp userApp = new UserApp();
		userApp.setAppName(ad);
		userApp.setAppId(-6L);
		userApp.setAppAppId("-1");
		userApp.setAppImg("qn|yybl|5N8WEV~CXLFPW6~AGFPE87C.jpg");
		userApp.setScore(new Integer(points));
		userApp.setArchive(false);
		userApp.setStatus("2");
		userApp.setSuccessStatus("3");
		userApp.setUserId(user.getId());
		userAppService.updateYoumiTaskSatus(userApp);
		responseObject = new ResponseObject<Map<String, Object>>(
				GlobalErrorCode.SUCESS);
		return responseObject;

	}

	/**
	 * 693549defb61f0fc String
	 * sig=md5(point_callback_secret+"||"+order+"||"+app+"||"+user+"||"+device+
	 * "||"+points+"||"+time).substring(8, 24);
	 * 
	 * @param order
	 * @param app
	 * @param ad
	 * @param user
	 * @param device
	 * @param openid
	 * @param points
	 * @param time
	 * @param sig
	 * @param sig2
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/qumi")
	public ResponseObject qumi(String order, String app, String ad,
			String user, String device, String openid, String points,
			String time, String sig, String sig2) {
		log.warn("order=" + order + ",app=" + app + ",ad=" + ad + ",user="
				+ user + ",device=" + device + ",openid=" + openid + ",points="
				+ points + ",time=" + time + ",sig=" + sig + ",sig2=" + sig2);
		String sigToCheck = DigestUtils.md5Hex(
				"693549defb61f0fc" + "||" + order + "||" + app + "||" + user
						+ "||" + device + "||" + points + "||" + time)
				.substring(8, 24);
		if (StringUtils.equals(sigToCheck, sig2) == false) {
			log.error("非法的qumi回调");
			return null;
		}

		User user2 = userService.loadUserByOpenId(openid);
		UserApp userApp = new UserApp();
		userApp.setAppName(ad);
		userApp.setAppId(-1L);
		userApp.setAppAppId("-2");
		userApp.setAppImg("qn|yybl|5N8WEV~CXLFPW6~AGFPE87C.jpg");
		userApp.setScore(new Integer(points));
		userApp.setArchive(false);
		userApp.setStatus("2");
		userApp.setSuccessStatus("3");
		userApp.setUserId(user2.getId());
		userAppService.updateYoumiTaskSatus(userApp);
		log.info("success:" + "order=" + order + ",app=" + app + ",ad=" + ad
				+ ",user=" + user + ",device=" + device + ",openid=" + openid
				+ ",points=" + points + ",time=" + time + ",sig=" + sig
				+ ",sig2=" + sig2);
		ResponseObject responseObject = new ResponseObject<Map<String, Object>>(
				GlobalErrorCode.SUCESS);
		return responseObject;
	}

	/**
	 * http://www.9080app.com/test.aspx?
	 * hashid=7947503245134836773&appid=1000&adid=2035&adname=%e9%85%8b%e9%95%bf
	 * %e8%90%a8%e5%b0% 94 &userid=zshd1
	 * 23456&deviceid=84:29:99:69:18:C1&source=zshd&point=180&time=1409072877&
	 * checksum=184b31e8c203ab579090d8c4760822cd
	 * 
	 * 
	 * ?hashid=7947503245134836773&appid=1000&adid=2035&adname=酋长萨尔
	 * &userid=zshd1 23456&devicei d=84:29:99:69:18:C1&source=zshd&poi nt=18
	 * 0&time =1409072877&appsecret=wewewewe
	 */
	@ResponseBody
	@RequestMapping("/juzhang")
	public Map<String, Object> juzhang(String hashid, String appid,
			String adid, String adname, String userid, String deviceid,
			String source, String point, String time, String checksum) {
		log.warn("hashid=" + hashid + ",appid=" + appid + ",adid=" + adid
				+ ",userid=" + userid + ",deviceid=" + deviceid + ",point="
				+ point + ",time=" + time);
		String appSecretkey = "23uofvsdpkvdi0-9pmdskmf";
		String checksum2 = DigestUtils.md5Hex("?hashid=" + hashid + "&appid="
				+ appid + "&adid=" + adid + "&adname=" + adname + "&userid="
				+ userid + "&deviceid=" + deviceid + "&source=" + source
				+ "&point=" + point + "&time=" + time + "&appsecret="
				+ appSecretkey);
		Map<String, Object> res = new HashMap<String, Object>();
		if (!StringUtils.equals(checksum, checksum2)) {
			log.error("checksum=" + checksum + " checksum2=" + checksum2);
			return res;
		}
		Long LUserId = new Long(userid);
		Float p = new Float(point);
		User user2 = userService.load(LUserId);
		UserApp userApp = new UserApp();
		userApp.setAppName(adname);
		userApp.setAppId(-1L);
		userApp.setAppAppId("-3");
		userApp.setAppImg("qn|yybl|5N8WEV~CXLFPW6~AGFPE87C.jpg");
		userApp.setScore(p.intValue());
		userApp.setArchive(false);
		userApp.setStatus("2");
		userApp.setSuccessStatus("3");
		userApp.setUserId(user2.getId());
		userAppService.updateYoumiTaskSatus(userApp);
		res.put("status", 1);
		res.put("status", "成功回调");
		return res;
	}

	/*
	 * http://appuser.kaifazhe.com/dianru.php? hashid={hashid}& appid={appid}&
	 * adid={adid} & adname={adname}& userid={userid}& deviceid={deviceid}&
	 * source=dianru& point={point} & time={time}& checksum= {checksum} 参数说明：?
	 * appsecret：开发者密钥（WEB端设置，此参数不会被回调，只⽤用做加密使⽤用） hashid：唯⼀一号 appid：开发者应⽤用ID
	 * adid：⼴广告ID adname：⼴广告名称（urlencode操作）
	 * userid：开发者设置的⽤用户ID（SDK中设置，可能会有urlencode操作） deviceid：唯⼀一标识(MAC)
	 * source：渠道来源 point：积分值(⽤用户可以赚取的积分) time：当前产⽣生的时间戳 checksum：签名结果值
	 * http://api.yahaomai.com/thirdThirdPart/dianru
	 * http://api.yahaomai.com/weixin/queryDianruTaskList
	 */
	@ResponseBody
	@RequestMapping("/dianru")
	public String dianru(HttpServletRequest req, String hashid, String appid,
			String adid, String adname, String userid, String deviceid,
			String source, String time, String point, String checksum) {

		String appsecret = "0000A52E2100002E";
		String parames = "hashid=" + hashid + "&appid=" + appid + "&adid="
				+ adid + "&adname=" + adname + "&userid=" + userid
				+ "&deviceid=" + deviceid + "&source=dianru&point=" + point
				+ "&time=" + time + "&appsecret=" + appsecret + "";
		String checksumGen = DigestUtils.md5Hex(parames);
		String checksumGen2 = DigestUtils.md5Hex("?" + parames);

		log.error("sig  checksum=" + checksum + ",checksumGen=" + checksumGen
				+ ",checksumGen2=" + checksumGen2);
		if (StringUtils.equals(checksum, checksumGen) == false
				&& StringUtils.equals(checksum, checksumGen2) == false) {
			log.error("sig error");
			return "false";
		}
		log.error("sig success");
		User user = userService.loadUserByOpenId(userid);
		UserApp userApp = new UserApp();
		userApp.setAppName(adname);
		userApp.setAppId(-1L);
		userApp.setAppAppId("-4");
		userApp.setAppImg("qn|yybl|5N8WEV~CXLFPW6~AGFPE87C.jpg");
		userApp.setScore(new Double(Math.floor(new Double(NumberUtils
				.toDouble(point)))).intValue());
		userApp.setArchive(false);
		userApp.setStatus("2");
		userApp.setSuccessStatus("3");
		userApp.setUserId(user.getId());
		userAppService.updateYoumiTaskSatus(userApp);
		return "true";

	}

	/**
	 * http://www.test.com/click?app=[广告主应用ID]&udid=[用户mac]&idfa=[idfa]&openudid
	 * =[openudid]&os=[设备版本号]
	 * &callbackurl=http%3A%2F%2Fios.wapx.cn%2Fios%2Freceiver%2Factivate%3Fapp%.
	 * 
	 * http://www.yahaomai.com/thirdThirdPart/wpClick?app=984424855&udid=[用户mac]
	 * &idfa=[idfa]&openudid=[openudid]&os=[设备版本号] /thirdThirdPart/wpClick
	 */
	@ResponseBody
	@RequestMapping("/wpClick")
	public Map<String, Object> wpClick(HttpServletRequest req, String app,
			String udid, String idfa, String openudid, String os,
			String callbackurl) {
		Long LappId = new Long(app);
		Map<String, Object> result = new HashMap<String, Object>();
		TryApp tryApp = tryAppService.queryMyTryAppById(LappId);
		UserApp userApp = new UserApp();
		userApp.setAppName(tryApp.getAppName());
		userApp.setAppId(tryApp.getId());
		userApp.setAppAppId(tryApp.getAppAppId());
		userApp.setAppImg(tryApp.getAppImg());
		userApp.setScore(0);
		userApp.setArchive(false);
		userApp.setStatus("0");
		userApp.setSuccessStatus(tryApp.getSuccessStatus());
		userApp.setUserId(-1L);
		userApp.setUuid(idfa);
		userApp.setChannel("wp");
		String pageUid = UUID.randomUUID().toString();
		userApp.setPageUid(pageUid);
		ClickLog clickLog = new ClickLog();
		clickLog.setAppid(LappId);
		clickLog.setCallbackurl(callbackurl);
		clickLog.setChannel("wp");
		clickLog.setIdfa(idfa);
		clickLog.setOpenudid(openudid);
		clickLog.setOs(os);
		clickLog.setStatus("0");
		clickLog.setUdid(udid);
		clickLogService.insert(clickLog);
		userAppService.insert(userApp);
		String callBackUrl = new StringBuilder(tryApp.getCallBackUrl())
				.append("?flag=").append(pageUid).append("&status=")
				.append("&app=").append(tryApp.getAppAppId()).append("&idfa=")
				.append(idfa).toString();
		try {
			callBackUrl = URLEncoder.encode(callBackUrl, "UTF-8");
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		String clickUrl = tryApp.getClickCallUrl().replaceAll("\\{idfa\\}",
				idfa);
		clickUrl = clickUrl.replaceAll("\\{callbackurl\\}", callBackUrl);
		log.info(clickUrl);
		try {
			String re = restTemplate.getForObject(clickUrl, String.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		result.put("message", "激活成功");
		result.put("success", true);
		return result;

	}

	/**
	 * 
	 * http://api.yahaomai.com/thirdThirdPart/myClick?app=17&idfa=xxx&
	 * callbackurl=xxx
	 * http://www.test.com/click?app=[广告主应用ID]&udid=[用户mac]&idfa=[idfa]&openudid
	 * =[openudid]&os=[设备版本号]
	 * &callbackurl=http%3A%2F%2Fios.wapx.cn%2Fios%2Freceiver%2Factivate%3Fapp%.
	 * 
	 * http://www.yahaomai.com/thirdThirdPart/wpClick?app=984424855&udid=[用户mac]
	 * &idfa=[idfa]&openudid=[openudid]&os=[设备版本号] /thirdThirdPart/wpClick
	 */
	@ResponseBody
	@RequestMapping("/myClick")
	public Map<String, Object> myClick(HttpServletRequest req, String app,
			String idfa, String channel, String callbackurl) {
		Map<String, Object> result = new HashMap<String, Object>();
		Long LappId = new Long(app);
		String pageUid = UUID.randomUUID().toString();
		TryApp tryApp = tryAppService.queryMyTryAppById(LappId);
		UserApp userApp = new UserApp();
		userApp.setAppName(tryApp.getAppName());
		userApp.setAppId(tryApp.getId());
		userApp.setAppAppId(tryApp.getAppAppId());
		userApp.setAppImg(tryApp.getAppImg());
		userApp.setScore(0);
		userApp.setArchive(false);
		userApp.setStatus("0");
		userApp.setSuccessStatus(tryApp.getSuccessStatus());
		userApp.setUserId(-1L);
		userApp.setUuid(idfa);
		userApp.setPageUid(pageUid);
		userApp.setChannel(channel);
		ClickLog clickLog = new ClickLog();
		clickLog.setAppid(LappId);
		clickLog.setCallbackurl(callbackurl);
		clickLog.setChannel(channel);
		clickLog.setIdfa(idfa);
		clickLog.setOpenudid(idfa);
		clickLog.setOs("");
		clickLog.setStatus("0");
		clickLog.setUdid(idfa);
		UserApp queryUserApp = new UserApp();
		queryUserApp.setAppAppId(tryApp.getAppAppId());
		queryUserApp.setUuid(idfa);
		List<UserApp> list = userAppService
				.queryUserAppListByIdfa(queryUserApp);
		if (list != null && !list.isEmpty()) {
			for (UserApp myUserApp : list) {
				if (StringUtils.equals(myUserApp.getStatus(),
						myUserApp.getSuccessStatus())) {
					result.put("message", "重复调用");
					result.put("success", true);
					return result;
				}
			}
		}

		clickLogService.insert(clickLog);
		userAppService.insert(userApp);

		String callBackUrl = new StringBuilder(tryApp.getCallBackUrl())
				.append("?flag=").append(pageUid).append("&status=")
				.append("&app=").append(tryApp.getAppAppId())
				.append("&source=").toString();
		try {
			callBackUrl = URLEncoder.encode(callBackUrl, "UTF-8");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		String clickUrl = tryApp.getClickCallUrl().replaceAll("\\{idfa\\}",
				idfa);
		clickUrl = clickUrl.replaceAll("\\{callbackurl\\}", callBackUrl);
		log.info(clickUrl);
		try {
			String re = restTemplate.getForObject(clickUrl, String.class);
			Map<String, Object> map = JsonUtils.json2Object(re, HashMap.class);
			Boolean success = (Boolean) map.get("success");
			if (!success) {
				result.put("message", "重复调用");
				result.put("success", true);
			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		result.put("message", "激活成功");
		result.put("success", true);
		return result;

	}

}
