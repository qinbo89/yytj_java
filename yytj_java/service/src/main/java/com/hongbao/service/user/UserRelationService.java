package com.hongbao.service.user;

import java.util.List;

import com.hongbao.dal.model.User;
import com.hongbao.dal.model.UserRelation;
import com.hongbao.service.user.vo.ApprenticeVO;



/**
 * 
 */
public interface UserRelationService {

	public void insert(UserRelation userRelation);

	/**
	 * 根据师父的编号查找徒弟
	 * 
	 * @param puserId
	 * @return
	 */
	public List<ApprenticeVO> getAllApprenticeByPuserId(Long puserId);

	public ApprenticeVO getApprenticeNewByPuserId(Long puserId);
	
	//关联关系 根据被邀请人的 id 判断该用户是否被建立过关系
//	UserRelation findByOpenIdRelation( String openId);
	
	
	//关注时 判断用户是否 有没有绑定过
	UserRelation findByOpenIdNoRelation( String openId);
	
	//建立关系
	void updateByOpenId(String openId,Long userId);
	
	
	public boolean userRelation(Long userId ,String openId ,String levelOpenId);
	
	User queryUserByUserId( Long userId);
	
	
	UserRelation findByUserIdRelation( Long userId);
}
