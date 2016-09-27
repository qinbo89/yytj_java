package com.hongbao.service.userapp.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestOperations;

import com.hongbao.dal.mapper.UserAppMapper;
import com.hongbao.dal.model.AppCtr;
import com.hongbao.dal.model.ClickLog;
import com.hongbao.dal.model.ScoreType;
import com.hongbao.dal.model.TryApp;
import com.hongbao.dal.model.User;
import com.hongbao.dal.model.UserApp;
import com.hongbao.dal.model.UserScore;
import com.hongbao.dal.redis.JedisUtil;
import com.hongbao.error.GlobalErrorCode;
import com.hongbao.service.UserService;
import com.hongbao.service.error.BizException;
import com.hongbao.service.impl.BaseServiceImpl;
import com.hongbao.service.score.UserScoreService;
import com.hongbao.service.tryapp.ClickLogService;
import com.hongbao.service.tryapp.TryAppService;
import com.hongbao.service.user.UserRelationService;
import com.hongbao.service.user.impl.WeixinService;
import com.hongbao.service.userapp.AppCtrService;
import com.hongbao.service.userapp.UserAppService;
import com.hongbao.utils.MoneyUtil;

/**
 * 
 * @author tatos
 * 
 */

@Service("userAppService")
public class UserAppServiceImpl extends BaseServiceImpl implements UserAppService {

	private Logger log = LoggerFactory.getLogger(UserAppServiceImpl.class);

	@Autowired
	private UserScoreService userScoreService;

	@Autowired
	private UserAppMapper userAppMapper;

	@Autowired
	private WeixinService weixinService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRelationService userRelationService;

	@Autowired
	private TryAppService tryAppService;

	@Autowired
	private RestOperations restTemplate;

	@Autowired
	private ClickLogService clickLogService;

	@Autowired
	private AppCtrService appCtrService;
	
	@Autowired
	private JedisUtil countAddCache;

