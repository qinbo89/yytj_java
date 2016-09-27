package com.hongbao.web.controller.login;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hongbao.dal.base.controller.ResponseObject;
import com.hongbao.dal.model.User;
import com.hongbao.dal.model.UserBind;


@Controller
public class OpenLoginController {



	@RequestMapping(value = "/open_login/index", method = RequestMethod.GET)
	public String index(Principal principal, HttpServletRequest req,
			HttpServletResponse resp, Model model) {
		String uri = req.getHeader("Referer");
		String url = uri;
		model.addAttribute("redirectUrl", url);
		return "login/open_login";
	}

	/**
	 * 从第三方外部登录
	 * <p>
	 * 1.判断该用户是否是第一次进入
	 * </p>
	 * <p>
	 * 2.如果是第一次登录， 就插入一个自动注册一个用户 ，然后user_bind表插入一条记录
	 * </p>
	 * 
	 * 3.如果不是第一次登录,查出用户user对象登录 这个方法需要和客户端进行校验，防止恶意注册,这个到时候跟客户端开发人员协商
	 * 
	 * @param userBindForm
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/user/open_login")
	public ResponseObject<UserBind> openLogin(
			@Valid @ModelAttribute UserBindForm userBindForm, Errors errors,
			HttpServletRequest req, HttpServletResponse resp) {

		UserBind userBind = new UserBind();
		BeanUtils.copyProperties(userBindForm, userBind);
		//这里一定要记得 根据业务改
		User user = new User();
		
		
		ResponseObject<UserBind> rep = new ResponseObject<UserBind>();
		rep.setData(userBind);
	    //XXX login success
		return rep;
	}
}
