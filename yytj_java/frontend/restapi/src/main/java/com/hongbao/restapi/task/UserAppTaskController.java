package com.hongbao.restapi.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hongbao.dal.base.annotation.NeedLogin;
import com.hongbao.dal.base.controller.ResponseObject;
import com.hongbao.dal.enums.UserType;
import com.hongbao.dal.model.User;
import com.hongbao.dal.model.UserApp;
import com.hongbao.error.BizException;
import com.hongbao.error.GlobalErrorCode;
import com.hongbao.restapi.BaseController;
import com.hongbao.service.UserService;
import com.hongbao.service.userapp.UserAppService;
import com.hongbao.utils.DateUtils;



/**
 * app端刷新任务的接口和UserAppTaskController相关的接口
 * 
 * @author tatos
 * 
 */
@Controller
@RequestMapping("/app")
public class UserAppTaskController extends BaseController {

	@Autowired
	private UserAppService userAppService;
	
	@Autowired
	private UserService  userService ;
	
	

	private Logger log = LoggerFactory.getLogger(UserAppTaskController.class);

	/**
	 * 手机端查询还有多少待安装app 查询10分钟之内的任务
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryTaskList")
	@NeedLogin(accessUserType = UserType.ALL)
	public ResponseObject<List<UserApp>> queryTaskList() {
		User user = userService.getCurrentUser();
		Date queryTime = DateUtils.getDateTimeBeforeMin(10000);
		ResponseObject<List<UserApp>> responseObject = null;
		try {
			List<UserApp> userAppList = userAppService.queryTaskList(
					user.getId(), queryTime);
			responseObject = new ResponseObject<List<UserApp>>();
			responseObject.setData(userAppList);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			responseObject = new ResponseObject<List<UserApp>>(
					GlobalErrorCode.INTERNAL_ERROR);

		}
		return responseObject;
	}
	
	


	/**
	 * 更新任务状态
	 * 
	 * @param userAppId
	 * @param status
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateTaskSatus")
	@NeedLogin(accessUserType = UserType.ALL)
	public ResponseObject<Map<String, Object>> updateTaskSatus(Long taskId,
			String status) {
		User user = getCurrentUser();
		ResponseObject<Map<String, Object>> responseObject = new ResponseObject<Map<String, Object>>();
		Map<String, Object> obj = new HashMap<String, Object>();
		try {
			userAppService.updateTaskSatus(taskId, status, user.getId());
		} catch (BizException e) {
			log.error(e.getMessage(), e);
			responseObject = new ResponseObject<Map<String, Object>>(
					e.getErrorCode());
			return responseObject;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			responseObject = new ResponseObject<Map<String, Object>>(
					GlobalErrorCode.INTERNAL_ERROR);
			responseObject.setData(obj);
			return responseObject;
		}
		return responseObject;
	}
	
	
}
