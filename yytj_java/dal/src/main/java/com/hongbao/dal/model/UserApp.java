package com.hongbao.dal.model;

import java.math.BigDecimal;

import com.hongbao.dal.Archivable;
import com.hongbao.dal.BaseEntityImpl;



/**
 * 用户试玩记录
 * 
 */
public class UserApp extends BaseEntityImpl implements Archivable {
	private static final long serialVersionUID = 5030846321858072896L;
	/** 用户编号 */
	private Long userId;
	/** 试玩 APP编号 */
	private Long appId;
	/** 试玩APP的名称 */
	private String appName;
	/** 试玩APP图标 */
	private String appImg;

	private String projectName;

	private String packageName;

	private String schema;

	private String keyWord;

	private Integer tryTime;

	private String status;

	private String successStatus;

	private Integer score;

	private String appAppId;

	private Boolean archive;

	private Long taskId;
	
	private String channel;
	
	private String uuid;
	
	private String pageUid;


	public String getPrice() {
		if (score == null) {
			return "0";
		}
		return BigDecimal.valueOf(Long.valueOf(score))
				.divide(new BigDecimal(100)).toString();
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the appId
	 */
	public Long getAppId() {
		return appId;
	}

	/**
	 * @param appId
	 *            the appId to set
	 */
	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppImg() {
		return appImg;
	}

	public void setAppImg(String appImg) {
		this.appImg = appImg;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public Integer getTryTime() {
		return tryTime;
	}

	public void setTryTime(Integer tryTime) {
		this.tryTime = tryTime;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the successStatus
	 */
	public String getSuccessStatus() {
		return successStatus;
	}

	/**
	 * @param successStatus
	 *            the successStatus to set
	 */
	public void setSuccessStatus(String successStatus) {
		this.successStatus = successStatus;
	}

	@Override
	public Boolean getArchive() {
		// TODO Auto-generated method stub
		return archive;
	}

	@Override
	public void setArchive(Boolean archive) {
		// TODO Auto-generated method stub
		this.archive = archive;
	}

	public String getAppAppId() {
		return appAppId;
	}

	public void setAppAppId(String appAppId) {
		this.appAppId = appAppId;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getPageUid() {
		return pageUid;
	}

	public void setPageUid(String pageUid) {
		this.pageUid = pageUid;
	}

	
	
	
	
	
	
	
	

}
