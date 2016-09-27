package com.hongbao.web.controller.score;

import com.hongbao.utils.MoneyUtil;

public class UserScoreVO {
	private long usableScore;
	private long cashApplyScore;
	private long totalScore;

	private long tryScore;
	private long apprenticeScore;

	public long getUsableScore() {
		return usableScore;
	}

	public String getUsableScoreYuan() {
		return MoneyUtil.changeF2Y(usableScore);
	}

	public void setUsableScore(long usableScore) {
		this.usableScore = usableScore;
	}

	public long getCashApplyScore() {
		return cashApplyScore;
	}

	public String getCashApplyScoreYuan() {
		return MoneyUtil.changeF2Y(cashApplyScore);
	}

	public void setCashApplyScore(long cashApplyScore) {
		this.cashApplyScore = cashApplyScore;
	}

	public long getTotalScore() {
		return totalScore;
	}

	public String getTotalScoreYuan() {
		return MoneyUtil.changeF2Y(totalScore);
	}

	public void setTotalScore(long totalScore) {
		this.totalScore = totalScore;
	}

	public long getTryScore() {
		return tryScore;
	}

	public void setTryScore(long tryScore) {
		this.tryScore = tryScore;
	}

	public String getTryScoreYuan() {
		return MoneyUtil.changeF2Y(tryScore);
	}

	public long getApprenticeScore() {
		return apprenticeScore;
	}

	public String getApprenticeScoreYuan() {
		return MoneyUtil.changeF2Y(apprenticeScore);
	}

	public void setApprenticeScore(long apprenticeScore) {
		this.apprenticeScore = apprenticeScore;
	}

}
