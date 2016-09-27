package com.hongbao.dal.model;

import com.hongbao.dal.Archivable;
import com.hongbao.dal.BaseEntityImpl;
import com.hongbao.dal.enums.AccountApplyType;

public class CashConfigure extends BaseEntityImpl implements Archivable {

	private static final long serialVersionUID = -2886888476017029028L;
	/** 提现金额 */
	private Integer money;
	/** 提现需要扣除的积分 */
	private Integer score;
	/** 提现类型 */
	private AccountApplyType applyType;
	/** 展示还是隐藏 */
	private ShowOrHide status;
	private Boolean archive;

	public Boolean getArchive() {
		return archive;
	}

	public void setArchive(Boolean archive) {
		this.archive = archive;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public AccountApplyType getApplyType() {
		return applyType;
	}

	public void setApplyType(AccountApplyType applyType) {
		this.applyType = applyType;
	}

	public ShowOrHide getStatus() {
		return status;
	}

	public void setStatus(ShowOrHide status) {
		this.status = status;
	}
}
