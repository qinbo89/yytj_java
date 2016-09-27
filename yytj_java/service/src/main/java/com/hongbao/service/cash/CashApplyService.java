package com.hongbao.service.cash;

import java.util.List;
import java.util.Map;

import com.hongbao.dal.GlobalResult;
import com.hongbao.dal.enums.ApplyStatus;
import com.hongbao.dal.model.CashApply;
import com.hongbao.dal.query.CashApplyQuery;
import com.hongbao.vo.PageVO;


public interface CashApplyService {
	/**
	 * 新增提现申请
	 * 
	 * @param cashApply
	 * @return
	 */
	public GlobalResult insertCashApply(CashApply cashApply);

	/**
	 * 根据编号获取一条记录
	 * 
	 * @param id
	 * @return
	 */
	public CashApply getById(Long id);

	/**
	 * 根据用户编号查询对应的提现申请列表
	 * 
	 * @param userId
	 * @return
	 */
	public List<CashApply> getCashApplyListByUserId(Long userId);

	/**
	 * 根据用户编号和对应的状态查询列表
	 * 
	 * @param userId
	 * @param applyStatus
	 * @return
	 */
	public List<CashApply> getCashApplyListByUserIdAndStatus(Long userId,
			ApplyStatus applyStatus);

	/**
	 * 提现审核
	 * 
	 * @param cashApply
	 * @return
	 */
	public GlobalResult cashApplyAudit(CashApply cashApply);
	
	
	/**
	 * 取出用户最新的一条记录
	 * @param userId
	 * @return
	 */
	CashApply getCashApplyListByUserIdNew( Long userId);

	public PageVO<CashApply> getCashApplyList(int page, int rows,
			Map<String, Object> queryParam);

	public GlobalResult updateApplyStatus(CashApply cashApply);

    public Integer getCashApplyListCount(Map queryParam);

	List<CashApply> selectListByUserId(CashApplyQuery query);

}