	@Override
	public List<UserApp> queryTaskList(Long userId, Date queryTime) {
		List<UserApp> list = userAppMapper.queryUserAppList(userId, queryTime);
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public void updateTaskSatus(Long id, String status, Long userId) {
		UserApp userApp = userAppMapper.getUserAppId(id);
		TryApp tryApp = tryAppService.queryMyTryAppById(userApp.getAppId());
		if (userApp == null) {
			log.warn("taskid=" + id + " is null");
			throw new BizException(GlobalErrorCode.NOAPP, "没有这个app");
		}
		UserApp updatedUserApp = new UserApp();
		updatedUserApp.setId(id);
		updatedUserApp.setStatus(status);
		updatedUserApp.setUserId(userId);
		Long row = userAppMapper.updateUserApp(updatedUserApp);
		if (row == 0) {
			log.warn("taskid=" + id + " has success");
			throw new BizException(GlobalErrorCode.STATUS_UPDATED, "状态已经更新");
		}
		User user = userService.load(userApp.getUserId());
		String uuid = user.getUuid();
		List<User> users = userService.findByUuidList(uuid);
		int successCnt = 0;
		for (User myuser : users) {
			UserApp queryUserApp = new UserApp();
			queryUserApp.setAppId(userApp.getAppId());
			queryUserApp.setUserId(myuser.getId());
			List<UserApp> list = userAppMapper.getUserSuccessAppList(queryUserApp);
			successCnt = successCnt + list.size();
		}
		log.warn("successCnt=" + successCnt);

		if (StringUtils.equals(status, userApp.getSuccessStatus()) && (successCnt == 1 || userApp.getAppId() <= 0)) {// 和第三方兼容
			UserScore userScore = new UserScore();
			userScore.setScore(userApp.getScore());
			userScore.setUserId(userApp.getUserId());
			userScore.setType(ScoreType.TryPlayScore);
			userScore.setObjectId(userApp.getId());
			userScoreService.insert(userScore);
			try {
				weixinService.sendWeiXinMessage(user.getOpenId(), "您试用『" + userApp.getAppName() + "』奖金"
						+ MoneyUtil.changeF2Y(new Long(userApp.getScore())) + "元");
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}

			if (userApp.getAppId() <= 0) {
				int myScore = (userApp.getScore() * 2) / 10;
				insertParentScore(userApp, userApp.getUserId(), myScore);
			} else {
				int myScore = tryApp.getParentScore();
				insertParentScore(userApp, userApp.getUserId(), myScore);
			}

		}
		checkAppCtr(tryApp);//控制数量
	}

	private void insertParentScore(UserApp userApp, Long userId, Integer score) {

		User user = userRelationService.queryUserByUserId(userId);
		if (user == null) {
			return;
		}
		UserScore userScore = new UserScore();
		userScore.setScore(score);
		userScore.setUserId(user.getId());
		userScore.setType(ScoreType.ApprenticeScore);
		userScore.setObjectId(userApp.getId());
		userScoreService.insert(userScore);
		try {
			weixinService.sendWeiXinMessage(user.getOpenId(),
					"您徒弟试玩『" + userApp.getAppName() + "』您奖金" + MoneyUtil.changeF2Y(new Long(score)) + "元,快去收徒弟");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	public List<UserApp> getUserAppListByUserId(Long userId) {
		if (userId == null) {
			return null;
		}
		return userAppMapper.getUserAppListByUserId(userId);
	}

	@Override
	public void insert(UserApp userApp) {
		userAppMapper.insert(userApp);
	}

	@Override
	public UserApp getUserAppNewByUserId(Long userId) {
		if (userId == null) {
			return null;
		}
		return userAppMapper.getUserAppNewByUserId(userId);
	}

	public List<UserApp> queryByUserIdAndAppId(UserApp userApp) {
		return userAppMapper.selectByUserIdAndAppId(userApp);
	}

	@Override
	public int updateYoumiTaskSatus(UserApp userApp) {
		List<UserApp> queryList = userAppMapper.selectByUserIdAndAppName(userApp);
		if (queryList == null || queryList.size() == 0) {
			userAppMapper.insert(userApp);
			this.updateTaskSatus(userApp.getId(), "3", userApp.getUserId());
			// notifiedOthers(userApp);
			return 1;
		} else {
			log.error("这个app已经下载");
			return 0;
		}
	}

	private void notifiedOthers(UserApp userApp) {
		List<UserApp> list = userAppMapper.selectByAppName(userApp);
		Set<Long> sendSets = new HashSet<Long>();
		for (UserApp app : list) {
			sendSets.add(app.getUserId());
		}
		User my = userService.load(userApp.getUserId());
		for (Long userId : sendSets) {
			User user = userService.load(userId);
			try {
				if (userId == my.getId().intValue()) {
					continue;
				}
				weixinService.sendWeiXinMessage(user.getOpenId(), my.getNickname() + "试用『" + userApp.getAppName()
						+ "』奖金" + MoneyUtil.changeF2Y(new Long(userApp.getScore())) + "元，快去玩吧，您也能拿奖金");
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	@Override
	public List<UserApp> getUserAppListByPageUid(String pageUid) {
		List<UserApp> list = userAppMapper.selectByAppPageUid(pageUid);
		return list;
	}

	@Override
	public int updateCpUserAppStatus(UserApp userApp, String status, Long userId) {
		TryApp tryApp = tryAppService.queryMyTryAppById(userApp.getAppId());
		if (!StringUtils.equals(tryApp.getTaskType(), "1")) {// 判断 如果是cp回调的任务
			return 0;
		}
		if (StringUtils.equals(userApp.getSuccessStatus(), userApp.getStatus())) {// 标示改用户已经付款，cp重复调用
			return 1;
		}
		UserApp updatedUserApp = new UserApp();
		updatedUserApp.setId(userApp.getId());
		updatedUserApp.setStatus(status);
		updatedUserApp.setUserId(userId);
		Long row = userAppMapper.updateUserApp(updatedUserApp);
		if (row == 0) {
			log.warn("taskid=" + userApp.getId() + " has success");
			throw new BizException(GlobalErrorCode.STATUS_UPDATED, "状态已经更新或没有有效任务");
		}

		User user = userService.load(userApp.getUserId());// 查询同一手机上的其他用户
		String uuid = user.getUuid();
		List<User> users = userService.findByUuidList(uuid);
		int successCnt = 0;
		for (User myuser : users) {
			UserApp queryUserApp = new UserApp();
			queryUserApp.setAppId(userApp.getAppId());
			queryUserApp.setUserId(myuser.getId());
			List<UserApp> list = userAppMapper.getUserSuccessAppList(queryUserApp);
			successCnt = successCnt + list.size();
		}
		log.warn("successCnt=" + successCnt);
		if (successCnt > 1) {
			return 2;
		}

		if (StringUtils.equals(status, userApp.getSuccessStatus()) && successCnt == 1) {// 和第三方兼容
			UserScore userScore = new UserScore();
			userScore.setScore(userApp.getScore());
			userScore.setUserId(userApp.getUserId());
			userScore.setType(ScoreType.TryPlayScore);
			userScore.setObjectId(userApp.getId());
			userScoreService.insert(userScore);
			try {
				weixinService.sendWeiXinMessage(user.getOpenId(), "您试用『" + userApp.getAppName() + "』奖金"
						+ MoneyUtil.changeF2Y(new Long(userApp.getScore())) + "元");
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			Integer myScore = tryApp.getParentScore();
			insertParentScore(userApp, userApp.getUserId(), myScore);
		}
		checkAppCtr(tryApp);//控制数量
		return 3;
	}

	private void checkAppCtr(TryApp tryApp) {
		AppCtr appCtr = appCtrService.getLastAppCtr(tryApp.getId());
		if (appCtr != null) {
			long count = userAppMapper.querySuccessAppCount(appCtr);
			if (count >= appCtr.getNum()) {
				TryApp updateTryApp = new TryApp();
				updateTryApp.setStatus("0");
				updateTryApp.setId(tryApp.getId());
				tryAppService.updateTryApp(updateTryApp);
			}
		}
	}

	@Override
	public void insertUserAppAndCallCp(UserApp userApp) {
		TryApp tryApp = tryAppService.queryMyTryAppById(userApp.getAppId());
		if (StringUtils.equals(tryApp.getTaskType(), "1")) {
			User user = userService.load(userApp.getUserId());// 查询同一手机上的其他用户
			if (StringUtils.isBlank(user.getUuid())) {
				throw new BizException(GlobalErrorCode.INTERNAL_ERROR, "请下载钥匙并且微信认证");
			}
			String pageUid = UUID.randomUUID().toString();
			userApp.setPageUid(pageUid);
			userApp.setUuid(user.getUuid());
			String callBackUrl = new StringBuilder(tryApp.getCallBackUrl()).append("?flag=").append(pageUid)
					.append("&idfa=").append(user.getUuid()).append("&status=").append("&app=").toString();
			try {
				callBackUrl = URLEncoder.encode(callBackUrl, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			String clickUrl = new StringBuilder(tryApp.getClickCallUrl()).append("?app=").append(tryApp.getAppAppId())
					.append("&appid=").append(tryApp.getAppAppId()).append("&source=KUMI").append("&os=")
					.append("&data=").append("&extendtype=2").append("&deviceid=idfa").append("&idfa=")
					.append(user.getUuid()).append("&flag=").append(pageUid).append("&callbackurl=").append(callBackUrl)
					.append("&callback=").append(callBackUrl).toString();
			this.insert(userApp);
			try {
				String result = restTemplate.getForObject(clickUrl, String.class);
				log.info(result);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		} else {
			this.insert(userApp);
		}

	}

	@Override
	public List<UserApp> queryUserAppListByIdfa(UserApp userApp) {
		List<UserApp> list = userAppMapper.selectByUuid(userApp);
		return list;
	}

	@Override
	public int updateWpUserAppStatus(UserApp userApp) {
		TryApp tryApp = tryAppService.queryMyTryAppById(userApp.getAppId());
		Long count = countAddCache.increase(tryApp.getId()+"");
		if (count == null)
			count = 0L;
		ClickLog clickLog = new ClickLog();
		clickLog.setAppid(tryApp.getId());
		clickLog.setIdfa(userApp.getUuid());
		List<ClickLog> list = clickLogService.queryClickLogByIdfa(clickLog);
		if (list == null || list.isEmpty()) {
			return 0;
		}
		ClickLog updateClickLog = list.get(0);
		String status = "0";
		try {
			if ((count % 100 < 95 && count%100 >= 90) || (count % 100 < 57 && count%100 >= 47)) {
				log.info("not callback..");
			} else {
				String result = restTemplate.getForObject(updateClickLog.getCallbackurl(), String.class);
				status = "1";
				log.info(result);
			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		/*try {
			String result = restTemplate.getForObject(updateClickLog.getCallbackurl(), String.class);
			log.info(result);
		} catch (Exception e) {
			log.error(e.getMessage());
		}*/
		updateClickLog.setStatus(status);
		clickLogService.updateClickLog(updateClickLog);
		userApp.setStatus(tryApp.getSuccessStatus());
		int l = userAppMapper.updateCpUserApp(userApp);
		return l;
	}

	@Override
	public UserApp load(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addUserSocore(String openId, Long userId, int score, String message) {
		UserScore userScore = new UserScore();
		userScore.setScore(score);
		userScore.setUserId(userId);
		userScore.setType(ScoreType.SUBSCRIBE);
		userScore.setObjectId(userId);
		userScoreService.insert(userScore);
		try {
			weixinService.sendWeiXinMessage(openId, message);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

}
