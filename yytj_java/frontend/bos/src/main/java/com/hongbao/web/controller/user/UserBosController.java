package com.hongbao.web.controller.user;

import com.hongbao.dal.base.exception.BizException;
import com.hongbao.dal.enums.UserType;
import com.hongbao.web.controller.base.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hongbao.dal.base.annotation.NeedLogin;
import com.hongbao.dal.base.annotation.NotNeedLogin;
import com.hongbao.dal.base.controller.ResponseObject;
import com.hongbao.dal.model.User;
import com.hongbao.dal.redis.JedisUtil;
import com.hongbao.service.RequestUtilService;
import com.hongbao.service.UserService;

@Controller
public class UserBosController extends BaseController{

	@Autowired
	private UserService userService;

	@Autowired
	private JedisUtil jedisUtil;

	@Autowired
	private RequestUtilService requestUtilService;

	@RequestMapping("login.html")
	@NotNeedLogin
	public String login() {
		return "login";
	}

	@RequestMapping("login")
	@ResponseBody
	@NotNeedLogin
	public ResponseObject doLogin(@RequestParam String accountNo,
								  @RequestParam String pwd, @RequestParam String imageCheckCode) {
		// validate
		String imageCheckCodeCached = (String) jedisUtil
				.getObj(requestUtilService
						.getCookie(UserService.ACCESS_CONTROL_ID)
						+ "_imageCheckCode");
		if (StringUtils.isBlank(imageCheckCode)
				|| imageCheckCode.equalsIgnoreCase(imageCheckCodeCached) == false) {
			throw new BizException("验证码错误");
		}
		// login
		ResponseObject ret = new ResponseObject();
		User user = userService.login(accountNo, pwd);
		ret.setData(user);
		return ret;
	}

	@RequestMapping("logout")
	@ResponseBody
	@NeedLogin
	public ResponseObject logout() {
		User user = userService.getCurrentUser();
		userService.logout(user.getId());
		ResponseObject ret = new ResponseObject();
		return ret;
	}

}
