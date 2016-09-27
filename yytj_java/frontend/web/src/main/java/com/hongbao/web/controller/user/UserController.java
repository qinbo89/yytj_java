package com.hongbao.web.controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hongbao.dal.base.annotation.NeedLogin;
import com.hongbao.dal.base.controller.ResponseObject;
import com.hongbao.dal.enums.UserType;
import com.hongbao.dal.model.User;
import com.hongbao.dal.model.UserLocus;
import com.hongbao.dal.model.UserStat;
import com.hongbao.dal.redis.JedisUtil;
import com.hongbao.service.UserService;
import com.hongbao.service.score.UserScoreService;
import com.hongbao.service.user.UserLocusService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserLocusService userLocusService;

	@Autowired
	private JedisUtil userHeadImgCache;

	@Autowired
	private UserScoreService userScoreService;

	@RequestMapping("/weixin/portal")
	public String portal(Model model) {
		User user = userService.getCurrentUser();
		Long userId = user.getId();
		if (StringUtils.isBlank(user.getPicUri())) {
			if (StringUtils.isBlank(userHeadImgCache.getData(userId + ""))) {
				userHeadImgCache.setData(userId + "", "");
				user.setPicUri("http://wx.qlogo.cn/mmopen/PiajxSqBRaEIqxmkCqIqOictDnrNSn");
			} else {
				user.setPicUri(userHeadImgCache.getData(userId + ""));
			}
		}
		long totalScore = userScoreService.getTotalScoreByUserId(userId);
		long cutScore = userScoreService.getDeductedScoreByUserId(userId);
		long todayScore = userScoreService.getTodayScoreByUserId(userId);
		long canCash = totalScore - cutScore;
		model.addAttribute("totalScore", totalScore);
		model.addAttribute("cutScore", cutScore);
		model.addAttribute("canCash", canCash);
		model.addAttribute("todayScore", todayScore);
		model.addAttribute("user", user);
		return "portal";
	}

	@RequestMapping("/innerPage/profitPortal")
	@NeedLogin(accessUserType = UserType.ALL)
	public String profitPortal(Model model) {
		User user = userService.getCurrentUser();
		model.addAttribute("user", user);
		return "profit_portal";
	}

	@RequestMapping("/innerPage/individualCenter")
	public String personCenter(Model model) {
		User user = userService.getCurrentUser();
		model.addAttribute("user", user);
		return "weixin/personalCenter";
	}

	@RequestMapping("/innerPage/userInfo")
	public String userInfo(Model model) {
		User user = userService.getCurrentUser();
		model.addAttribute("user", user);
		return "weixin/userInfo";
	}

	@RequestMapping("/weixin/stat")
	@ResponseBody
	public ResponseObject<Integer> stat() {
		ResponseObject<Integer> obj = new ResponseObject<Integer>();
		User user = userService.getCurrentUser();
		UserStat userStat = new UserStat();
		userStat.setUserId(user.getId());
		Integer stat = userService.stat(userStat);
		obj.setData(stat);
		return obj;
	}

	@ResponseBody
	@RequestMapping(value = "/weixin/checkDownLoad")
	@NeedLogin(accessUserType = UserType.ALL)
	public ResponseObject<Integer> appLogin(HttpServletRequest req,
			HttpServletResponse resp) {
		User user = userService.getCurrentUser();
		List<UserLocus> list = userLocusService.getUserLocusByUserIdLimit(user
				.getId());
		ResponseObject<Integer> obj = new ResponseObject<Integer>();
		obj.setData(list.size());
		return obj;
	}

}
