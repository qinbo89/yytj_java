package com.hongbao.service.userapp;

import java.util.Date;
import java.util.List;

import com.hongbao.dal.model.UserApp;


/**
 * 
 * @author tatos
 * 
 */
public interface UserAppService {

	/**
	 * 查询用户在微信已经领到的任务
	 * 
	 * @return
	 */
	List<UserApp> queryTaskList(Long userId, Date queryTime);

	/**
	 * 更新任务
	 * 
	 * @param appId
	 * @param status
	 */
	void updateTaskSatus(Long appId, String status, Long userId);

	List<UserApp> getUserAppListByUserId(Long userId);

	/**
	 * 
	 * @param appId
	 * @param status
	 */
	void insert(UserApp userApp);

	/**
	 * 
	 * @param appId
	 * @param status
	 */
	void insertUserAppAndCallCp(UserApp userApp);

	UserApp getUserAppNewByUserId(Long userId);

	List<UserApp> queryByUserIdAndAppId(UserApp userApp);

	int updateYoumiTaskSatus(UserApp userApp);


	/**
	 * 更新Cp对接的
	 * 
	 * @param userApp
	 * @return
	 */
	int updateCpUserAppStatus(UserApp userApp, String status, Long userId);

	/**
	 * 查询特定的idfa下载的特定appappid的列表
	 * 
	 * @param userApp
	 * @return
	 */
	List<UserApp> queryUserAppListByIdfa(UserApp userApp);

	/**
	 * 更新Cp对接的
	 * 
	 * @param userApp
	 * @return
	 */
	int updateWpUserAppStatus(UserApp userApp);
	
	List<UserApp> getUserAppListByPageUid(String pageUid);
	
	void addUserSocore(String openId,Long userId,int score,String message);

}
