package com.hongbao.dal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hongbao.dal.model.UserLocus;


public interface UserLocusMapper {
	void addUserLocus(UserLocus userLocus);

	List<UserLocus> getUserLocusByUserId(@Param("userId") Long userId);

	List<UserLocus> getUserLocusByUserIdLimit(@Param("userId") Long userId);

	Integer findByUuid(@Param("deviceId") String deviceId);

	List<UserLocus> getLastLoginLocusByUserId(@Param("userId") Long userId);
}
