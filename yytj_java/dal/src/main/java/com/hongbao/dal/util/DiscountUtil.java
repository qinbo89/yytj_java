package com.hongbao.dal.util;

public class DiscountUtil {

	public static int promotionToDiscount(Integer promotion) {
		if (promotion == null) {
			promotion = 0;
		}
		int n= promotion - 2  ;
		if(n<0){
			return 100;
		}
       return 100-n;
	}
	

	public static int promotionToDiscountNotHb(Integer promotion) {
		if (promotion == null) {
			promotion = 0;
		}
		int n = promotion - 2;
		if(n<0){
			return 100;
		}
		
		return 100-n;

	}

}
