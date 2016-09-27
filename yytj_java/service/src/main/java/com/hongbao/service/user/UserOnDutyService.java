package com.hongbao.service.user;

import java.util.List;

import com.hongbao.dal.model.UserOnDuty;

public interface UserOnDutyService {

	UserOnDuty checkOnDutyToday(String userId);

	List<UserOnDuty> getUserOnDutyRecord(String userId, int dayCount);

	/**
	 * 处理签到并且返回加分
	 * 
	 * @param userOnDuty
	 * @param userId
	 * @return
	 * 
	 * 		OnDutyVO dealScoreWhileOnduty(UserOnDuty userOnDuty, String
	 *         userId);
	 */
}
