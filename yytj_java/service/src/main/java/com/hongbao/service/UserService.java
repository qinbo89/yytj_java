package com.hongbao.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hongbao.dal.model.User;
import com.hongbao.dal.model.UserStat;
import com.hongbao.vo.PageVO;

public interface UserService {

	public static final String KEY_LOGIN_ID = "_login_id";
	public static final String ACCESS_CONTROL_ID = "_access_control_id";
	public static final String ACCESS_CONTROL_VAL = "_access_control_val";

	User loadUserByUsername(String name);

	int insert(User user);

	User getCurrentUser();

	User register(String cid);

	User register(String source, String outUserId, String outUserNick);

	/**
	 * 手机号码注册，若已存在，直接返回该对象
	 * 
	 * @param loginname
	 */
	User register(String loginname, String password);

	/**
	 * 更新用户姓名，身份证号码
	 * 
	 * @param user
	 * @return
	 */
	boolean updateUserInfo(User user);

	/**
	 * 用户是否已注册
	 */
	boolean isRegistered(String mobile);

	/**
	 * 用户是否已注册
	 */
	User loadByLoginname(String loginname);

	/**
	 * 通过外部用户id查找用户
	 * 
	 * @param outUserId
	 * @return
	 */
	User findUserFindByOutUserId(String outUserId);

	/**
	 * 用于测试 --Xie xh
	 */
	User load(Long id);

	/**
	 * 兼容老的沙克
	 * 
	 * @param outUserId
	 * @return
	 */
	User findByUuid(String uuid);

	boolean updateUserPhoneByUserId(User user);

	public User selectByPrimaryKey(Long id);

	// 一天的用户统计数 默认是昨天的注册统计数据
	int findByOneDateRegisterAmount(Date startTime, Date endTime);

	// 所以用户的数量
	int findAllUserAmount();

	/**
	 * 插入user 状态表
	 * 
	 * @param userStat
	 * @return
	 */
	int insertUserStat(UserStat userStat);

	/**
	 * 查询状态在不在线
	 * 
	 * @param userStat
	 * @return
	 */
	int stat(UserStat userStat);

	User loadUserByOpenId(String openId);

	User loadUserByUnionId(String unionId);

	public String getMode();

	/**
	 * cut:用于切换是用openid‘1’ 还是 Unionid ‘2’
	 */
	User selectByIdOpenIdOrUnionid(Long userId, String openId, String cut);

	public User registerOpenId(String openId);

	PageVO<User> listUser(Integer page, Integer rows,
			Map<String, Object> queryParam);

	List<User> findByUuidList(String uuid);

	/**
	 * 
	 * @return
	 */
	User getCurrentUserNotUnauthorizedException();

	User setCurrentUser(User user);

	User login(String phone, String password);

	void logout(Long userId);
	
	void  clearSloginId();


}
