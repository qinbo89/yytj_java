package com.hongbao.service.user.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hongbao.dal.base.exception.BizException;
import com.hongbao.dal.config.ApplicationConfig;
import com.hongbao.dal.mapper.UserMapper;
import com.hongbao.dal.mapper.UserStatMapper;
import com.hongbao.dal.model.User;
import com.hongbao.dal.model.UserStat;
import com.hongbao.dal.redis.JedisUtil;
import com.hongbao.service.UserService;
import com.hongbao.service.support.ResponseHolder;
import com.hongbao.utils.DateUtil;
import com.hongbao.utils.MD5Util;
import com.hongbao.utils.UniqueNoUtils.UniqueNoType;
import com.hongbao.vo.PageVO;

@Service("userService")
public class UserServiceImpl implements UserService {

    private final Logger        log      = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserMapper          userMapper;

    @Autowired
    private JedisUtil           userByIdCache;

    @Autowired
    private UserStatMapper      userStatMapper;

    @Autowired
    private JedisUtil           jedisUtil;

    @Autowired
    private JedisUtil           userByLoginNameCache;

    @Autowired
    private ApplicationConfig   applicationConfig;

    private  ThreadLocal<String> sLoginId = new ThreadLocal<String>();

    @Value("${profiles.active}")
    private String              mode;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @Override
    public User loadUserByUsername(String username) {
        User user = loadUserFromLoginNameCache(username);
        return user;
    }

    @Override
    @Transactional
    public User register(String cid) {
        User u = userMapper.selectByuuid(cid);
        if (u != null) {
            return u;
        }
        String loginname = UniqueNoType.PT + cid;
        u = loadUserFromLoginNameCache(loginname);
        if (u != null) {
            loginname += System.currentTimeMillis();
        }
        User user = new User();
        user.setLoginname(loginname);
        user.setUuid(cid);
        this.insert(user);
        return user;
    }

    @Override
    @Transactional
    public User registerOpenId(String openId) {
        User u = userMapper.selectByOpenId(openId);
        if (u != null) {
            return u;
        }
        String loginname = UniqueNoType.PT + openId;
        u = loadUserFromLoginNameCache(loginname);
        if (u != null) {
            loginname += System.currentTimeMillis();
        }
        User user = new User();
        user.setLoginname(loginname);
        user.setOpenId(openId);
        this.insert(user);
        return user;
    }

    @Override
    public User register(String source, String outUserId, String outUserNick) {
        String loginName = UniqueNoType.PT + outUserId;
        User u = loadUserFromLoginNameCache(loginName);
        if (u != null) {
            return u;
        }
        User user = new User();
        user.setLoginname(loginName);
        user.setSource(source);
        user.setOutUserId(outUserId);
        user.setOutNick(outUserNick);
        this.insert(user);
        return user;
    }

    @Override
    @Transactional
    public User register(String phone, String password) {
        User u = loadUserFromLoginNameCache(phone);
        if (u != null) {
            return u;
        }
        User user = new User();
        user.setLoginname(phone);
        user.setPhone(phone);
        user.setPassword(password);
        this.insert(user);
        return user;
    }

    @Override
    @Transactional
    public User login(String phone, String password) {
        User user = userMapper.findUserByPhone(phone, true);
        if (user == null) {
            throw new BizException("账户不存在");
        }
        String comparePwd = MD5Util.MD5EncodeForPwd(phone, password);
        if (!comparePwd.equals(user.getPassword())) {
            throw new BizException("密码错误");
        }
        this.setCurrentUser(user);
        return user;
    }

    @Override
    public void logout(Long userId) {
        User user = selectByPrimaryKey(userId);
        String lastLoginIdKey = getLastLoginIdKey(user);
        String loginId = (String) jedisUtil.getObj(lastLoginIdKey);
        jedisUtil.delete(loginId);
        jedisUtil.delete(lastLoginIdKey);
    }

    @Override
    public boolean updateUserInfo(User user) {
        userByIdCache.delete(user.getId() + "");
        return userMapper.updateByPrimaryKeySelective(user) > 0;
    }

    @Override
    public User load(Long id) {
        User user = loadUserFromIdCache(id);
        return user;
    }

    @Override
    public boolean isRegistered(String mobile) {
        return userMapper.countRegistered(mobile) > 0;
    }

    @Override
    public int insert(User record) {
        return userMapper.insert(record);
    }

    @Override
    public User loadByLoginname(String loginname) {
        return userMapper.loadByLoginname(loginname);
    }

