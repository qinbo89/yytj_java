package com.hongbao.service.score;


import java.util.List;

import com.hongbao.dal.GlobalResult;
import com.hongbao.dal.model.ScoreType;
import com.hongbao.dal.model.UserScore;
import com.hongbao.dal.model.UserScoreTopTen;
import com.hongbao.dal.query.UserScoreQuery;


public interface UserScoreService {

	public UserScore getByIdAndUserId(Long id, Long userId);

	public long getTotalScoreByUserId(Long userId);
	
	public long getTodayScoreByUserId(Long userId);

	/**
	 * 获取用户收支明细
	 * @param query
	 * @return
	 */
	List<UserScore> selectListByUserId(UserScoreQuery query);

	public long getDeductedScoreByUserId(Long userId);

	public long getCashApplyScoreByUserId(Long userId);

	public long getScoreByUserIdScoreType(Long userId, ScoreType scoreType);

	public GlobalResult insert(UserScore userScore);

	public int updateFrozenToCashApplyByIdAndUserId(Long id, Long userId);
	
	public List<UserScoreTopTen> getTopTen();

}
