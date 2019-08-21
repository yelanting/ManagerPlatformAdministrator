/**
 * @author : 孙留平
 * @since : 2018年9月11日 下午2:53:13
 * @see:
 */
package com.administrator.platform.core.base.util;

/**
 * @author : Administrator
 * @since : 2018年9月11日 下午2:53:13
 * @see :
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.administrator.platform.config.GlobalExceptionMessage;
import com.administrator.platform.exception.base.BusinessValidationException;

public class CalendarUtil {
    /**
     * 格式化类型
     */
    public static final String DEFAULT_FORMAT = "yyyy-MM-dd";

    /**
     * 通用格式化类型
     */
    public static final String DEFAULT_FORMAT_WHOLE = "yyyy-MM-dd HH:mm:ss";

    /**
     * 一月
     */
    public static final int FIRST_MONTH_OF_SEASON_ONE = 1;

    /**
     * 四月
     */
    public static final int FIRST_MONTH_OF_SEASON_FOUR = 4;

    /**
     * 七月
     */
    public static final int FIRST_MONTH_OF_SEASON_SEVEN = 7;

    /**
     * 十月
     */
    public static final int FIRST_MONTH_OF_SEASON_TEN = 10;

    /**
     * 第一天
     */
    public static final int DAY_OF_FIRST = 1;

    /***
     * 默认格式的现在日期
     * 
     * @return
     */
    public static Date now() {
        return new Date();
    }

    /***
     * 返回指定格式的现在日期
     * 
     * @return
     */
    public static Date now(String format) {
        return formatDate(format, now());
    }

    /***
     * yyyy-MM-dd 格式的现在日期
     * 
     * @return
     */
    public static Date today() {
        return formatDate(DEFAULT_FORMAT, now());
    }

    /***
     * yyyy-MM-dd 格式的日期
     * 
     * @return
     */
    public static Date getTomorrow() {
        Calendar c = convertToCalendar(today());

        if (null == c) {
            throw new BusinessValidationException(GlobalExceptionMessage.NULL_PARAMETER_MESSAGE);
        }
        c.add(Calendar.DAY_OF_MONTH, 1);
        return c.getTime();
    }

    public static Date getYesterDay() {
        Calendar c = convertToCalendar(today());

        if (null == c) {
            throw new BusinessValidationException(GlobalExceptionMessage.NULL_PARAMETER_MESSAGE);
        }
        c.add(Calendar.DAY_OF_MONTH, -1);
        return c.getTime();
    }

    /***
     * yyyy-MM-dd 格式的日期
     * 
     * @return
     */
    @SuppressWarnings("static-access")
    public static Date getNewDate(Date date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, day);
        return c.getTime();
    }

    /***
     * 按照指定的格式解析日期字符串
     * 
     * @return
     */
    public static Date parseDate(String dateString, String format) {
        if (!StringUtil.isStringAvaliable(dateString)) {
            return null;
        }
        SimpleDateFormat formater = new SimpleDateFormat();
        formater.applyPattern(format);
        formater.setLenient(false);
        try {
            return formater.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    /***
     * 按照指定的格式格式化日期
     * 
     * @return
     */
    private static Date formatDate(String format, Date date) {
        SimpleDateFormat formater = new SimpleDateFormat();
        formater.applyPattern(format);
        formater.setLenient(false);
        try {
            return formater.parse(formater.format(date));
        } catch (ParseException e) {
            return now();
        }
    }

    /***
     * 返回yyyy-MM-dd 格式的日期字符串
     * 
     * @return
     */
    public static String format(Date date) {
        return format(DEFAULT_FORMAT, date);
    }

    /***
     * 返回指定格式的日期字符串
     * 
     * @return
     */
    public static String format(String format, Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat formater = new SimpleDateFormat();
        formater.applyPattern(format);
        return formater.format(date);
    }

    /***
     * 返回今天是星期几 例如：星期一
     * 
     * @return
     */
    public static String getWeekDay() {
        return format("EEEE", today());
    }

    /***
     * 返回指定的日期是星期几 例如：星期一
     * 
     * @return
     */
    public static String getWeekDay(Date date) {
        return format("EEEE", date);
    }

    /***
     * 返回现在日期的年份
     * 
     * @return
     */
    public static int getNowYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /***
     * 返回现在日期的月份
     * 
     * @return
     */
    public static int getNowMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    public static int getNextMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
        return cal.get(Calendar.MONTH) + 1;
    }

    /***
     * 返回现在日期的天
     * 
     * @return
     */
    public static int getNowDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    /***
     * 本月最后一天的天数
     * 
     * @param year
     * @param month
     * @return
     */
    public static int lastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 1);
        return cal.getMaximum(Calendar.DAY_OF_MONTH);
    }

    public static int getLastYear() {
        return Calendar.getInstance().get(Calendar.YEAR) - 1;
    }

    public static int getLastMonth() {
        Calendar temp = Calendar.getInstance();
        temp.add(Calendar.MONTH, -1);
        return temp.get(Calendar.MONTH) + 1;
    }

    public static int getLastMonthYearValue() {
        Calendar temp = Calendar.getInstance();
        temp.add(Calendar.MONTH, -1);
        return temp.get(Calendar.YEAR);
    }

    public static Date getMonthStart(int year, int month) {
        if (0 == year) {
            return null;
        }
        return parseDate(String.valueOf(year) + month, "yyyyM");
    }

    public static Date getNextMonthStart(int year, int month) {
        if (0 == year) {
            return null;
        }
        Calendar temp = getMonthEndCalendar(year, month);
        temp.add(Calendar.DAY_OF_MONTH, 1);
        return temp.getTime();
    }

    public static Date getNextDay(Date date) {
        if (null == date) {
            return null;
        }
        Calendar temp = Calendar.getInstance();
        temp.setTime(date);
        temp.add(Calendar.DAY_OF_MONTH, 1);
        return temp.getTime();
    }

    public static Date getLastDay(Date date) {
        if (null == date) {
            return null;
        }
        Calendar temp = Calendar.getInstance();
        temp.setTime(date);
        temp.add(Calendar.DAY_OF_MONTH, -1);
        return temp.getTime();
    }

    public static Date getLastMonthEnd(int year, int month) {
        if (month == 1) {
            year = year - 1;
            month = 12;
        } else {
            month = month - 1;
        }

        Calendar temp = getMonthEndCalendar(year, month);

        // 这样写会导致10月份只获取到30日,31日会丢失
        // temp.add(Calendar.MONTH, -1);
        return temp.getTime();
    }

    public static Date getLastMonthStart(int year, int month) {
        Calendar result = Calendar.getInstance();
        result.setTime(getMonthStart(year, month));
        result.add(Calendar.MONTH, -1);
        return result.getTime();
    }

    public static Date getMonthEnd(int year, int month) {
        return getMonthEndCalendar(year, month).getTime();
    }

    private static Calendar getMonthEndCalendar(int year, int month) {
        Calendar result = Calendar.getInstance();
        result.setTime(getMonthStart(year, month));
        result.set(Calendar.DAY_OF_MONTH, result.getActualMaximum(Calendar.DAY_OF_MONTH));
        return result;
    }

    /***
     * 返回指定日期的年份
     * 
     * @return
     */
    public static int getYear(Date date) {
        Calendar c = convertToCalendar(date);
        if (null == c) {
            throw new BusinessValidationException(GlobalExceptionMessage.NULL_PARAMETER_MESSAGE);
        }
        return c.get(Calendar.YEAR);
    }

    /***
     * 返回指定日期的月份
     * 
     * @return
     */
    public static int getMonth(Date date) {
        Calendar c = convertToCalendar(date);
        return c == null ? 1 : c.get(Calendar.MONTH) + 1;
    }

    public static int getDay(Date date) {
        Calendar c = convertToCalendar(date);
        return c == null ? 1 : c.get(Calendar.DAY_OF_MONTH);
    }

    public static Integer getHour(Date date) {
        Calendar c = convertToCalendar(date);
        return c == null ? null : c.get(Calendar.HOUR_OF_DAY);
    }

    public static String getHourStr(Date date) {
        Integer hour = getHour(date);
        if (hour == null) {
            return null;
        }
        return hour.toString().length() > 1 ? hour.toString() : "0" + hour;

    }

    public static Integer getMinute(Date date) {
        Calendar c = convertToCalendar(date);
        return c == null ? null : c.get(Calendar.MINUTE);
    }

    public static String getMinuteStr(Date date) {
        Integer minute = getMinute(date);
        if (minute == null) {
            return null;
        }
        return minute.toString().length() > 1 ? minute.toString() : "0" + minute;
    }

    public static Calendar convertToCalendar(Date date) {
        if (date == null) {
            return null;
        }
        Calendar result = Calendar.getInstance();
        result.setTime(date);
        return result;
    }

    /**
     * 获取当前月第一天 字符串格式
     * 
     * @param dateFormat
     * @return
     */
    public static String getCurrMonthFirstDay(String dateFormat) {

        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        // 获取当前月第一天：
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        // 设置为1号,当前日期既为本月第一天
        c.set(Calendar.DAY_OF_MONTH, 1);
        String first = format.format(c.getTime());

        return first;
    }

    /**
     * 获取当前月最后一天 字符串格式
     * 
     * @param dateFormat
     * @return
     */
    public static String getCurrMonthLastDay(String dateFormat) {

        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        // 获取当前月最后一天
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = format.format(ca.getTime());

        return last;
    }

    /**
     * 得到当前周 周一的日期
     * 
     * 
     * 
     * 咳咳，这个方法别用了哈，礼拜天调用会返回下周一的日期，显然是不合理的对吧~
     * 同理，怕改了出问题，就没改哈，谁要用的话自己写一个吧=。=
     * 
     * @return
     */
    @Deprecated
    public static Date getMonday() {
        Calendar cal = Calendar.getInstance();
        // 获取本周一的日期
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }

    /**
     * 得到当前月 第一天的日期
     * 
     * @return
     */
    public static Date getCurrMonthFirstDay() {
        // 获取当前月第一天：
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        // 设置为1号,当前日期既为本月第一天
        c.set(Calendar.DAY_OF_MONTH, 1);
        return c.getTime();
    }

    /**
     * 获取当前月最后一天
     * 
     * @param dateFormat
     * @return
     */
    public static Date getCurrMonthLastDay() {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        return ca.getTime();
    }

    /**
     * 某年第一天
     * 
     * @see :
     * @param :
     * @return : Date
     * @param year
     * @return
     */
    public static Date getYearFirtDay(int year) {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.YEAR, year);
        // 本年第一天
        ca.set(Calendar.DAY_OF_YEAR, 1);
        return ca.getTime();
    }

    /**
     * 今年第一天
     * 
     * @see :
     * @param :
     * @return : Date
     * @return
     */
    public static Date getCurrentYearFirtDay() {
        Calendar c = Calendar.getInstance();
        // 本年第一天
        c.set(Calendar.DAY_OF_YEAR, 1);
        return c.getTime();
    }

    /**
     * 某年最后一天
     * 
     * @see :
     * @param :
     * @return : Date
     * @param year
     * @return
     */
    public static Date getYearLastDay(int year) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.add(Calendar.YEAR, 1);
        // 本年最后一天
        c.set(Calendar.DAY_OF_YEAR, 1);
        c.add(Calendar.DAY_OF_YEAR, -1);
        return c.getTime();
    }

    /**
     * 今年最后一天
     * 
     * @see :
     * @param :
     * @return : Date
     * @return
     */
    public static Date getCurrentYearLastDay() {
        // Calendar c = Calendar.getInstance();
        // c.add(Calendar.YEAR, 1);
        // c.set(Calendar.DAY_OF_YEAR, 1);// 本年最后一天
        // c.add(Calendar.DAY_OF_YEAR, -1);
        // return c.getTime();
        return getYearLastDay(getNowYear());
    }

    /***
     * 两个时间的差
     * 
     * @param max
     * @param min
     * @return
     */
    public static int getMinusDay(Date max, Date min) {
        Date d1 = formatDate(DEFAULT_FORMAT, max);
        Date d2 = formatDate(DEFAULT_FORMAT, min);
        // 这样得到的差值是微秒级别
        long diff = d1.getTime() - d2.getTime();
        Long days = diff / (1000 * 60 * 60 * 24);
        return (days.intValue() + 1);
    }

    public static int getMinusDay(Date min) {
        return getMinusDay(now(), min);
    }

    public static Date getDate(int year, int month, int day) {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.YEAR, year);
        ca.set(Calendar.MONTH, month);
        ca.set(Calendar.DAY_OF_MONTH, day);
        return ca.getTime();
    }

    /**
     * 修复 getDate(int year, int month, int day)
     * 该方法 对于月份处理 -- ca.set(Calendar.MONTH, month);
     * 而 month = 0 是 1 月份 11 代表 12 月份
     * 
     * @param year
     *            年份
     * @param month
     *            月份 1 代表 1 月份 (1~12)
     * @param day
     *            天数
     * @return
     */
    public static Date getDateFix(int year, int month, int day) {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.YEAR, year);
        ca.set(Calendar.MONTH, month - 1);
        ca.set(Calendar.DAY_OF_MONTH, day);
        return ca.getTime();
    }

    public static Date getDateEnd(Date date, int day) {
        Calendar result = Calendar.getInstance();
        result.setTime(getDate(date, day));
        result.set(Calendar.DAY_OF_MONTH, result.getActualMaximum(Calendar.DAY_OF_MONTH));
        return result.getTime();
    }

    public static Date getDate(Date date, int day) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.set(Calendar.DAY_OF_MONTH, day);
        return ca.getTime();
    }

    public static Calendar calFirstDaynThisWeek(int year, int week) {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.WEEK_OF_YEAR, week);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 1);
        return c;
    }

    public static Calendar calLastDaynThisWeek(int year, int week) {
        Calendar c = CalendarUtil.calFirstDaynThisWeek(year, week);
        c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + 6);
        return c;
    }

    public static Date getDateAfterAddDate(Date date, int day) {
        Date date1 = (Date) date.clone();
        Calendar c = new GregorianCalendar();
        c.setTime(date1);
        c.add(Calendar.DATE, day);
        date1 = c.getTime();
        return date1;
    }

    public static Date getDateAfterAddMonth(Date date, int month) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.add(Calendar.MONTH, month);
        date = c.getTime();
        return date;
    }

    public static Date getWeekStartDate() {

        return newGetWeekStartDate();
    }

    public static Date newGetWeekStartDate() {
        Calendar cal = Calendar.getInstance();
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            cal.add(Calendar.DATE, -6);
        }
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date date = cal.getTime();
        return date;
    }

    public static Date getLastWeekStartDateOfYear() {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date date = cal.getTime();

        return date;
    }

    public static int getWeekOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public static Date getLastWeekStartDate() {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.add(Calendar.DATE, -7);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date date = cal.getTime();
        return date;
    }

    /**
     * 是否是周一
     * 
     * @see :
     * @param :
     * @return : boolean
     * @return
     */
    public static boolean whetherMonday() {
        boolean flag = false;
        Calendar c = Calendar.getInstance();
        int week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (week == 1) {
            flag = true;
        }
        return flag;
    }

    /**
     * 是否是某月的第一天
     * 
     * @see :
     * @param :
     * @return : boolean
     * @return
     */
    public static boolean whetherMonthFirstDay() {
        boolean flag = false;
        if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == 1) {
            flag = true;
        }
        return flag;
    }

    /**
     * 是否是某季度的第一天
     * 
     * @see :
     * @param :
     * @return : boolean
     * @return
     */
    public static boolean whetherQuarterFirstDay() {
        boolean flag = false;
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);

        boolean isFirstDayOfSeason = (month == FIRST_MONTH_OF_SEASON_ONE || month == FIRST_MONTH_OF_SEASON_FOUR
                || month == FIRST_MONTH_OF_SEASON_SEVEN || month == FIRST_MONTH_OF_SEASON_TEN) && day == 1;
        if (isFirstDayOfSeason) {
            flag = true;
        }
        return flag;
    }

    /**
     * 是否是某年的第一天
     * 
     * @see :
     * @param :
     * @return : boolean
     * @return
     */
    public static boolean whetherYearFirstDay() {
        boolean flag = false;
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        if (month == 1 && day == 1) {
            flag = true;
        }
        return flag;
    }

    // /**
    // * 上个季度的第一天起始时间
    // *
    // * @see :
    // * @param :
    // * @return : Calendar
    // * @return
    // */
    // public static Calendar getLastQuarterStartTime() {
    // Calendar c = Calendar.getInstance();
    // int currentMonth = c.get(Calendar.MONTH) + 1;
    // if (currentMonth >= 1 && currentMonth <= 3) {
    // c.set(Calendar.MONTH, 9);
    // c.set(Calendar.YEAR, getLastYear());
    // } else if (currentMonth >= 4 && currentMonth <= 6) {
    // c.set(Calendar.MONTH, 0);
    // } else if (currentMonth >= 7 && currentMonth <= 9) {
    // c.set(Calendar.MONTH, 3);
    // } else if (currentMonth >= 10 && currentMonth <= 12) {
    // c.set(Calendar.MONTH, 6);
    // }
    // c.set(Calendar.DAY_OF_MONTH, 1);
    // return c;
    //
    // }

    // /**
    // * 本季度的第一天起始时间
    // *
    // * @see :
    // * @param :
    // * @return : Calendar
    // * @return
    // */
    // public static Calendar getCurrentQuarterStartTime() {
    // Calendar c = Calendar.getInstance();
    // int currentMonth = c.get(Calendar.MONTH) + 1;
    // if (currentMonth >= 1 && currentMonth <= 3) {
    // c.set(Calendar.MONTH, 0);
    // } else if (currentMonth >= 4 && currentMonth <= 6) {
    // c.set(Calendar.MONTH, 3);
    // } else if (currentMonth >= 7 && currentMonth <= 9) {
    // c.set(Calendar.MONTH, 6);
    // } else if (currentMonth >= 10 && currentMonth <= 12) {
    // c.set(Calendar.MONTH, 9);
    // }
    // c.set(Calendar.DAY_OF_MONTH, 1);
    // return c;
    // }

    // /**
    // * 本季度的第一天起始时间
    // *
    // * @see :
    // * @param :
    // * @return : Integer
    // * @return
    // */
    // public static Integer getCurrentQuarter() {
    // Calendar c = Calendar.getInstance();
    // int currentMonth = c.get(Calendar.MONTH) + 1;
    // Integer quarter = null;
    // if (currentMonth >= 1 && currentMonth <= 3) {
    // quarter = 1;
    // } else if (currentMonth >= 4 && currentMonth <= 6) {
    // quarter = 2;
    // } else if (currentMonth >= 7 && currentMonth <= 9) {
    // quarter = 3;
    // } else if (currentMonth >= 10 && currentMonth <= 12) {
    // quarter = 4;
    // }
    // return quarter;
    // }

    /***
     * 两个时间的月份差
     * 
     * @param max
     * @param min
     * @return
     */
    public static int getMinusMonth(Date max, Date min) {
        Date d1 = formatDate(DEFAULT_FORMAT, max);
        Date d2 = formatDate(DEFAULT_FORMAT, min);
        int result = getMonth(d1) - getMonth(d2);
        int month = (getYear(d1) - getYear(d2)) * 12;
        return Math.abs(month + result);
    }

    /**
     * 获取时分秒毫秒
     */
    public static String getTimeMillis() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        int mm = cal.get(Calendar.MILLISECOND);
        StringBuffer sBuffer = new StringBuffer();
        sBuffer.append(hour).append(minute).append(second).append(mm);
        return sBuffer.toString();
    }

    /**
     * 修改时间
     * 
     * @see :
     * @param :
     * @return : Date
     * @param date
     * @param hour
     * @param min
     * @param sec
     * @return
     */
    public static Date getDateModifiedTime(Date date, Integer hour, Integer min, Integer sec) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, hour == null ? 0 : hour);
        cal.set(Calendar.MINUTE, min == null ? 0 : min);
        cal.set(Calendar.SECOND, sec == null ? 0 : sec);
        return cal.getTime();
    }

    /**
     * 一年中的天
     * 
     * @see :
     * @param :
     * @return : int
     * @param date
     * @return
     */
    public static int getDayInYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_YEAR);
    }

    public static int getActualMaxDayInMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getMonthStart(year, month));
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static Date getTimeAfterAddMin(Date date, int min) {
        long time = date.getTime();
        Date result = new Date(time + min * 60 * 1000);
        return result;
    }

    /**
     * 获取常用当前时间
     * 
     * @see :
     * @param :
     * @return : void
     * @param args
     */
    public static String getUsualNowDateTime() {
        return format(DEFAULT_FORMAT_WHOLE, now());
    }

    /**
     * 获取常用当前时间
     *
     * @see :
     * @param :
     * @return : void
     * @param args
     */
    public static Date getUsualNowDateTimeWithDateStyle() {
        // String currentDateTime = format(DEFAULT_FORMAT_WHOLE, now());
        return formatDate(DEFAULT_FORMAT_WHOLE, now());
    }

    public static void main(String[] args) {
        System.out.println(getUsualNowDateTime());
    }

}
