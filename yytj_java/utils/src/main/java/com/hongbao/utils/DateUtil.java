package com.hongbao.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;

public class DateUtil {
	public static final int MINITUE_SECONDS = 60;
	public static final int MINITUE_MILLIS = MINITUE_SECONDS * 1000;
	
	public static final int MINITUE_5_SECONDS = 60 * 5;
	public static final int MINITUE_5_MILLIS  = MINITUE_5_SECONDS * 1000;
	
	public static final int HOUR_SECONDS = MINITUE_SECONDS * 60;
	public static final int HOUR_MILLIS = HOUR_SECONDS * 1000;
	
	public static final int DAY_SECONDS = HOUR_SECONDS * 24;
	public static final int DAY_MILLIS  =DAY_SECONDS * 1000;
	
	public static final int MONTH_SECONDS = DAY_SECONDS * 30;
	public static final int MONTH_MILLIS  = MONTH_SECONDS * 1000;

	public static String getFmtYMDNoSymbol(long timeMillis) {
		return new SimpleDateFormat("yyyyMMdd").format(new Date(timeMillis));
	}

	public static String getFmtyMdHmNoSymbol(long timeMillis) {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(timeMillis));
	}

	public static String getFmtyMdHmsSSSNoSymbol(long timeMillis) {
		return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date(timeMillis));
	}

	public static String getFmtYMDHMS(long timeMillis) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timeMillis));
	}
	public static String getFmtYMDHM(long timeMillis) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(timeMillis));
	}

	/**
	 * 
	 * @param dt
	 * @param day
	 * @return
	 */
	public static Date getDateAfter(Date dt, int day) {
		Calendar curr = Calendar.getInstance();
		curr.setTime(dt);
		curr.add(Calendar.DATE, day);
		return curr.getTime();
	}
    /**
     * 
     * @param dt
     * @param day
     * @return
     */
    public static Date getMonthAfter(Date dt, int day) {
        Calendar curr = Calendar.getInstance();
        curr.setTime(dt);
        curr.add(Calendar.MONTH, day);
        return curr.getTime();
    }
	/**
	 * 
	 * @param dt
	 * @param day
	 * @return
	 */
	public static boolean isDateAfter(Date dt, int day) {
		Date copDate = DateUtil.getDateAfter(dt, day);
		if (copDate.before(new Date())) {
			return false;
		}
		return true;

	}

	public static String remainTime(Date beginTime, Date endTime) {
		if (beginTime == null || endTime == null) {
			return null;
		}
		long seconds = (endTime.getTime() - beginTime.getTime()) / 1000;
		if ((seconds / DAY_SECONDS) > 0) {
			return seconds / DAY_SECONDS + "天";
		}
		if ((seconds / HOUR_SECONDS) > 0) {
			return seconds / HOUR_SECONDS + "小时";
		}
		if ((seconds / MINITUE_SECONDS) > 0) {
			return seconds / MINITUE_SECONDS + "分钟";
		}
		return null;
	}

	public static void main(String[] args) {
			System.out.println(new Date(1437521526000L).getMinutes());
		
		Calendar curr = Calendar.getInstance();
		curr.set(2015, 6, 7, 0, 1, 1);
		System.out.println(DateUtil.isDateAfter(curr.getTime(),1));
		
	
		
		System.out.println(getDateAfter(new Date(),1));
		System.out.println(DigestUtils.md5Hex("type=1&uid=1166&appid=5091&wechatid=oI2NQt7shnylutzgWbBKD4fvUGhQ&appuserid=c9z5dcgn&key=DFAEADFDSAFSFEADFDSFA"));
	}

}
