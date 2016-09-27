package com.hongbao.dal.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 账户类型
 * 
 *
 */
public enum AccountType {
	/** 支付宝 */
	ALIPAY, WEIPAY;

	public static  AccountType getAccountType(String name){
		for(AccountType type:AccountType.values()){
			if(StringUtils.equals(type.name(), name)){
				return type;
			}
		}
		return null;
		
	}
}
