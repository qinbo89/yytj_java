package com.hongbao.service.score.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import com.hongbao.dal.query.UserScoreQuery;
import com.hongbao.utils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hongbao.dal.GlobalResult;
import com.hongbao.dal.mapper.UserScoreMapper;
import com.hongbao.dal.model.ScoreType;
import com.hongbao.dal.model.User;
import com.hongbao.dal.model.UserScore;
import com.hongbao.dal.model.UserScoreTopTen;
import com.hongbao.dal.redis.JedisUtil;
import com.hongbao.service.UserService;
import com.hongbao.service.score.UserScoreService;

@Service("userScoreService")
public class UserScoreServiceImpl implements UserScoreService {
	private static Logger logger = LoggerFactory.getLogger(UserScoreServiceImpl.class);
	private static final int TABLE_SIZE = 16;
	/** 初始化线程池，避免不必要的线程开销 */
	private static Executor executor = new ScheduledThreadPoolExecutor(TABLE_SIZE);
	@Autowired
	private UserScoreMapper userScoreMapper;
	@Autowired
	private UserService userService;

	@Value("${profiles.active}")
	private String profileActive;
	@Autowired
	private JedisUtil userScoreTopTenCache;
	/** 表名前缀 */
	private static final String SCORE_TABLE_PREFIX = "vdlm_user_score";

	/**
	 * 根据用户ID进行分表处理
	 * 
	 * @param userId
	 * @return
	 */
	private static String getTableName(Long userId) {
		return SCORE_TABLE_PREFIX + "_" + (userId % TABLE_SIZE);
	}

	@Override
	public long getTotalScoreByUserId(Long userId) {
		String tableName = getTableName(userId);
		return userScoreMapper.getTotalScoreByUserId(userId, tableName);
	}

	@Override
	public long getDeductedScoreByUserId(Long userId) {
		String tableName = getTableName(userId);
		return userScoreMapper.getDeductedScoreByUserId(userId, tableName);
	}

	@Override
	public GlobalResult insert(UserScore userScore) {
		if (userScore != null) {
			String tableName = getTableName(userScore.getUserId());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tableName", tableName);
			map.put("userScore", userScore);
			int rows = userScoreMapper.insert(map);
			if (rows > 0) {
				return GlobalResult.success;
			} else {
				return GlobalResult.error;
			}
		}
		return GlobalResult.nullValue;
	}

	@Override
	public long getCashApplyScoreByUserId(Long userId) {
		return getScoreByUserIdScoreType(userId, ScoreType.CashApplyScore);
	}

	@Override
	public UserScore getByIdAndUserId(Long id, Long userId) {
		String tableName = getTableName(userId);
		return userScoreMapper.getById(id, tableName);
	}

	@Override
	public int updateFrozenToCashApplyByIdAndUserId(Long id, Long userId) {
		String tableName = getTableName(userId);
		return userScoreMapper.updateFrozenToCashApplyById(id, tableName);
	}

	@Override
	public long getScoreByUserIdScoreType(Long userId, ScoreType scoreType) {
		String tableName = getTableName(userId);
		return userScoreMapper.getScoreByUserIdScoreType(userId, scoreType, tableName);
	}

	@Override
	public List<UserScoreTopTen> getTopTen() {
		// 缓存在测试阶段不使用
		if (StringUtils.equals(profileActive, "prod")) {
			String top_ten_key = "top_ten_key";
			@SuppressWarnings("unchecked")
			List<UserScoreTopTen> topTenCache = (List<UserScoreTopTen>) userScoreTopTenCache.getObj(top_ten_key);
			if (topTenCache != null) {
				return topTenCache;
			} else {
				topTenCache = getTopTenData();
				userScoreTopTenCache.setObj(top_ten_key, topTenCache);
				return topTenCache;
			}
		} else {
			return getTopTenData();
		}
	}

	private List<UserScoreTopTen> getTopTenData() {
		List<UserScoreTopTen> allList = new ArrayList<UserScoreTopTen>();
		List<UserScoreTopTen> resultList = new ArrayList<UserScoreTopTen>();
		// 在每个表里查询top10，然后排序
		CountDownLatch latch = new CountDownLatch(TABLE_SIZE);

		for (int i = 0; i < TABLE_SIZE; i++) {
			String tableName = SCORE_TABLE_PREFIX + "_" + i % TABLE_SIZE;
			executor.execute(new ScoreThread(allList, tableName, latch));
		}
		try {
			latch.await();
		} catch (InterruptedException e) {
			logger.error("线程计算出现错误，错误信息" + e.getMessage());
			return null;
		}
		Collections.sort(allList, new Comparator<UserScoreTopTen>() {
			@Override
			public int compare(UserScoreTopTen o1, UserScoreTopTen o2) {
				return (int) (o1.getTotalScore().longValue() - o2.getTotalScore().longValue());
			}

		});

		if (allList.size() > 10) {
			for (int i = 0; i < 10; i++) {
				resultList.add(allList.get(i));
			}
		} else {
			resultList.addAll(allList);
		}
		int rank = 1;
		// 配置用户信息
		if (resultList.size() > 0) {
			for (UserScoreTopTen oneScore : resultList) {
				User user = userService.load(oneScore.getUserId());
				oneScore.setNickName(user.getNickname());
				oneScore.setUserImg(user.getPicUri());
				oneScore.setRank(rank);
				rank++;
			}
		}

		return resultList;
	}

	class ScoreThread implements Runnable {
		List<UserScoreTopTen> scoreList;
		CountDownLatch latch;
		String tableName;

		public ScoreThread(List<UserScoreTopTen> scoreList, String tableName, CountDownLatch latch) {
			this.scoreList = scoreList;
			this.latch = latch;
			this.tableName = tableName;
		}

		public void run() {
			List<UserScoreTopTen> tmpList = userScoreMapper.getEveryTopTen(tableName);
			scoreList.addAll(tmpList);
			latch.countDown();
		}
	}

	@Override
	public long getTodayScoreByUserId(Long userId) {
		String tableName = getTableName(userId);
		return userScoreMapper.getTodayScoreByUserId(userId, tableName);
	}

	public List<UserScore> selectListByUserId(UserScoreQuery query){
		String tableName = getTableName(query.getUserId());
		query.setTableName(tableName);
		Map<String,Object> queryMap = BeanUtils.beanToMap(query);
		List<UserScore> list = userScoreMapper.selectList(queryMap);
		return list;
	}

}
