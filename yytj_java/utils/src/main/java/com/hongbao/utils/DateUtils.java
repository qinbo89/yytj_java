package com.hongbao.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 
 * 类DateUtils.java的实现描述：TODO 类实现描述
 * 
 * @author tatos 2014年8月1日 下午1:51:53
 */

public class DateUtils {

	/**
	 * <p>
	 * 存储时间格式类,用这个存储是为了在同一个线程里面不需要反复创建
	 * </p>
	 * 
	 */
	private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>();

	private static String ymdformate = "yyyy-MM-dd";

	private static ThreadLocal<DateFormat> threadLocalymhms = new ThreadLocal<DateFormat>();

	private final static String ymdhms = "yyyy-MM-dd HH:mm:ss";
	private static ThreadLocal<DateFormat> threadLocalymdh = new ThreadLocal<DateFormat>();

	private static String ymdh = "yyyyMMddHH";

	/**
	 * 用于返回指定日期格式的日期增加指定天数的日期
	 * 
	 * @author taiyi
	 */

	public static Date getFutureDayInDays(Date date, int days) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, days);
		return calendar.getTime();
	}

	/**
	 * 计算连续签到天数
	 * 
	 * @author taiyi
	 */
	public static int countContinueOnDutyDay(List<String> onDutyDateList,
			List<String> pastDateList) {
		int continueDay = 0;
		for (int i = 0; i < pastDateList.size(); i++) {
			if (!onDutyDateList.contains(pastDateList.get(i))) {
				return i;
			} else {
				continueDay++;
			}
		}
		return continueDay;
	}

	public static List<String> getPastDateList(int pastDayNumber) {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<String> dateList = new ArrayList<String>();
		for (int pastDay = -1; pastDay > -pastDayNumber; pastDay--) {
			dateList.add(sdf.format(getFutureDayInDays(new Date(), pastDay)));
		}
		return dateList;
	}

	/**
	 * 返回时间
	 * 
	 * @return
	 */
	public static Date getCurrentDateHead() {
		String currday = DateUtils.getDateFormat().format(new Date());
		String currdate = currday + " 00:00:00";
		Date nowDate = null;
		try {
			nowDate = DateUtils.getYmdhmsDateFormat().parse(currdate);
		} catch (ParseException e) {
			return null;
		}
		return nowDate;

	}

	/**
	 * 返回时间
	 * 
	 * @return
	 */
	public static Date getCurrentDateTail() {
		String currday = DateUtils.getDateFormat().format(new Date());
		String currdate = currday + " 23:59:59";
		Date nowDate = null;
		try {
			nowDate = DateUtils.getYmdhmsDateFormat().parse(currdate);
		} catch (ParseException e) {
			return null;
		}
		return nowDate;

	}

	/**
	 * 取得Ymd 时间格式化类
	 * 
	 * @return
	 */
	public static DateFormat getDateFormat() {
		DateFormat df = threadLocal.get();
		if (df == null) {
			df = new SimpleDateFormat(ymdformate);
			threadLocal.set(df);
		}
		return df;
	}

	/**
	 * 取得Ymdhms 时间格式化类
	 * 
	 * @return
	 */
	public static DateFormat getYmdhmsDateFormat() {
		DateFormat df = threadLocalymhms.get();
		if (df == null) {
			df = new SimpleDateFormat(ymdhms);
			threadLocalymhms.set(df);
		}
		return df;
	}

	/**
	 * 取得Ymdhms 时间格式化类
	 * 
	 * @return
	 */
	public static DateFormat getYmdhDateFormat() {
		DateFormat df = threadLocalymdh.get();
		if (df == null) {
			df = new SimpleDateFormat(ymdh);
			threadLocalymdh.set(df);
		}
		return df;
	}

	/**
	 * 取得Ymdhms 时间格式化类
	 * 
	 * @return
	 */
	public static String getCurrentYmdh() {
		DateFormat dateFormat = getYmdhDateFormat();
		String crrentYmdh = dateFormat.format(new Date());
		return crrentYmdh;
	}

	/**
	 * 取得Ymd 时间格式化类
	 * 
	 * @return
	 */
	public static DateFormat getYmdDateFormat() {
		DateFormat df = threadLocalymdh.get();
		if (df == null) {
			df = new SimpleDateFormat("yyyy-MM-dd");
			threadLocalymdh.set(df);
		}
		return df;
	}

	/**
	 * 取得Ymd 时间格式化类
	 * 
	 * @return
	 */
	public static String getCurrentYmd() {
		DateFormat dateFormat = getYmdDateFormat();
		String crrentYmdh = dateFormat.format(new Date());
		return crrentYmdh;
	}

	/**
	 * 几天前的 日期的23：59：59
	 * 
	 * @return Date
	 */
	public static Date getTodayLastSecond(int date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH) - date;
		calendar.set(year, month, day, 23, 59, 59);
		return calendar.getTime();
	}

	/**
	 * 几天前的 日期的00：00：00
	 * 
	 * @return Date
	 */
	public static Date getTodayLastSecondZero(int date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH) - date;
		calendar.set(year, month, day, 00, 00, 00);
		return calendar.getTime();
	}

	/**
	 * 几天前的 日期的y-m-d
	 * 
	 * @return Date
	 */
	public static Date getTodayLastYMD(int date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH) - date;
		calendar.set(year, month, day);
		return calendar.getTime();
	}

	/**
	 * 返回一天二十四个小时的年月日时，格式为yyyyMMddHH
	 * 
	 * @return
	 */
	public static List<String> getOneDayYmdh() {
		List<String> hours = new ArrayList<String>();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		DateFormat sdf = getYmdhDateFormat();
		for (int i = 0; i < 24; i++) {
			calendar.set(Calendar.HOUR_OF_DAY, i);
			hours.add(sdf.format(new Date(calendar.getTimeInMillis())));
		}
		return hours;
	}

	/**
	 * 返回一天二十四个小时的年月日时，格式为yyyyMMddHH
	 * 
	 * @return
	 */
	public static String getDateWeekBefore() {
		String lastModified = new java.text.SimpleDateFormat("yyyy-MM-dd")
				.format(new java.util.Date(
						(new java.util.Date().getTime() - (long) 7 * 24 * 60
								* 60 * 1000)));
		return lastModified;
	}

	/**
	 * 返回前一天的日期
	 * 
	 * @return
	 */
	public static String getDateBeforeDay() {
		String lastModified = getDateFormat().format(
				new java.util.Date((new java.util.Date().getTime() - (long) 1
						* 24 * 60 * 60 * 1000)));
		lastModified = lastModified.replace("-", "");
		return lastModified;
	}

	/**
	 * 返回前一天的日期
	 * 
	 * @return
	 */
	public static Date getDateBefore(int min) {
		return new java.util.Date(
				(new java.util.Date().getTime() - (long) min * 60 * 1000));

	}

	/**
	 * 返回前一天的日期
	 * 
	 * @return
	 */
	public static Date getDateTimeBeforeMin(int min) {
		Date myDate = new java.util.Date(new java.util.Date().getTime()
				- (long) min * 60 * 1000);
		return myDate;
	}

	public static String getDateDateFormat(Date dTime) {
		DateFormat dateFormat = new SimpleDateFormat(ymdhms);
		return dateFormat.format(dTime);
	}

	public static Date parseDateNewFormat(String sDate) {
		DateFormat dateFormat = new SimpleDateFormat(ymdhms);
		Date d = null;
		if (sDate != null) {
			try {
				d = dateFormat.parse(sDate);
			} catch (ParseException ex) {
				throw new RuntimeException(
						"将时间转换为yyyy-MM-dd HH:mm:ss格式异常 格式转化前字符串=" + sDate, ex);
			}
		}
		return d;
	}

	/**
	 * 两日期间的间隔的天数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getDaysBetween(Date startDate, Date endDate) {
		Calendar fromCalendar = Calendar.getInstance();
		fromCalendar.setTime(startDate);
		fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
		fromCalendar.set(Calendar.MINUTE, 0);
		fromCalendar.set(Calendar.SECOND, 0);
		fromCalendar.set(Calendar.MILLISECOND, 0);

		Calendar toCalendar = Calendar.getInstance();
		toCalendar.setTime(endDate);
		toCalendar.set(Calendar.HOUR_OF_DAY, 0);
		toCalendar.set(Calendar.MINUTE, 0);
		toCalendar.set(Calendar.SECOND, 0);
		toCalendar.set(Calendar.MILLISECOND, 0);

		return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime()
				.getTime()) / (1000 * 60 * 60 * 24));
	}

	/**
	 * 取得两个日期间隔秒数（日期1-日期2）
	 * 
	 * @param one
	 *            日期1
	 * @param two
	 *            日期2
	 * 
	 * @return 间隔秒数
	 */
	public static long getDiffSeconds(Date one, Date two) {
		Calendar sysDate = new GregorianCalendar();

		sysDate.setTime(one);

		Calendar failDate = new GregorianCalendar();

		failDate.setTime(two);
		return (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / 1000;
	}

	public static void main(String[] args) {
		// List<String> pastDateList = getPastDateList(6);
		// System.out.println(pastDateList);
		// List<String> ondutyDateList = getPastDateList(5);
		// System.out.println(ondutyDateList);
		// System.out.println(DateUtils.getCurrentDateTail());

		Date list = getTodayLastYMD(5);
		// for (String s : list) {
		System.out.println(getDaysBetween(list, new Date()));
		// }

	}
}
