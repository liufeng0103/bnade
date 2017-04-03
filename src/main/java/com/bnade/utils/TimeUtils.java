package com.bnade.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {
	
	private static SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	
	public static final long SECOND = 1000;
	public static final long MINUTE = 60 * SECOND;
	public static final long HOUR = 60 * MINUTE;
	public static final long DAY = 24 * HOUR;

	public static String format(long millisecond) {
		StringBuffer sb = new StringBuffer();
		long day = millisecond / DAY;
		if (day > 0) {
			sb.append(day);
			sb.append("天");
			millisecond = millisecond - day * DAY;
		}
		long hour = millisecond / HOUR;
		if (hour > 0) {
			sb.append(hour);
			sb.append("小时");
			millisecond = millisecond - hour * HOUR;
		}
		long minute = millisecond / MINUTE;
		if (minute > 0) {
			sb.append(minute);
			sb.append("分");
			millisecond = millisecond - minute * MINUTE;
		}
		long second = millisecond / SECOND;
		if (second > 0) {
			sb.append(second);
			sb.append("秒");
			millisecond = millisecond - second * SECOND;
		}
		if (millisecond > 0) {
			sb.append(millisecond);
			sb.append("毫秒");
		}
		return sb.toString();
	}
	
	public static String getDate2(long time) {
		return sf2.format(time);
	}
	
	/**
	 * 返回当天日期
	 * @return
	 */
	public static String getDate() {		
		return sf.format(new Date());
	}
	
	/**
	 * 返回比当天差i天的日期
	 * @return
	 */
	public static String getDate(int i) {		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, i);
		return sf.format(cal.getTime());
	}
	
	/**
	 * 返回比当天差i天的日期
	 * @return
	 */
	public static String getDate(Date date, int i) {		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, i);
		return sf.format(cal.getTime());
	}
	
	public static String getDate(long time) {
		return sf.format(time);
	}
	
	/**
	 * 返回比当天差i天的日期
	 * @return
	 */
	public static int getYear(Date date) {	
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);			
		return cal.get(Calendar.YEAR);	
	}
	
	/**
	 * 返回比当月差i月的日期
	 * @return
	 */
	public static String getYearMonth(int i) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, i);
		return new SimpleDateFormat("yyyyMM").format(cal.getTime());
	}

	public static String getYearMonth(long time) {
		return new SimpleDateFormat("yyyyMM").format(time);
	}
	
	public static Date parse(String date) throws ParseException {
		return sf.parse(date);			
	}
	
	public static void main(String[] args) throws ParseException {
		System.out.println(TimeUtils.getDate());
		System.out.println(TimeUtils.getDate(-1));
		System.out.println(TimeUtils.parse("20160512"));	
		System.out.println(TimeUtils.getYear(new Date()));
		System.out.println(TimeUtils.getYearMonth(-1));
		System.out.println(TimeUtils.parse(TimeUtils.getDate(0)).getTime());
		System.out.println(TimeUtils.getDate(TimeUtils.parse("20160610"), -1));
		System.out.println(System.currentTimeMillis() + DAY * 31);
		System.out.println(System.currentTimeMillis());
		System.out.println(new SimpleDateFormat("hh:mm:ss").format(1491148977541l));
//		11:17:26
//		Sun, 02 Apr 2017 16:16:44 GMT - Mon Apr 03 00:16:46 CST 2017
	}
}
