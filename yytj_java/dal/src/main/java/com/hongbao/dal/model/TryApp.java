package com.hongbao.dal.model;

import java.math.BigDecimal;

import com.hongbao.dal.Archivable;
import com.hongbao.dal.BaseEntityImpl;



/**
 * 试玩APP
 * 
 * @author ThinkPad
 * 
 */
public class TryApp extends BaseEntityImpl implements Archivable {
	private static final long serialVersionUID = 2560250242912137270L;

	/** APP名称 */
	private String appName;
	/** APP图标 */
	private String appImg;
	/** 对APP图标图片的访问 */
	private String appUrl;

	private String projectName;

	private String packageName;

	private String schema;

	/** 关键字 */
	private String keyWord;

	private String tag;
	/** 描述信息 */
	private String description;
	/** 试玩时间 */
	private Integer tryTime;

	private String successStatus;

	private Integer score;

	private Boolean archive;

	private String status;

	private String appAppId;

	private String price;

	private String isAdmin;

	private String taskType;

	private String clickCallUrl;

	private Integer parentScore;

	private String callBackUrl;
	
	private Integer num;
	
	private Integer appType ;
	
	private String investUrl;
	
	private String siginKey;

	@Override
	public Boolean getArchive() {
		return archive;
	}

	@Override
	public void setArchive(Boolean archive) {
		this.archive = archive;

	}

	/**
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * @param appName
	 *            the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * @return the appImg
	 */
	public String getAppImg() {
		return appImg;
	}

	/**
	 * @param appImg
	 *            the appImg to set
	 */
	public void setAppImg(String appImg) {
		this.appImg = appImg;
	}

	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * @param projectName
	 *            the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * @return the schema
	 */
	public String getSchema() {
		return schema;
	}

	/**
	 * @param schema
	 *            the schema to set
	 */
	public void setSchema(String schema) {
		this.schema = schema;
	}

	/**
	 * @return the keyWord
	 */
	public String getKeyWord() {
		return keyWord;
	}

	/**
	 * @param keyWord
	 *            the keyWord to set
	 */
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	/**
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * @param tag
	 *            the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the tryTime
	 */
	public Integer getTryTime() {
		return tryTime;
	}

	/**
	 * @param tryTime
	 *            the tryTime to set
	 */
	public void setTryTime(Integer tryTime) {
		this.tryTime = tryTime;
	}

	/**
	 * @return the score
	 */
	public Integer getScore() {
		return score;
	}

	/**
	 * @param score
	 *            the score to set
	 */
	public void setScore(Integer score) {
		this.score = score;
	}

	/**
	 * @return the successStatus
	 */
	public String getSuccessStatus() {
		return this.successStatus;
	}

	/**
	 * @param successStatus
	 *            the successStatus to set
	 */
	public void setSuccessStatus(String successStatus) {
		this.successStatus = successStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAppAppId() {
		return appAppId;
	}

	public void setAppAppId(String appAppId) {
		this.appAppId = appAppId;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getPrice() {
		if (score == null) {
			return "0";
		}
		return BigDecimal.valueOf(Long.valueOf(score)).divide(new BigDecimal(100)).toString();
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public String getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getClickCallUrl() {
		return clickCallUrl;
	}

	public void setClickCallUrl(String clickCallUrl) {
		this.clickCallUrl = clickCallUrl;
	}

	public Integer getParentScore() {
		return parentScore;
	}

	public void setParentScore(Integer parentScore) {
		this.parentScore = parentScore;
	}

	public String getCallBackUrl() {
		return callBackUrl;
	}

	public void setCallBackUrl(String callBackUrl) {
		this.callBackUrl = callBackUrl;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getInvestUrl() {
		return investUrl;
	}

	public void setInvestUrl(String investUrl) {
		this.investUrl = investUrl;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Integer getAppType() {
		return appType;
	}

	public void setAppType(Integer appType) {
		this.appType = appType;
	}

	public String getSiginKey() {
		return siginKey;
	}

	public void setSiginKey(String siginKey) {
		this.siginKey = siginKey;
	}

	

}
