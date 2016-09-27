package com.hongbao.web.controller.cash;

import com.hongbao.dal.GlobalResult;
import com.hongbao.dal.base.annotation.NeedLogin;
import com.hongbao.dal.base.controller.ResponseObject;
import com.hongbao.dal.base.exception.BizException;
import com.hongbao.dal.enums.AccountApplyType;
import com.hongbao.dal.enums.AccountType;
import com.hongbao.dal.enums.ApplyStatus;
import com.hongbao.dal.enums.UserType;
import com.hongbao.dal.model.CashApply;
import com.hongbao.dal.model.User;
import com.hongbao.dal.model.UserAccount;
import com.hongbao.dal.query.CashApplyQuery;
import com.hongbao.dal.util.WebResultUtils;
import com.hongbao.service.UserService;
import com.hongbao.service.cash.CashApplyService;
import com.hongbao.service.cash.UserAccountService;
import com.hongbao.service.score.UserScoreService;
import com.hongbao.utils.NumberUtil;
import com.hongbao.web.controller.base.BaseController;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;


@Controller
public class CashApplyWebController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(CashApplyWebController.class);

	@Autowired
	private CashApplyService cashApplyService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private UserScoreService userScoreService;

	@RequestMapping(value = "/innerPage/addUserAccount")
	@NeedLogin(accessUserType = UserType.ALL)
	public String addUserAccount(Model model) {
		User user = userService.getCurrentUser();
		//获取我的余额
		long totalScore = userScoreService.getTotalScoreByUserId(user.getId());  //总分
		long deductedScore = userScoreService.getDeductedScoreByUserId(user.getId());  //扣分和冻结
		long cashApplyScore = totalScore - deductedScore;
		model.addAttribute("cashApplyScore", cashApplyScore);
		return "weixin/addCashInfo";
	}

	@RequestMapping(value = "/innerPage/isApplyBindAccount")
	@ResponseBody
	@NeedLogin(accessUserType = UserType.ALL)
	public boolean isApplyBindAccount(){
		User user = userService.getCurrentUser();
		List<UserAccount> accountList = userAccountService.getAccountListByUserId(user.getId());
		return CollectionUtils.isNotEmpty(accountList);
	}

	@RequestMapping(value = "/innerPage/getMyAccountList")
	@ResponseBody
	@NeedLogin(accessUserType = UserType.ALL)
	public ResponseObject getUserAccountListByUserId() {
		User user = userService.getCurrentUser();
		List<UserAccount> accountList = userAccountService.getAccountListByUserId(user.getId());
		return WebResultUtils.buildResult(accountList);
	}


	@RequestMapping(value = "/innerPage/addUserAccountInfo")
	@ResponseBody
	@NeedLogin(accessUserType = UserType.ALL)
	public ResponseObject addUserAccountInfo(@ModelAttribute CashApplyVO cashApplyVO) {
		//validate
		if(cashApplyVO.getType() == null){
			throw new BizException("提现方式不能为空");
		}
		if( StringUtils.isEmpty(cashApplyVO.getRealName())){
			throw new BizException("真实姓名不能为空");
		}
		if( StringUtils.isEmpty(cashApplyVO.getAccountNo())){
			throw new BizException("认证账号不能为空");
		}

		//add
		User user = userService.getCurrentUser();
		UserAccount userAccount = new UserAccount();
		userAccount.setUserId(user.getId());
		userAccount.setAccountNo(cashApplyVO.getAccountNo());
		userAccount.setRealName(cashApplyVO.getRealName());
		userAccount.setType(AccountType.getAccountType(cashApplyVO.getType().name()));
		GlobalResult result = userAccountService.addUserAccount(userAccount);
		return WebResultUtils.buildResult(result);
	}


	@RequestMapping(value = "/innerPage/doApply")
	@ResponseBody
	@NeedLogin(accessUserType = UserType.ALL)
	public ResponseObject doApply(@ModelAttribute CashApplyVO cashApplyVO) {
		User user = userService.getCurrentUser();
		//校验
		if(!isApplyBindAccount()){
			throw new BizException("未绑定账号");
		}
		//4小时
		Map queryParam = new HashMap();
		queryParam.put("userId", user.getId());
		queryParam.put("startDate", new Date(System.currentTimeMillis() - 4*60*60*1000L));
		Integer count = cashApplyService.getCashApplyListCount(queryParam);
		logger.warn("getCashApplyListCount userId"+user.getId());
		logger.warn("getCashApplyListCount startDate"+new Date(System.currentTimeMillis() - 4*60*60*1000L));
		logger.warn("getCashApplyListCount count"+count);
		if(count >= 2){
			throw new BizException("提现过于频繁");
		}

		//金额
		//获取我的余额
		long totalScore = userScoreService.getTotalScoreByUserId(user.getId());  //总分
		long deductedScore = userScoreService.getDeductedScoreByUserId(user.getId());  //扣分和冻结
		long cashApplyScore = totalScore - deductedScore;
		double doubleCashApplyScore = NumberUtil.longFen2Yuan(cashApplyScore);
		if(doubleCashApplyScore < cashApplyVO.getCash().doubleValue()){
			throw new BizException("提现金额不能大于账户余额");
		}

		//保存提现
		Long accountId = cashApplyVO.getAccountId();
		UserAccount userAccount = userAccountService.getUserAccountById(accountId);
		CashApply cashApply = new CashApply();
		cashApply.setUserId(user.getId());
		cashApply.setStatus(ApplyStatus.APPLY);
		cashApply.setAccountNo(userAccount.getAccountNo());
		cashApply.setRealName(userAccount.getRealName());
		cashApply.setCash(cashApplyVO.getCash());
		cashApply.setType(AccountApplyType.valueOf(userAccount.getType().name()));
		GlobalResult result = cashApplyService.insertCashApply(cashApply);
		return WebResultUtils.buildResult(result);

	}

	@RequestMapping(value = "/innerPage/getMyCashApplyRecords")
	@NeedLogin(accessUserType = UserType.ALL)
	public String getCashApplyListByUserId(CashApplyQuery query, Model model) {
		User user = userService.getCurrentUser();
		query.setUserId(user.getId());
		List<CashApply> list = cashApplyService.selectListByUserId(query);
		model.addAttribute("dataList", list);
		model.addAttribute("query", query);
		if(CollectionUtils.isEmpty(list)){  //返回空页面
			return "weixin/cashRecordEmpty";
		}
		return "weixin/cashRecord";

	}

}
