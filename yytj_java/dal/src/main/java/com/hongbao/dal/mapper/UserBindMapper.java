package com.hongbao.dal.mapper;

import com.hongbao.dal.model.UserBind;

/**
 * 
 * @author tatos
 * 
 */
public interface UserBindMapper {

	/**
	 * 查询改用户是否已经绑定
	 * 
	 * @param openId
	 * @param source
	 * @return
	 */
	UserBind selectUserBindByUnionId(String openId);

	/**
	 * 插入绑定表
	 * 
	 * @param userBind
	 */
	int insert(UserBind userBind);

}
