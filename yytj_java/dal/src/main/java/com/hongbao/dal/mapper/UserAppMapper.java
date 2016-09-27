package com.hongbao.dal.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hongbao.dal.model.AppCtr;
import com.hongbao.dal.model.UserApp;


/**
 * @author bes
 * 
 */
public interface UserAppMapper {

	/**
	 * 查询app端待处理的applist,queryTime这个时间是查询的截止时间
	 * 
	 * @param userId
	 * @param queryTime
	 * @return
	 */
	List<UserApp> queryUserAppList(@Param("userId") Long userId,
			@Param("queryTime") Date queryTime);

	/**
	 * 查询app端待处理的applist,queryTime这个时间是查询的截止时间
	 * 
	 * userApp 为要更新的userApp
	 * 
	 * @return
	 */
	Long updateUserApp(UserApp userApp);

	List<UserApp> getUserAppListByUserId(@Param("userId") Long userId);

	/**
	 * 
	 * @param userApp
	 * @return
	 */
	Long insert(UserApp userApp);

	UserApp getUserAppNewByUserId(Long userId);

	UserApp getUserAppId(Long Id);

	/**
	 * 查出成功
	 * 
	 * @param userApp
	 * @return
	 */
	List<UserApp> getUserSuccessAppList(UserApp userApp);

	/**
	 * 查询在当前时间之前时间段里面有没有领取任务
	 * 
	 * @param userApp
	 * @return
	 */
	List<UserApp> selectByUserIdAndAppId(UserApp userApp);
	
	/**
	 * 查询在当前时间之前时间段里面有没有领取任务
	 * 
	 * @param userApp
	 * @return
	 */
	List<UserApp> selectByUserIdAndAppName(UserApp userApp);
	
	/**
	 * 查询试用这个app的试用列表
	 * @param userApp
	 * @return
	 */
	List<UserApp> selectByAppName(UserApp userApp);
	
	/**
	 * 根据pageUid记录查询userApp对象
	 * @param pageUid
	 * @return
	 */
	List<UserApp> selectByAppPageUid(String pageUid);
	
	/**
	 * 通过uuid查找正任务
	 * @param uuid
	 * @return
	 */
	List<UserApp> selectByUuid(UserApp userApp);
	
	/**
	 * 
	 * @param userApp
	 */
	int updateCpUserApp(UserApp userApp);
	
	/**
	 * 在一定时间内成功的app
	 * @param param
	 * @return
	 */
	int querySuccessAppCount(AppCtr appCtr);

}
