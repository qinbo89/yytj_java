package com.hongbao.service.user.vo;

/**
 * 类InvitedVO.java的实现描述：TODO 类实现描述
 * 
 * @author tatos 2014年8月28日 上午10:58:10
 */
public class InvitedVO {

	private String code;

	public enum invitedVOEnum {
		SUCCESS, BLANK, EROOR
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

}
