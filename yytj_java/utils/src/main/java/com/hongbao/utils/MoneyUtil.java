package com.hongbao.utils;

import java.math.BigDecimal;

public class MoneyUtil {

	public static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+";

	/**
	 * 将分为单位的转换为元 （除100）
	 * 
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	public static String changeF2Y(Long amount) {

		return BigDecimal.valueOf(amount).divide(new BigDecimal(100))
				.toString();
	}
}
