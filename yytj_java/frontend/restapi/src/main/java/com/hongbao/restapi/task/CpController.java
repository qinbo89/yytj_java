package com.hongbao.restapi.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hongbao.dal.model.User;
import com.hongbao.dal.model.UserApp;
import com.hongbao.service.UserService;
import com.hongbao.service.userapp.UserAppService;



@Controller
@RequestMapping("/cp")
public class CpController {

	private Logger log = LoggerFactory.getLogger(CpController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private UserAppService userAppService;


	@ResponseBody
	@RequestMapping("/notify")
	public Map<String, Object> notifyReg(String flag, String appid, String app, String status, String idfa,
			String checksum) {
		Map<String, Object> result = new HashMap<String, Object>();
		UserApp updateUserApp = null;
		if (StringUtils.isBlank(flag)) {
			flag = "####";
		}

		List<UserApp> list = userAppService.getUserAppListByPageUid(flag);
		if (list.size() == 0) {

			UserApp queryUserApp = new UserApp();
			if (StringUtils.isBlank(appid)) {
				appid = app;
			}
			queryUserApp.setAppAppId(appid);
			queryUserApp.setUuid(idfa);
			List<UserApp> userAppList = userAppService.queryUserAppListByIdfa(queryUserApp);
			if (userAppList == null || userAppList.isEmpty()) {
				result.put("message", "参数错误,找不到注册任务");
				result.put("success", false);
				return result;
			}
			for (UserApp userApp : userAppList) {// 寻找同一个手机上是否这个app已经试玩
				if (StringUtils.equals(userApp.getStatus(), userApp.getSuccessStatus())) {
					result.put("message", "该手机用此app已经试玩");
					result.put("success", false);
					return result;
				}
			}
			updateUserApp = userAppList.get(0);

		} else {

			updateUserApp = list.get(0);
		}

		if (!StringUtils.isBlank(updateUserApp.getChannel())) {// 渠道消耗走这个分支
			if (!StringUtils.equals(updateUserApp.getChannel(), "wp")) {
				userAppService.updateWpUserAppStatus(updateUserApp);
				result.put("success", true);
				return result;
			}
			if (!StringUtils.equals(updateUserApp.getChannel(), "sb")) {
				userAppService.updateWpUserAppStatus(updateUserApp);
				result.put("success", true);
				return result;
			}

		}

		List<User> idfaList = userService.findByUuidList(idfa);
		if (idfaList == null || idfaList.isEmpty()) {
			result.put("message", "参数错误,找不到注册任务");
			result.put("success", false);
			return result;
		}

		int updateResult = userAppService.updateCpUserAppStatus(updateUserApp, updateUserApp.getSuccessStatus(),
				updateUserApp.getUserId());
		if (updateResult == 0) {
			log.error("任务类型有错误");
			result.put("message", "任务类型有错误");
			result.put("success", false);
			return result;
		}
		if (updateResult == 1) {
			log.error("此任务已经审核回调");
			result.put("message", "此任务已经审核回调，cp重复调用");
			result.put("success", false);
			return result;
		}
		if (updateResult == 2) {
			log.error("无效下载");
			result.put("message", "无效下载");
			result.put("success", false);
			return result;
		}
		result.put("message", "激活成功");
		result.put("success", true);
		return result;
	}

}
