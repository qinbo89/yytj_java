package com.hongbao.utils;

public class GeoUtil {
	

	/**
	 * 度数转为了米
	 * @param degrees
	 * @return
	 */
	public static double degreeToMeter(double degrees){
		return degrees * 111.1951*1000 ;
	}
	
	/**
	 * 度数转为了米
	 * @param degrees
	 * @return
	 */
	public static double meterToDegree(double  meter){
		return  meter / (111.1951*1000) ;
	}

}
