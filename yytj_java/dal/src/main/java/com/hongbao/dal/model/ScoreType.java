package com.hongbao.dal.model;

public enum ScoreType {
	
	/** 首次关注 */
	SUBSCRIBE,
	
	/** 下载试玩加分 */
	TryPlayScore,
	/** 徒弟提现加分 */
	ApprenticeScore,
	/** 提现扣分 */
	CashApplyScore,
	/** 提现冻结积分 */
	FrozenScore,
	/** 充值 */
	CashIn,
	/** 充值冻结 */
	CashInTmp;
	public String getDescriptionByType(ScoreType soreType) {
		if (soreType != null) {
			switch (soreType) {
			case SUBSCRIBE:
				return "首次登录红包";
			case TryPlayScore:
				return "试玩收入";
			case ApprenticeScore:
				return "邀请收入";
			case CashApplyScore:
				return "提现扣分";
			case FrozenScore:
				return "提现冻结";
			default:
				return "未知异常";
			}

		}
		return "";
	}
}
