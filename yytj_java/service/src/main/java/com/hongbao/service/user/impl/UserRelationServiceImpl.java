package com.hongbao.service.user.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hongbao.dal.mapper.UserRelationMapper;
import com.hongbao.dal.model.User;
import com.hongbao.dal.model.UserRelation;
import com.hongbao.dal.redis.JedisUtil;
import com.hongbao.service.UserService;
import com.hongbao.service.user.UserRelationService;
import com.hongbao.service.user.vo.ApprenticeVO;

@Service("userRelationService")
public class UserRelationServiceImpl implements UserRelationService {

	@Autowired
	private UserRelationMapper userRelationMapper;
	@Autowired
	private UserService userService;
	@Autowired
	private JedisUtil userHeadImgCache;

	@Override
	public List<ApprenticeVO> getAllApprenticeByPuserId(Long puserId) {

		List<UserRelation> apprentices = userRelationMapper.getAllApprenticesByPuserId(puserId);
		if (apprentices != null && apprentices.size() > 0) {
			List<ApprenticeVO> list = new ArrayList<ApprenticeVO>();
			for (UserRelation ur : apprentices) {
				ApprenticeVO vo = configApprentice(ur);
				if (vo != null) {
					list.add(vo);
				}
			}
			return list;
		}
		return null;
	}

	private ApprenticeVO configApprentice(UserRelation ur) {
		Long userId = ur.getUserId();
		User user = userService.load(userId);
		if (user != null) {
			ApprenticeVO vo = new ApprenticeVO();
			vo.setUserId(userId);
			if (StringUtils.isBlank(user.getPicUri())) {
				if (StringUtils.isBlank(userHeadImgCache.getData(userId + ""))) {
					String headImg = "";
					vo.setImgUrl(headImg);
				} else {
					vo.setImgUrl(userHeadImgCache.getData(userId + ""));
				}
			} else {
				vo.setImgUrl(user.getPicUri());
			}
			vo.setUserName(user.getNickname());
			vo.setRelationTime(ur.getCreatedAt());
			return vo;
		}
		return null;
	}

	@Transactional
	@Override
	public void insert(UserRelation userRelation) {
		UserRelation queryUserRelation = this.findByUserIdRelation(userRelation.getUserId());
		if (queryUserRelation == null) {
			userRelationMapper.insert(userRelation);
		}

	}

	@Override
	public ApprenticeVO getApprenticeNewByPuserId(Long puserId) {
		UserRelation userRelation = userRelationMapper.getApprenticeNewByPuserId(puserId);
		if (userRelation != null) {
			return configApprentice(userRelation);
		}
		return null;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public boolean userRelation(Long userId, String openId, String levelOpenId) {

		if (StringUtils.isBlank(openId)) {
			return false;
		}
		if (StringUtils.isBlank(levelOpenId)) {
			return false;
		}
		// User user = userService.selectByIdOpenIdOrUnionid(userId,openId,cat);
		User user = userService.load(userId);
		if (user == null) {
			return false;
		}
		// 邀请openid和 当前用户的openid 不相同
		if (!StringUtils.equals(openId, user.getOpenId())) {
			return false;
		}
		// 邀请openid 和被邀请相同
		if (StringUtils.equals(levelOpenId, user.getOpenId())) {
			return false;
		}
		// 判断是否已经建立关系 根据被邀请者的 levelOpenId
		UserRelation userRelation = userRelationMapper.findByOpenIdRelation(levelOpenId);
		if (userRelation != null) {
			return false;
		}

		// 帮被邀请的用户生产 注册
		// User inUser =userService.registerOpenId(levelOpenId);
		// if(inUser==null){
		// return false;
		// }
		UserRelation uRelation = new UserRelation();
		uRelation.setOpenId(levelOpenId); // 徒弟openid
		uRelation.setParentOpenId(openId); // 是否openid
		uRelation.setParentUserId(userId); // 师傅userid
		uRelation.setUserId(74L); // 徒弟userid
		userRelationMapper.insert(uRelation);
		return true;

	}

	/**
	 * 没有建立关系
	 */
	@Override
	public UserRelation findByOpenIdNoRelation(String openId) {
		UserRelation userRelation = userRelationMapper.findByOpenIdNoRelation(openId);
		return userRelation;
	}

	/**
	 * 建立关系
	 */
	@Override
	public void updateByOpenId(String openId, Long userId) {
		userRelationMapper.updateByOpenId(openId, userId);
	}

	@Override
	public User queryUserByUserId(Long userId) {
		UserRelation userRelation = userRelationMapper.findByUserIdRelation(userId);
		if (userRelation == null) {
			return null;
		}
		Long parentUserId = userRelation.getParentUserId();
		User user = userService.load(parentUserId);
		return user;
	}

	@Override
	public UserRelation findByUserIdRelation(Long userId) {
		UserRelation userRelation = userRelationMapper.findByUserIdRelation(userId);
		return userRelation;
	}

}
