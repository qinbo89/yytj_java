package com.hongbao.service.user.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hongbao.dal.mapper.UserLocusMapper;
import com.hongbao.dal.model.UserLocus;
import com.hongbao.dal.redis.JedisUtil;
import com.hongbao.service.user.UserLocusService;



@Service("userLocusService")
public class UserLocusServiceImpl implements UserLocusService {
	@Autowired
	private UserLocusMapper userLocusMapper;

	@Autowired
	private JedisUtil loginSiginCache;

	@Override
	public void addUserLocus(UserLocus userLocus) {
		userLocusMapper.addUserLocus(userLocus);
	}

	@Override
	public List<UserLocus> getUserLocusByUserId(Long userId) {
		return userLocusMapper.getUserLocusByUserId(userId);
	}

	@Override
	public Integer findByUuid(String deviceId) {
		Integer uuids = userLocusMapper.findByUuid(deviceId);
		return uuids;
	}

	@Override
	public List<UserLocus> getUserLocusByUserIdLimit(Long userId) {
		return userLocusMapper.getUserLocusByUserIdLimit(userId);
	}

	@Override
	public List<UserLocus> getLastLoginLocus(Long userId) {
		return userLocusMapper.getLastLoginLocusByUserId(userId);
	}

	@Override
	public String getLastLoginSign(Long userId) {

		List<UserLocus> list = this.getLastLoginLocus(userId);
		if (list != null && list.size() > 0) {
			UserLocus userLocus = list.get(0);
			return userLocus.getSigin();
		}
		/**
		 * String sigin = loginSiginCache.getData(userId); if
		 * (StringUtils.isBlank(sigin)) { List<UserLocus> list =
		 * this.getLastLoginLocus(userId); if (list != null && list.size() > 0)
		 * { UserLocus userLocus = list.get(0); loginSiginCache.setData(userId,
		 * userLocus.getSigin()); return sigin; }
		 **/
		return "";

	}
}
