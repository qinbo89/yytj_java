package com.hongbao.dal;


import java.util.ArrayList;
import java.util.List;

/**
 * 全局结果定义，可以根据业务继续在此添加
 * 
 * @author 于东伟
 *
 */
public enum GlobalResult {
	/** 用户未登录 */
	notLogin("notLogin", "用户未登录"),
	/** 操作成功 */
	success("success", "操作成功"),
	/** 重复提交 */
	repeat("repeat", "重复提交"),
	/** 出现错误 */
	error("error", "出现错误"),
	/** 参数为空 */
	nullValue("nullValue", "参数为空"),
	/** 余额不足 */
	notEnough("notEnough", "余额不足"),
	
	weipaycashpf("weipaycashpf", "提现过于频繁");

	private String code;
	private String message;

	private GlobalResult(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public static String[] getCodes() {
		List<String> enums = new ArrayList<String>();
		for (GlobalResult s : GlobalResult.values()) {
			enums.add(s.getCode());
		}

		return enums.toArray(new String[] {});
	}

	public static GlobalResult getByCode(String code) {
		for (GlobalResult s : GlobalResult.values()) {
			if ((s.getCode()).equals(code)) {
				return s;
			}
		}
		return null;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

