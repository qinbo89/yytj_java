package com.hongbao.restapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hongbao.dal.model.User;
import com.hongbao.service.UserService;


/**
 * rest-api工程的基类Controller
 * 
 */
public class BaseController {
	protected Logger log = LoggerFactory.getLogger(getClass());
	
    @Autowired
	protected   UserService userService;
	
	public User getCurrentUser() {
		return userService.getCurrentUser();
	}
	
	

}
