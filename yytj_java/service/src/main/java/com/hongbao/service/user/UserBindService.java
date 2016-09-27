package com.hongbao.service.user;

import java.util.List;

import com.hongbao.dal.model.User;
import com.hongbao.dal.model.UserBind;


/**
 * 类UserBindService.java的实现描述：TODO 类实现描述
 * 
 * @author tatos 2014年7月29日 下午5:09:15
 */
public interface UserBindService  {

	/**
	 * 根据外部唯一openId 查出该用户是否在本系统登录
	 * 
	 * @param openId
	 * @return
	 */
	UserBind findUserBindByUnionId(String openId);

	/**
	 * 绑定外部用户,返回用户记录
	 * 
	 * @param userBind
	 * @return
	 */
	int  bindOpenUser(UserBind userBind);

	/**
	 * 绑定外部用户,返回用户记录
	 * 
	 * @param userBind
	 * @return
	 */
	User bindOpenUserForLogin(UserBind userBind, User user);

	/**
	 * 用户自主绑定
	 * 
	 * @param user
	 * @param code
	 */
	void bindUser(User user);

	/**
	 * 根据用户的id 查询对应的 绑定信息 列表
	 * 
	 * @param userId
	 * @return
	 */
	public List<UserBind> getByUserIdUserBindList(String userId);

}
