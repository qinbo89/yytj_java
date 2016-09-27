package com.hongbao.web.controller.score;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hongbao.dal.GlobalResult;
import com.hongbao.dal.model.User;
import com.hongbao.dal.model.UserScore;
import com.hongbao.dal.model.UserScoreTopTen;
import com.hongbao.dal.query.UserScoreQuery;
import com.hongbao.service.UserService;
import com.hongbao.service.score.UserScoreService;

@Controller
public class UserScoreWebController {
	@Autowired
	private UserService userService;
	@Autowired
	private UserScoreService userScoreService;

	@ResponseBody
	@RequestMapping("/innerPage/addScoreInfo")
	public GlobalResult insertScore(UserScore userScore) {
		User user = userService.getCurrentUserNotUnauthorizedException();
		if (user != null) {
			userScoreService.insert(userScore);
		}
		return GlobalResult.notLogin;
	}

	@RequestMapping("/innerPage/getScoreInfo")
	public String getScoreInfo(UserScoreQuery query, Model model) {
		User user = userService.getCurrentUser();
		query.setUserId(user.getId());
		List<UserScore> dataList = userScoreService.selectListByUserId(query);
		model.addAttribute("dataList", dataList);
		model.addAttribute("query", query);
		return "weixin/scoreInfo";
	}

	/**
	 * 获取top 10 排行榜
	 */
	@ResponseBody
	@RequestMapping("/innerPage/getTopTen")
	public List<UserScoreTopTen> getTopTen() {
		List<UserScoreTopTen> list = userScoreService.getTopTen();
		return list;
	}

}
