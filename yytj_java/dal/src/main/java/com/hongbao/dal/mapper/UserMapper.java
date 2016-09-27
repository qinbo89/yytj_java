package com.hongbao.dal.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import com.hongbao.dal.model.User;


public interface UserMapper {

	int deleteByPrimaryKey(Long id);

	int undeleteByPrimaryKey(Long id);

	int insert(User record);

	User selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(User record);

	int updateByPrimaryKey(User record);

	int countRegistered(String mobile);

	User loadByLoginname(String loginname);



	List<User> findUserByOutUserId(String outUserId);

	User selectByuuid(String uuid);

	int updateUserPhoneByUserId(User user);

	// 一天的用户统计数 默认是昨天的注册统计数据
	int findByOneDateRegisterAmount(@Param("startTime") Date startTime,
			@Param("endTime") Date endTime);

	// 所以用户的数量
	int findAllUserAmount();

	User selectByOpenId(@Param("openId") String openId);

	User selectByUnionid(@Param("unionId") String unionId);

	User selectByIdOpenIdOrUnionid(@Param("id") Long userId,
			@Param("openId") String openId, @Param("cut") String cut);

	Integer getUserListCount(@Param("map") Map<String, Object> queryParam);

	List<User> getUserList(@Param("page") Pageable pageable,
			@Param("map") Map<String, Object> queryParam);

	List<User> selectByuuidList(String uuid);

	User findUserByPhone(@Param("phone") String phone,@Param("admin") boolean admin);

}