    @Override
    public User findUserFindByOutUserId(String outUserId) {
        List<User> users = userMapper.findUserByOutUserId(outUserId);
        if (users == null || users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    @Override
    public User findByUuid(String uuid) {
        return userMapper.selectByuuid(uuid);
    }

    @Override
    public boolean updateUserPhoneByUserId(User user) {
        // 清除缓存信息
        userByIdCache.delete(user.getId() + "");
        // 更新号码和登陆名称
        if (userMapper.updateUserPhoneByUserId(user) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public User selectByPrimaryKey(Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }

    /**
     * 查询一天的注册数（默认是昨天）
     */
    @Override
    public int findByOneDateRegisterAmount(Date startTime, Date endTime) {
        return userMapper.findByOneDateRegisterAmount(startTime, endTime);
    }

    /**
     * 统计所有的注册用户
     */
    @Override
    public int findAllUserAmount() {
        return userMapper.findAllUserAmount();
    }

    @Override
    public int insertUserStat(UserStat userStat) {
        return userStatMapper.insert(userStat);

    }

    @Override
    public int stat(UserStat userStat) {
        Integer stat = userStatMapper.stat(userStat).size();
        return stat;
    }

    @Override
    public User loadUserByOpenId(String openId) {
        if (org.apache.commons.lang3.StringUtils.isBlank(openId)) {
            return null;
        }
        return userMapper.selectByOpenId(openId);

    }

    @Override
    public User loadUserByUnionId(String unionId) {
        return userMapper.selectByUnionid(unionId);
    }

    private User loadUserFromLoginNameCache(String username) {
        User user = (User) userByLoginNameCache.getObj(username);
        if (user == null) {
            user = loadByLoginname(username);
            if (user != null) {
                userByLoginNameCache.setObj(username, user);
            }
        }
        return user;
    }

    private User loadUserFromIdCache(Long userId) {
        User user = (User) userByIdCache.getObj(userId + "");
        if (user == null) {
            user = selectByPrimaryKey(userId);
            if (user != null) {
                userByIdCache.setObj(userId + "", user);
            }
        }
        return user;
    }

    /**
     * cut:用于切换是用openid‘1’ 还是 Unionid ‘2’
     */
    @Override
    public User selectByIdOpenIdOrUnionid(Long userId, String openId, String cut) {
        User user = userMapper.selectByIdOpenIdOrUnionid(userId, openId, cut);
        return user;
    }

    @Override
    public PageVO<User> listUser(Integer page, Integer rows, Map<String, Object> queryParam) {
        PageVO<User> pageVO = new PageVO<User>();
        pageVO.setPage(page);

        Integer count = userMapper.getUserListCount(queryParam);
        if (count == null || count == 0) {
            pageVO.setTotal(0);
            pageVO.setRows(new ArrayList<User>());
        }
        pageVO.setTotal(count);
        page = (page <= 1) ? 1 : page;
        Pageable pageable = new PageRequest(page - 1, rows);

        List<User> list = userMapper.getUserList(pageable, queryParam);
        pageVO.setRows(list);
        return pageVO;
    }

    @Override
    public User getCurrentUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String loginId = getLoginId(request);
        User user = null;
        log.info("loginId=" + loginId);
        if (StringUtils.isBlank(loginId)) {
            loginId = sLoginId.get();
        }
        if (StringUtils.isNotBlank(loginId)) {
            user = (User) jedisUtil.getObj(loginId);
            log.info("user is null " + (user == null));
            if (user != null) {
                String lastLoginId = (String) jedisUtil.getObj(getLastLoginIdKey(user));
                if (loginId.equals(lastLoginId) == false) { // 生产环境开启

                    if (applicationConfig.isProd()) { // 单点登录 //
                        // log.info("删除lastLoginId=" + lastLoginId); //
                        // 检查最后一次登录的SESSION
                        // //
                        // jedisUtil.delete(lastLoginId);
                        // jedisUtil.delete(getLastLoginIdKey(user));
                        // user = null;
                    }
                } else {
                    jedisUtil.setObj(loginId, user);
                    jedisUtil.setObj(getLastLoginIdKey(user), loginId);
                }
            }
        }
        return user;
    }

    private String getLoginId(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String loginId = null;
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (KEY_LOGIN_ID.equalsIgnoreCase(cookie.getName()) && StringUtils.isNotBlank(cookie.getValue())) {
                    loginId = cookie.getValue();
                    break;
                }
            }
        }
        return loginId;
    }

    private String getLastLoginIdKey(User user) {
        return "id_" + user.getId() + "_type_" + user.getType() + "_lastLoginId";
    }

    @Override
    public List<User> findByUuidList(String uuid) {

        return userMapper.selectByuuidList(uuid);
    }

    @Override
    public User getCurrentUserNotUnauthorizedException() {
        return this.getCurrentUser();
    }

    @Override
    public User setCurrentUser(User user) {
        HttpServletResponse response = (HttpServletResponse) ResponseHolder.get();
        return setCurrentUser(user, response);
    }

    private User setCurrentUser(User user, HttpServletResponse response, boolean islogin) {
        user = userMapper.selectByPrimaryKey(user.getId());
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String url = request.getContextPath();
        log.info("url=" + url);
        String loginId = getLoginId(request);
        if (StringUtils.isBlank(loginId) || islogin) {
            loginId = UUID.randomUUID().toString();
        }
        sLoginId.set(loginId);
        Cookie cookie = new Cookie(KEY_LOGIN_ID, loginId);
        cookie.setMaxAge(DateUtil.MONTH_SECONDS);
        cookie.setPath("/");
        response.addCookie(cookie);

        log.info("set loginId=" + loginId);
        jedisUtil.setObj(loginId, user);
        jedisUtil.setObj(getLastLoginIdKey(user), loginId);
        return user;
    }

    private User setCurrentUser(User user, HttpServletResponse response) {
        return setCurrentUser(user, response, false);
    }
    
 

	@Override
	public void clearSloginId() {
		// TODO Auto-generated method stub
		sLoginId.remove();
	}

}
