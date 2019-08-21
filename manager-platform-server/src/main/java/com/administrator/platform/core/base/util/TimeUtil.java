/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年8月6日 上午9:18:14
 * @see:
 */
package com.administrator.platform.core.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeUtil {
	private static final Logger LOGGER = LoggerFactory
	        .getLogger(TimeUtil.class);

	private TimeUtil() {
	}

	/**
	 * 获取两个时间之间的差值
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param fromTime
	 * @param toTime
	 * @return
	 */
	public static String getPeriodTime(String fromTime, String toTime) {
		Date fromTimeDate = getFormatDate(fromTime);
		Date toTimeDate = getFormatDate(toTime);
		return getPeriodTimeOfDate(fromTimeDate, toTimeDate);

	}

	/**
	 * 获取指定格式的时间
	 * 
	 * @see :
	 * @param :
	 * @return : Date
	 * @param dateString
	 * @return
	 */
	public static Date getFormatDate(String dateString) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
		        CalendarUtil.DEFAULT_FORMAT_WHOLE);

		try {
			return simpleDateFormat.parse(dateString);
		} catch (ParseException e) {
			LOGGER.error("解析时间失败：{}，失败原因:{}", dateString, e.getMessage());
			return null;
		}
	}

	/**
	 * 获取指定时间的某种格式
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param date
	 * @return
	 */
	public static String getFormatStringOfDate(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
		        CalendarUtil.DEFAULT_FORMAT_WHOLE);
		return simpleDateFormat.format(date);
	}

	/**
	 * 获取两个时间之间的差值
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param fromTime
	 * @param toTime
	 * @return
	 */
	public static String getPeriodTimeOfDate(Date fromTime, Date toTime) {
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		long ms = 0;

		StringBuilder formatDate = new StringBuilder();
		long periodTime = getPeriodTime(fromTime, toTime);

		day = periodTime / (24 * 60 * 60 * 1000);
		hour = (periodTime / (60 * 60 * 1000) - day * 24);
		min = ((periodTime / (60 * 1000)) - day * 24 * 60 - hour * 60);
		sec = (periodTime / 1000 - day * 24 * 60 * 60 - hour * 60 * 60
		        - min * 60);

		formatDate.append(day).append("天");
		formatDate.append(hour).append("小时");
		formatDate.append(min).append("分");
		formatDate.append(sec).append("秒");
		formatDate.append(ms).append("毫秒");
		return formatDate.toString();
	}

	/**
	 * 获取两个时间之间的差值
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param fromTime
	 * @param toTime
	 * @return
	 */
	public static long getPeriodTime(Date fromTime, Date toTime) {
		long periodTime = toTime.getTime() - fromTime.getTime();

		if (periodTime < 0) {
			return 0L;
		}
		return periodTime;
	}

	/**
	 * 
	 * @see :
	 * @param :
	 * @return : boolean
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public static boolean toDateAfterFromDate(String fromDate, String toDate) {
		return toDateAfterFromDate(getFormatDate(fromDate),
		        getFormatDate(toDate));
	}

	/**
	 * 
	 * @see :
	 * @param :
	 * @return : boolean
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public static boolean toDateAfterFromDate(Date fromDate, Date toDate) {
		return getPeriodTime(fromDate, toDate) > 0;
	}

	public static void main(String[] args) {
		System.out.println(
		        getPeriodTime("2019-08-06 09:36:45", "2019-08-10 10:45:51"));
	}
}
