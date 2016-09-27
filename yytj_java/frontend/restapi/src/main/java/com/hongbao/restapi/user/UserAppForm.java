/**
 * 
 */
package com.hongbao.restapi.user;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Bes
 *
 */
public class UserAppForm {
	private static final String NOT_BLANK_MESSAGE = "{valid.notBlank.message}";
	@NotBlank(message = NOT_BLANK_MESSAGE)
	private String activityId;
	
	private List<String> appStatus ;

	/**
	 * @return the activityId
	 */
	public String getActivityId() {
		return activityId;
	}

	/**
	 * @param activityId the activityId to set
	 */
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	/**
	 * @return the appStatus
	 */
	public List<String> getAppStatus() {
		return appStatus;
	}

	/**
	 * @param appStatus the appStatus to set
	 */
	public void setAppStatus(List<String> appStatus) {
		this.appStatus = appStatus;
	}


}
