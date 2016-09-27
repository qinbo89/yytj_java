package com.hongbao.web.controller.tryapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hongbao.dal.base.annotation.NeedLogin;
import com.hongbao.dal.base.annotation.NotNeedLogin;
import com.hongbao.dal.enums.UserType;
import com.hongbao.dal.model.TryApp;
import com.hongbao.dal.model.User;
import com.hongbao.dal.model.UserApp;
import com.hongbao.dal.vo.TryAppVo;
import com.hongbao.service.tryapp.TryAppService;
import com.hongbao.service.userapp.UserAppService;
import com.hongbao.utils.DateUtils;
import com.hongbao.utils.UAgentInfo;
import com.hongbao.web.controller.base.BaseController;



@Controller
public class TryAppController  extends BaseController{

	@Autowired
	private TryAppService tryAppService;

	@Autowired
	private UserAppService userAppService;

	@RequestMapping(value = "/innerPage/queryTryAppList")
	@NotNeedLogin
	public String queryTryAppList(HttpServletRequest req,
			HttpServletResponse resp, Model model) {
		String  userId = req.getParameter("userId");
		Long myUserId = new Long(userId);
		String userAgent = req.getHeader("user-agent");
		UAgentInfo uAgentInfo = new UAgentInfo(userAgent, "");
		//if (uAgentInfo.detectIos()) {
			User user = userService.load(myUserId);
			List<TryAppVo> showList = new ArrayList<TryAppVo>();
			List<TryAppVo> list = tryAppService.queryTryApp(user.getId());
			if (list != null) {
				for (TryAppVo tryApp : list) {
					if (StringUtils.equals(tryApp.getIsAdmin(), "1")) {
						if (user.getAdmin()) {
							showList.add(tryApp);
						}
					} else {
						showList.add(tryApp);
					}
				}
			}
			List<TryAppVo> notStartTryList = tryAppService.queryNotTryApp(user.getId());
			model.addAttribute("tryList", showList);
			model.addAttribute("notStartTryList", notStartTryList);
			model.addAttribute("userId", user.getId());
			if (showList == null || showList.size() == 0) { // 当列表为空时要求为空

				return "empty";
			}
			//return "tryAppList";
		//}

		return "tryAppList";
	}
	
	
	@RequestMapping(value = "/innerPage/queryInvestTryAppList")
	@NotNeedLogin
	public String queryInvestTryAppList(HttpServletRequest req,
			HttpServletResponse resp, Model model) {
		String  userId = req.getParameter("userId");
		String userAgent = req.getHeader("user-agent");
		UAgentInfo uAgentInfo = new UAgentInfo(userAgent, "");
		//if (uAgentInfo.detectIos()) {
		    User user = userService.getCurrentUser();
			List<TryAppVo> showList = new ArrayList<TryAppVo>();
			List<TryAppVo> list = tryAppService.queryInvestTryApp(user.getId());
			if (list != null) {
				for (TryAppVo tryApp : list) {
					if (StringUtils.equals(tryApp.getIsAdmin(), "1")) {
						if (user.getAdmin()) {
							showList.add(tryApp);
						}
					} else {
						showList.add(tryApp);
					}
				}
			}
			model.addAttribute("tryList", showList);
			model.addAttribute("userId", user.getId());
			if (showList == null || showList.size() == 0) { // 当列表为空时要求为空

				return "empty";
			}
			//return "investTryApp";
		//}

		return "investTryApp";
	}

	@RequestMapping(value = "/weixin/appDetail")
	public String appDetail(@RequestParam(required = true) Long id,
			String userId, HttpServletRequest req, HttpServletResponse resp,
			Model model) {
		TryApp tryApp = tryAppService.queryTryAppById(id);
		model.addAttribute("tryApp", tryApp);
		model.addAttribute("userId", userId);
		return "weixin/appDetail";
	}
	
	
	@RequestMapping(value = "/innerPage/appInfo")
	@NeedLogin(accessUserType = UserType.ALL)
	@ResponseBody
	public TryApp appInfo(@RequestParam(required = true) Long id, HttpServletRequest req, HttpServletResponse resp,
			Model model) {
		TryApp tryApp = tryAppService.queryTryAppById(id);
		return tryApp;
	}

	@RequestMapping(value = "/innerPage/copyRedirect")
	@NeedLogin(accessUserType = UserType.ALL)
	@ResponseBody
	public Map<String,Object> copyRedirect(@RequestParam(required = true) Long id, HttpServletRequest req, HttpServletResponse resp,
			Model model) {
		Map<String,Object> result = new HashMap<String,Object>();
		TryApp tryApp = tryAppService.queryTryAppById(id);
		User user = userService.getCurrentUser();
		UserApp userApp = new UserApp();
		userApp.setAppId(tryApp.getId());
		userApp.setAppImg(tryApp.getAppImg());
		userApp.setAppName(tryApp.getAppName());
		userApp.setKeyWord(tryApp.getKeyWord());
		userApp.setPackageName(tryApp.getPackageName());
		userApp.setProjectName(tryApp.getProjectName());
		userApp.setTryTime(tryApp.getTryTime());
		userApp.setSchema(tryApp.getSchema());
		userApp.setStatus("0");
		userApp.setScore(tryApp.getScore());
		userApp.setSuccessStatus(tryApp.getSuccessStatus());
		userApp.setUserId(user.getId());
		userApp.setAppAppId(tryApp.getAppAppId());
		UserApp queryUserApp = new UserApp();
		queryUserApp.setAppId(tryApp.getId());
		queryUserApp.setUserId(user.getId());
		queryUserApp.setCreatedAt(DateUtils.getDateBefore(60));
		List<UserApp> list = userAppService.queryByUserIdAndAppId(queryUserApp);
		if (list.size() == 0) {
			userAppService.insertUserAppAndCallCp(userApp);
		}
		result.put("result", "ok");
		return result;
	}
	
	

	
	
	
}
