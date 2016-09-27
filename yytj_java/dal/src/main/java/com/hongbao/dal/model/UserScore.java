package com.hongbao.dal.model;

import com.hongbao.dal.Archivable;
import com.hongbao.dal.BaseEntityImpl;

/**
 * 用户得分记录表
 * 
 *
 */
public class UserScore extends BaseEntityImpl implements Archivable {

	private static final long serialVersionUID = 7868742379381143012L;

	/** 得分类型 */
	private ScoreType type;
	/** 用户编号 */
	private Long userId;
	/** 用户得分OR扣分 */
	private Integer score;
	/** 放置其他表的外键，如徒弟编号 */
	private Long objectId;
	private Boolean archive;

	public Boolean getArchive() {
		return archive;
	}

	public void setArchive(Boolean archive) {
		this.archive = archive;
	}

	public ScoreType getType() {
		return type;
	}

	public void setType(ScoreType type) {
		this.type = type;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

}
