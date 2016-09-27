package com.hongbao.service.user.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hongbao.dal.mapper.UserBindMapper;
import com.hongbao.dal.model.User;
import com.hongbao.dal.model.UserBind;
import com.hongbao.dal.model.UserRelation;
import com.hongbao.service.UserService;
import com.hongbao.service.user.UserBindService;
import com.hongbao.service.user.UserRelationService;
import com.hongbao.utils.UniqueNoUtils.UniqueNoType;

@Service("userBindService")
public class UserBindServiceImpl implements UserBindService {

	@Autowired
	private UserBindMapper userBindMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRelationService userRelationService;

	@Override
	public UserBind findUserBindByUnionId(String unionId) {
		UserBind userBind = userBindMapper.selectUserBindByUnionId(unionId);
		return userBind;
	}

	@Override
	public User bindOpenUserForLogin(UserBind userBind, User user) {
		UserBind queryUserBind = this.findUserBindByUnionId(userBind.getUnionId());
		String openId = userBind.getOpenId();
		String loginName = UniqueNoType.PT + openId;
		user.setLoginname(loginName);
		if (queryUserBind == null) {
			User useLogin = userService.loadByLoginname(loginName);
			if (useLogin != null) { // 判断用户是否存在 存在修改 --不做绑定
				user.setId(useLogin.getId());
				userService.updateUserInfo(user);
			} else { // 反之绑定修改
				userService.insert(user);
				userBind.setUserId(user.getId());
				this.bindOpenUser(userBind);
				UserRelation userRelation = userRelationService.findByOpenIdNoRelation(openId);
				if (userRelation != null) {// 存在没有建立关系的
					// 就进行关系处理
					userRelationService.updateByOpenId(openId, user.getId());
				}
			}
		} else {
			user = userService.load(queryUserBind.getUserId());
		}

		return user;
	}

	

	/**
	 * 1.判断是否已经绑定
	 * <p>
	 * 2.已经绑定就查出userId,去用户表查询是否存在该用户
	 * <p>
	 * <p>
	 * 3.如果不存在就注册一个用户
	 * </p>
	 * 
	 */
	@Override
	public int  bindOpenUser(UserBind userBind) {

		return  userBindMapper.insert(userBind);
	}

	@Override
	public void bindUser(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<UserBind> getByUserIdUserBindList(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
