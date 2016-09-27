package com.hongbao.web.controller.cash;


import com.hongbao.dal.enums.AccountApplyType;
import com.hongbao.dal.enums.AccountType;

import java.math.BigDecimal;

public class CashApplyVO {
	/** 提现账户编号 */
	private Long accountId;
	/** 提现金额 */
	private BigDecimal cash;
	/** 提现类型 */
	private AccountApplyType type;
	/** 提现账号 */
	private String accountNo;
	/** 提现用户实名 */
	private String realName;

	public BigDecimal getCash() {
		return cash;
	}

	public void setCash(BigDecimal cash) {
		this.cash = cash;
	}

	public AccountApplyType getType() {
		return type;
	}

	public void setType(AccountApplyType type) {
		this.type = type;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

}
