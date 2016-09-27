package com.hongbao.dal.mapper;

import java.util.List;
import java.util.Map;

import com.hongbao.dal.model.UserScore;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import com.hongbao.dal.enums.ApplyStatus;
import com.hongbao.dal.model.CashApply;




public interface CashApplyMapper {

	public int insert(CashApply cashApply);

	public CashApply selectById(@Param("id") Long id);

	public List<CashApply> getCashApplyListByUserId(
			@Param("userId") Long userId);

	public List<CashApply> getCashApplyListByUserIdAndStatus(
			@Param("userId") Long userId,
			@Param("applyStatus") ApplyStatus applyStatus);
	
	CashApply getCashApplyListByUserIdNew(@Param("userId") Long userId);

	public int updateAuditInfo(CashApply cashApply);

	public Integer getCashApplyListCount(@Param("map") Map<String, Object> map);

	public List<CashApply> getCashApplyList(@Param("page") Pageable pageable,
			@Param("map") Map<String, Object> map);

	/**
	 * 提现明细
	 * @param queryMap
	 * @return
	 */
	List<CashApply> selectList(Map<String,Object> queryMap);
}
