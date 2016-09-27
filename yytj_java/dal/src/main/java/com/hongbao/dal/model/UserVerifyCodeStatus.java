package com.hongbao.dal.model;

/**
 * 用户验证码状态
 * 
 * @author 于东伟
 *
 */
public enum UserVerifyCodeStatus {
	/** 成功 */
	success,
	/** 重复 */
	repeat,
	/** 服务端错误 */
	error
}