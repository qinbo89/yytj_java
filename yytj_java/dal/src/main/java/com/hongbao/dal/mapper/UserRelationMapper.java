package com.hongbao.dal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hongbao.dal.model.UserRelation;


public interface UserRelationMapper {

	List<UserRelation> getAllApprenticesByPuserId(
			@Param("parentUserId") Long puserId);

	UserRelation getApprenticeNewByPuserId(@Param("parentUserId") Long puserId);
	
	//关联关系 根据被邀请人的 id 判断该用户是否被建立过关系
	UserRelation findByOpenIdRelation(@Param("openId") String openId);
	
	
	void insert(UserRelation userRelation);
	
	//关注时 判断用户是否 有没有绑定过
	UserRelation findByOpenIdNoRelation(@Param("openId") String openId);
	
	//建立关系
	void updateByOpenId(@Param("openId") String openId,@Param("userId") Long userId);
	
	UserRelation findByUserIdRelation(@Param("userId") Long userId);
}
