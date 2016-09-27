package com.hongbao.restapi.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hongbao.dal.base.annotation.NotNeedLogin;
import com.hongbao.dal.base.controller.ResponseObject;
import com.hongbao.dal.model.User;
import com.hongbao.dal.model.UserBind;
import com.hongbao.dal.model.UserLocus;
import com.hongbao.error.GlobalErrorCode;
import com.hongbao.service.UserService;
import com.hongbao.service.user.UserLocusService;


@Controller
public class UserAuthController {

	private static Log log = LogFactory.getLog(UserAuthController.class);

	@Autowired
	private UserService userService;



	
	@Autowired
	private UserLocusService userLocusService;

	

	

	/**
	 * 
	 * 
	 * @param form
	 * @param errors
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/registered")
	public ResponseObject<Boolean> isRegistered(
			@Valid @ModelAttribute UserForm form, Errors errors) {

		return new ResponseObject<Boolean>(userService.isRegistered(form
				.getMobile()));

	}

	
	
	
	

	@ResponseBody
	@RequestMapping(value = "/signined")
	public ResponseObject<UserVO> signined() {

		try {

			User user = userService.getCurrentUser();
			List<UserBind> userBindList = new ArrayList<UserBind>();
			UserVO userVO = new UserVO();
			userVO.setAvatar(user.getAvatar());
			userVO.setId(user.getId());
			userVO.setName(user.getName());
			userVO.setPhone(user.getPhone());

			userVO.setNickName(user.getNickname());
			userVO.setBirthday((user.getBirthday() == null) ? 0l : user
					.getBirthday().getTime());
			userVO.setHobbies(user.getHobbies());
			userVO.setPicUrl(user.getPicUri());
			userVO.setOccupation(user.getOccupation());
			userVO.setCity(user.getCity());
			userVO.setSex(user.getSex());
			userVO.setDecodeUserId(user.getId());

			userVO.setUserBindList(userBindList);
			ResponseObject<UserVO> responseObject = new ResponseObject<UserVO>();
			responseObject.setData(userVO);
			return responseObject;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseObject<UserVO>();
		}

	}

	/**
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/recordDeviceDInfo")
	public ResponseObject<Boolean> recordDeviceDInfo(
			@RequestHeader("device") String device,
			@RequestHeader("uuid") String uuid) {
		try {
			saveUserLocus(device, uuid);
		} catch (DataAccessException e) {
			log.error(e.getMessage(), e);
			return new ResponseObject<Boolean>(Boolean.FALSE);
		}
		ResponseObject<Boolean> result = new ResponseObject<Boolean>(
				GlobalErrorCode.SUCESS);
		result.setData(Boolean.TRUE);
		return result;

	}

	private void saveUserLocus(String device, String uuid) {
		UserLocus userLocus = new UserLocus();
		User user = userService.getCurrentUser();
		userLocus.setUserId(user.getId());
		userLocus.setDeviceType(device);
		userLocus.setDeviceId(uuid);
		userLocus.setLoginTime(new Date());
	    userLocusService.addUserLocus(userLocus);
	}

	

	@ResponseBody
	@RequestMapping(value = "/app/login")
	@NotNeedLogin
	public ResponseObject<User> appLogin(String uuid,Long userId ,
			String source, String isJailBreak, HttpServletRequest req,
			HttpServletResponse resp) {
        
	

		String sigin = req.getParameter("sign");
		UserBind userBind = new UserBind();
		userBind.setUuid(uuid);
		userBind.setSource(source);
		Boolean needBind = false;
		User uuid_user = userService.findByUuid(uuid);
		if (uuid_user != null) {// 已经绑定
			
			needBind = false;
		} else {
			needBind = true;
		}

		User user = userService.getCurrentUser();
		if (user != null) {
			ResponseObject<User> rep = new ResponseObject<User>();
			rep.setData(user);
			return rep;
		}

		user = userService.load(userId);
		if (user == null) {
			ResponseObject<User> rep = new ResponseObject<User>(
					GlobalErrorCode.NOATTEN);
			rep.setData(null);
			return rep;
		}

		userService.setCurrentUser(user);
		ResponseObject<User> rep = new ResponseObject<User>();
		rep.setData(user);
		if (needBind) {// 这个手机之前没绑定过微信账号,现在绑定
			User updateUUidUser = new User();
			updateUUidUser.setId(user.getId());
			updateUUidUser.setUuid(uuid);
			userService.updateUserInfo(updateUUidUser);
		}
		try {
			UserLocus userLocus = new UserLocus();
			userLocus.setUserId(user.getId());
			userLocus.setDeviceId(uuid);
			userLocus.setDeviceType(source);
			userLocus.setSigin(sigin);
			userLocusService.addUserLocus(userLocus);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return rep;

	}


}
