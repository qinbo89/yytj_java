package com.hongbao.dal.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hongbao.dal.model.ScoreType;
import com.hongbao.dal.model.UserScore;
import com.hongbao.dal.model.UserScoreTopTen;

public interface UserScoreMapper {
	public int insert(Map<String, Object> map);

	public long getTotalScoreByUserId(@Param("userId") Long userId, @Param("tableName") String tableName);

	public long getDeductedScoreByUserId(@Param("userId") Long userId, @Param("tableName") String tableName);

	public UserScore getById(@Param("id") Long id, @Param("tableName") String tableName);

	public int updateFrozenToCashApplyById(@Param("id") Long id, @Param("tableName") String tableName);

	public long getScoreByUserIdScoreType(@Param("userId") Long userId, @Param("scoreType") ScoreType scoreType,
			@Param("tableName") String tableName);

	public List<UserScoreTopTen> getEveryTopTen(@Param("tableName") String tableName);
	
	/**
	 * 查询今天的赚金
	 * @return
	 */
	long getTodayScoreByUserId(@Param("userId") Long userId, @Param("tableName") String tableName);

	/**
	 * 获取明细
	 * @param queryMap
	 * @return
	 */
	List<UserScore> selectList(Map<String,Object> queryMap);

}
