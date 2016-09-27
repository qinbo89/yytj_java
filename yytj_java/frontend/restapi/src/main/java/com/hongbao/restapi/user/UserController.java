package com.hongbao.restapi.user;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hongbao.dal.base.annotation.NeedLogin;
import com.hongbao.dal.enums.UserType;
import com.hongbao.dal.model.User;
import com.hongbao.dal.model.UserStat;
import com.hongbao.dal.redis.JedisUtil;
import com.hongbao.restapi.BaseController;
import com.hongbao.service.UserService;




@Controller
public class UserController extends BaseController {

	

	@Autowired
	private JedisUtil userHeadImgCache;
	


	@ResponseBody
	@RequestMapping("/app/stat")
	@NeedLogin(accessUserType = UserType.ALL)
	public void insertUserStat(String uuid) {
		User user = userService.getCurrentUser();
		UserStat userStat = new UserStat();
		userStat.setUserId(user.getId());
		userStat.setUuid(uuid);
		userService.insertUserStat(userStat);
	}

	
	
	


}
