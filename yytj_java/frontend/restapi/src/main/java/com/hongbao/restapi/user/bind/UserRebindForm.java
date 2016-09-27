package com.hongbao.restapi.user.bind;

public class UserRebindForm {
	/** 用户编号 */
	private String userId;
	/** 用户新手机号码 */
	private String newPhone;
	private String uuid;
	/** 用户新手机号码 */
	private String newSmsCode;
	/** 用户发送旧的手机号码进行验证码校验后返回的一个根据用户编号生成的随机数 */
	private String oldRandomNum;

	public String getOldRandomNum() {
		return oldRandomNum;
	}

	public void setOldRandomNum(String oldRandomNum) {
		this.oldRandomNum = oldRandomNum;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getNewSmsCode() {
		return newSmsCode;
	}

	public void setNewSmsCode(String newSmsCode) {
		this.newSmsCode = newSmsCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNewPhone() {
		return newPhone;
	}

	public void setNewPhone(String newPhone) {
		this.newPhone = newPhone;
	}

}
