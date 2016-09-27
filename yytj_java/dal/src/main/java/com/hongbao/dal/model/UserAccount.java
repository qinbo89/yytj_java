package com.hongbao.dal.model;

import com.hongbao.dal.Archivable;
import com.hongbao.dal.BaseEntityImpl;
import com.hongbao.dal.enums.AccountType;

/**
 * 用户账号
 * 
 *
 */
public class UserAccount extends BaseEntityImpl implements Archivable {
	private static final long serialVersionUID = 320635436438925203L;
	private Boolean archive;
	/** 用户编号 */
	private Long userId;
	/** 账号类型 */
	private AccountType type;
	/** 账号 */
	private String accountNo;
	/** 账号实名 */
	private String realName;

	public Boolean getArchive() {
		return archive;
	}

	public void setArchive(Boolean archive) {
		this.archive = archive;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public AccountType getType() {
		return type;
	}

	public void setType(AccountType type) {
		this.type = type;
	}
}
