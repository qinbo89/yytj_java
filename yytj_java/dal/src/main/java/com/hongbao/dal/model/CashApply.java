package com.hongbao.dal.model;

import java.math.BigDecimal;

import com.hongbao.dal.Archivable;
import com.hongbao.dal.BaseEntityImpl;
import com.hongbao.dal.enums.AccountApplyType;
import com.hongbao.dal.enums.ApplyStatus;



/**
 * 提现申请
 * 
 * @author 
 *
 */
public class CashApply extends BaseEntityImpl implements Archivable {

	private static final long serialVersionUID = 6086548030469919642L;
	/** 提现金额 */
	private BigDecimal cash;
	/** 提现用户 */
	private Long userId;
	/** 提现类型 */
	private AccountApplyType type;
	/** 提现账号 */
	private String accountNo;
	/** 提现用户实名 */
	private String realName;
	/** 状态 */
	private ApplyStatus status;
	/** 审核人编号 */
	private String auditId;
	/** 审核人登录名称 */
	private String auditLoginName;
	/** 审核是否通过给出的理由 */
	private String reason;
	/** 用户申请提现，积分冻结，积分表的编号放进去,其他相关的业务对应的ID都可以根据不同的业务放进来 */
	private String objectId;
	private Boolean archive;

	public BigDecimal getCash() {
		return cash;
	}

	public void setCash(BigDecimal cash) {
		this.cash = cash;
	}

	

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public ApplyStatus getStatus() {
		return status;
	}

	public void setStatus(ApplyStatus status) {
		this.status = status;
	}

	public String getAuditId() {
		return auditId;
	}

	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}

	public String getAuditLoginName() {
		return auditLoginName;
	}

	public void setAuditLoginName(String auditLoginName) {
		this.auditLoginName = auditLoginName;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Boolean getArchive() {
		return archive;
	}

	public void setArchive(Boolean archive) {
		this.archive = archive;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

}
