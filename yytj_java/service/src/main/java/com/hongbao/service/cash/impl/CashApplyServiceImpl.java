package com.hongbao.service.cash.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hongbao.dal.query.CashApplyQuery;
import com.hongbao.utils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hongbao.dal.GlobalResult;
import com.hongbao.dal.enums.AccountApplyType;
import com.hongbao.dal.enums.ApplyStatus;
import com.hongbao.dal.mapper.CashApplyMapper;
import com.hongbao.dal.model.CashApply;
import com.hongbao.dal.model.ScoreType;
import com.hongbao.dal.model.User;
import com.hongbao.dal.model.UserScore;
import com.hongbao.service.UserService;
import com.hongbao.service.cash.CashApplyService;
import com.hongbao.service.score.UserScoreService;
import com.hongbao.vo.PageVO;


@Service("cashApplyService")
public class CashApplyServiceImpl implements CashApplyService {
	private static Logger logger = LoggerFactory.getLogger(CashApplyServiceImpl.class);
	@Autowired
	private CashApplyMapper cashApplyMapper;
	@Autowired
	private UserService userService;
	@Autowired
	private UserScoreService userScoreService;

	@Override
	@Transactional
	public GlobalResult insertCashApply(CashApply cashApply) {
		if (cashApply.getUserId()==null) {
			// 判断可用积分是否够申请，在客户端和服务端都进行验证
			Long userId = cashApply.getUserId();
			User user = userService.getCurrentUser();
			long totalScore = userScoreService.getTotalScoreByUserId(userId);
			long deductedScore = userScoreService.getDeductedScoreByUserId(userId);
			BigDecimal cash = cashApply.getCash();

			long cashScore = cash.multiply(new BigDecimal(100)).longValue();

			if (cashScore <= totalScore - deductedScore) {
				if (cashApply.getType() == AccountApplyType.ALIPAY) {
					// 在积分表中插入一条冻结记录
					UserScore userScore = new UserScore();
					int frozenScore = cashApply.getCash().multiply(new BigDecimal(100)).intValue();
					userScore.setScore(frozenScore);
					userScore.setType(ScoreType.FrozenScore);
					userScore.setUserId(userId);
//					userScore.setObjectId(cashApply.getId());
					GlobalResult gr=userScoreService.insert(userScore);//插入不成功 就返回
					if(GlobalResult.success!=gr){
						return GlobalResult.error;
					}
					// 插入提现记录
					String userScoreId = userScore.getId()+"";
					cashApply.setObjectId(userScoreId);
					int rows = cashApplyMapper.insert(cashApply);
					if (rows > 0) {
						//修改userScore object_id  TODO tangshengshan
//						userScore.setObjectId(cashApply.getId());
//						userScoreService.
						return GlobalResult.success;
					} else {
						logger.error("新增提现申请出现错误");
						throw new RuntimeException("新增提现申请出现错误");
					}
				} else {
					// 在积分表中插入一条冻结记录
					UserScore userScore = new UserScore();
					int frozenScore = cashApply.getCash().multiply(new BigDecimal(100)).intValue();
					userScore.setScore(frozenScore);
					userScore.setType(ScoreType.CashApplyScore);
					userScore.setUserId(userId);
					GlobalResult gr=userScoreService.insert(userScore);
					if(GlobalResult.success!=gr){
						return GlobalResult.error;
					}
					
					// 插入提现记录
					String userScoreId = userScore.getId()+"";
					cashApply.setObjectId(userScoreId);
					cashApply.setStatus(ApplyStatus.SUCCESS);
					int rows = cashApplyMapper.insert(cashApply);
					if (rows > 0) {
						return GlobalResult.success;
					} else {
						logger.error("新增提现申请出现错误");
						return GlobalResult.error;
					}

				}

			} else {
				return GlobalResult.notEnough;
			}

		}
		return GlobalResult.nullValue;
	}

	@Override
	public CashApply getById(Long id) {
		return cashApplyMapper.selectById(id);
	}

	@Override
	public List<CashApply> getCashApplyListByUserId(Long userId) {
		return cashApplyMapper.getCashApplyListByUserId(userId);
	}

	/**
	 * 审核添加事务控制
	 */
	@Transactional
	@Override
	public GlobalResult cashApplyAudit(CashApply cashApply) {
		User auditUser = userService.getCurrentUser();
		cashApply.setAuditId(auditUser.getId()+"");
		cashApply.setAuditLoginName(auditUser.getLoginname());
		// 获取到冰冻记录
		Long scoreId = new Long(cashApply.getObjectId());
		Long userId = cashApply.getUserId();
		UserScore userScore = userScoreService.getByIdAndUserId(scoreId, userId);
		// 审核的时候检查冻结积分，将冻结积分清除，修改状态为提现
		if (userScore != null) {
			int rows = userScoreService.updateFrozenToCashApplyByIdAndUserId(scoreId, userId);
			if (rows > 0) {
				cashApply.setReason("同意");
				cashApply.setStatus(ApplyStatus.SUCCESS);
				cashApplyMapper.updateAuditInfo(cashApply);
				logger.info("审核成功");
				// TODO 对应的师父加分
				return GlobalResult.success;
			} else {
				logger.error("审核出错，冻结状态更新为提现状态出错!!!");
				return GlobalResult.error;
			}
		} else {
			cashApply.setReason("冻结积分里没有这条记录");
			cashApply.setStatus(ApplyStatus.FAIL);
			cashApplyMapper.updateAuditInfo(cashApply);
			logger.error("审核出错，冻结积分里没有这条记录!!!");
			return GlobalResult.error;
		}

	}

	@Override
	public List<CashApply> getCashApplyListByUserIdAndStatus(Long userId, ApplyStatus applyStatus) {
		return cashApplyMapper.getCashApplyListByUserIdAndStatus(userId, applyStatus);
	}

	@Override
	public CashApply getCashApplyListByUserIdNew(Long userId) {
		
		return cashApplyMapper.getCashApplyListByUserIdNew(userId);
	}

	@Override
	public PageVO<CashApply> getCashApplyList(int page, int rows, Map<String, Object> queryParam) {
		PageVO<CashApply> pageVO = new PageVO<CashApply>();
		pageVO.setPage(page);

		Integer count = cashApplyMapper.getCashApplyListCount(queryParam);
		if (count == null || count == 0) {
			pageVO.setTotal(0);
			pageVO.setRows(new ArrayList<CashApply>());
		}
		pageVO.setTotal(count);
		page = (page <= 1) ? 1 : page;
		Pageable pageable = new PageRequest(page - 1, rows);

		List<CashApply> list = cashApplyMapper.getCashApplyList(pageable, queryParam);
		pageVO.setRows(list);
		return pageVO;
	}

	@Override
	public GlobalResult updateApplyStatus(CashApply cashApply) {
		User auditUser = userService.getCurrentUser();
		cashApply.setAuditId(auditUser.getId()+"");
		cashApply.setAuditLoginName(auditUser.getLoginname());
		int rows = cashApplyMapper.updateAuditInfo(cashApply);
		return rows > 0 ? GlobalResult.success : GlobalResult.error;
	}

    @Override
    public Integer getCashApplyListCount(Map queryParam) {
        return cashApplyMapper.getCashApplyListCount(queryParam);
    }

	@Override
	public List<CashApply> selectListByUserId(CashApplyQuery query) {
		Map<String,Object> queryMap = BeanUtils.beanToMap(query);
		return cashApplyMapper.selectList(queryMap);
	}

}
