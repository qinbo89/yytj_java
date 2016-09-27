package com.hongbao.service.user;

import java.util.List;

import com.hongbao.dal.model.UserLocus;


/**
 * 用户轨迹service层
 * 
 * 
 */
public interface UserLocusService {
	void addUserLocus(UserLocus userLocus);

	List<UserLocus> getUserLocusByUserId(Long userId);

	List<UserLocus> getUserLocusByUserIdLimit(Long userId);

	Integer findByUuid(String deviceId);

	List<UserLocus> getLastLoginLocus(Long userId);

	String getLastLoginSign(Long userId);
}
