package com.aiccj.abtest.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 有关时间的工具类
 */
public final class DateUtil {

    public static List<Integer> TIMEZONES = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
            -1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12);

    /**
     * 时间字符串根据pattern的规则去转化为Date
     *
     * @param dateStr
     * @param pattern
     * @return
     */
    public final static Date convertToDate(String dateStr, String pattern) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(pattern);
            return dateFormat.parse(dateStr);
        } catch (Exception ex) {
            throw new RuntimeException("日期格式化转换异常: Str To Date", ex);
        }
    }

    public final static Date convertToDate(long dateLong) {
        return new Date(dateLong);
    }


    /**
     * Date转化为pattern格式的字符串
     *
     * @param date
     * @param pattern
     * @return
     */
    public final static String convertToStr(Date date, String pattern) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(pattern);
            return dateFormat.format(date);
        } catch (Exception ex) {
            throw new RuntimeException("日期格式化转换异常: Date To Str", ex);
        }
    }

    public final static String convertToStr(Date date) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.format(date);
        } catch (Exception ex) {
            throw new RuntimeException("日期格式化转换异常: Date To Str", ex);
        }
    }


    /**
     * 时间修改，增加或者减少年、月、日、时、分、秒、毫秒
     *
     * @param srcDate
     * @param field
     * @param value
     * @return
     * @see Calendar
     */
    public final static Date dateAdd(Date srcDate, int field, int value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(srcDate);
        calendar.add(field, value);
        return calendar.getTime();
    }


    /**
     * 时间修改，增加或者减少年、月、日、时、分、秒、毫秒
     *
     * @param srcDateMillions
     * @param field
     * @param value
     * @return
     * @see Calendar
     */
    public final static long dateAdd(long srcDateMillions, int field, int value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(srcDateMillions);
        calendar.add(field, value);
        return calendar.getTimeInMillis();
    }


    /**
     * 获取传入日期的 00:00:00 时间点
     *
     * @param date
     * @return
     */
    public final static Date getDateBegin(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


    /**
     * 获取日期中的小时
     *
     * @param date
     * @return
     * @see DateUtil#getDateItem(Date, int)
     */
    @Deprecated
    public final static int getDateHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }


    /**
     * 获取日期中的某个元素，如 年、月、日、时、分、秒
     *
     * @param date
     * @param itemIdex
     * @return
     * @see Calendar
     */
    public final static int getDateItem(Date date, int itemIdex) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int result = calendar.get(itemIdex);
        if (itemIdex == Calendar.MONTH) {
            return result + 1;
        }
        return result;
    }


    /**
     * 比较两个日期是否属于同一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public final static boolean isSameDay(Date date1, Date date2) {
        Date day1 = getDateBegin(date1);
        Date day2 = getDateBegin(date2);
        return day1.getTime() == day2.getTime();
    }


    /**
     * 获取传入日期下一天的 00:00:00 时间点
     *
     * @param date
     * @return
     */
    public final static Date getNextDateBegin(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


    public final static Date getMonthBegin(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, 0);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获得该月第一天
     *
     * @param month
     * @return
     */
    public static String getFirstDayOfMonth(int month) {
        Calendar cal = Calendar.getInstance();
        // 设置月份
        cal.set(Calendar.MONTH, month - 1);
        // 获取某月最小天数
        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        // 设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String firstDayOfMonth = sdf.format(cal.getTime()) + " 00:00:00";
        return firstDayOfMonth;
    }

    /**
     * 获得该月最后一天
     *
     * @param month
     * @return
     */
    public static String getLastDayOfMonth(int month) {
        Calendar cal = Calendar.getInstance();
        // 设置月份
        cal.set(Calendar.MONTH, month - 1);
        // 获取某月最大天数
        int lastDay = 0;
        //2月的平年瑞年天数
        if (month == 2) {
            lastDay = cal.getLeastMaximum(Calendar.DAY_OF_MONTH);
        } else {
            lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        // 设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfMonth = sdf.format(cal.getTime()) + " 23:59:59";
        return lastDayOfMonth;
    }

    /**
     * 根据日期判断本月有多少天
     */
    public static int dayByMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;

        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            //对于2月份需要判断是否为闰年
            case 2:
                if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                    return 29;
                } else {
                    return 28;
                }
            default:
                return 0;
        }
    }

    /**
     * 功能描述 获取当前时间
     **/
    public static Date getCurTime() {
        return new Date();
    }

    public static Date getTimeZoneCurTime(Integer timeZone) {
        String targetTimeZoneTime = getTargetTimeZoneTime(timeZone, getCurTime(), "yyyy-MM-dd " +
                "HH:mm:ss");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return df.parse(targetTimeZoneTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取当前日期的开始时间
     *
     * @return
     */
    public static Date getCurDateBegin() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取当前日期的结束时间
     *
     * @return
     */
    public static Date getCurDateEnd() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 获取当前日期的开始时间
     *
     * @return
     */
    public static Date getYesterdayDateBegin() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return DateUtil.dateAdd(calendar.getTime(), Calendar.DAY_OF_MONTH, -1);
    }

    /**
     * 获取当前日期的结束时间
     *
     * @return
     */
    public static Date getYesterdayDateEnd() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return DateUtil.dateAdd(calendar.getTime(), Calendar.DAY_OF_MONTH, -1);
    }

    /**
     * @param targetTimeZone 东区：1，2，3，4，5，6，7，8，9，10，11，12
     *                       西区：-1，-2，-3，-4，-5，-6，-7，-8，-9，-10，-11，-12
     *                       东12 == 西12
     * @param date
     * @return
     */
    public static String getTargetTimeZoneTime(Integer targetTimeZone, Date date, String pattern) {
        TimeZone timeZone = convertIntegerToTimeZone(targetTimeZone);
        DateFormat df = new SimpleDateFormat(pattern);
        df.setTimeZone(timeZone);
        return df.format(date);
    }

    public static TimeZone convertIntegerToTimeZone(Integer timeZoneInteger) {
        if (!TIMEZONES.contains(timeZoneInteger)) {
            return null;
        }
        String joiner = "+";
        String timeZoneStr = timeZoneInteger + ":00";
        if (timeZoneInteger < 0) {
            joiner = "";
        }
        return TimeZone.getTimeZone("GMT" + joiner + timeZoneStr);
    }

    public static String getTargetTimeZoneTime(Integer targetTimeZone, Date date) {
        return getTargetTimeZoneTime(targetTimeZone, date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String getTargetTimeZoneTime(Integer targetTimeZone, String dateStr) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date parseDate = sdf.parse(dateStr);
            return getTargetTimeZoneTime(targetTimeZone, parseDate, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Integer getServerDefaultTimeZone() {
        return 8;
    }


    public static void main1(String[] args) {
        System.out.println(getTimeZoneCurTime(-5));
    }

    /**
     * 获取目标时区的当前日期的开始时间
     *
     * @param timeZone 时区 如1···12 ， -1···-12
     * @param pattern
     * @return
     */
    public static String getCurTimeBeginStr(Integer timeZone, String pattern) {
        Date timeZoneCurTime = DateUtil.getTimeZoneCurTime(timeZone);
        Date curTimeBegin = DateUtil.getTimeBeginTime(timeZoneCurTime);
        return DateUtil.convertToStr(curTimeBegin, pattern);
    }

    public static Date getTimeBeginTime(Date timeZoneCurTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timeZoneCurTime);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取目标时区的当前日期的结束时间
     *
     * @param timeZone 时区 如1···12 ， -1···-12
     * @param pattern
     * @return
     */
    public static String getCurTimeEndStr(Integer timeZone, String pattern) {
        Date timeZoneCurTime = DateUtil.getTimeZoneCurTime(timeZone);
        Date curTimeBegin = DateUtil.getTimeEndTime(timeZoneCurTime);
        return DateUtil.convertToStr(curTimeBegin, pattern);
    }

    public static Date getTimeEndTime(Date timeZoneCurTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timeZoneCurTime);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }


    /**
     * 目标时区 昨日开始时间
     *
     * @param timeZone
     * @param pattern
     * @return
     */
    public static String getYesterdayTimeBeginStr(Integer timeZone, String pattern) {
        Date timeZoneCurTime = DateUtil.getTimeZoneCurTime(timeZone);
        Date yesterdayBeginTime = DateUtil.getYesterdayBeginTime(timeZoneCurTime);
        return DateUtil.convertToStr(yesterdayBeginTime, pattern);
    }

    private static Date getYesterdayBeginTime(Date timeZoneCurTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timeZoneCurTime);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return DateUtil.dateAdd(calendar.getTime(), Calendar.DAY_OF_MONTH, -1);
    }

    /**
     * 目标时区 昨日结束时间
     *
     * @param timeZone
     * @param pattern
     * @return
     */
    public static String getYesterdayTimeEndStr(Integer timeZone, String pattern) {
        Date timeZoneCurTime = DateUtil.getTimeZoneCurTime(timeZone);
        Date yesterdayBeginTime = DateUtil.getYesterdayEndTime(timeZoneCurTime);
        return DateUtil.convertToStr(yesterdayBeginTime, pattern);
    }

    private static Date getYesterdayEndTime(Date timeZoneCurTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timeZoneCurTime);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return DateUtil.dateAdd(calendar.getTime(), Calendar.DAY_OF_MONTH, -1);
    }


    /**
     * 将sourceDate转换成指定时区的时间
     *
     * @param sourceDate
     * @param sourceTimezone sourceDate所在的时区
     * @param targetTimezone 转化成目标时间所在的时区
     * @return
     */
    public static Date convertTimezone(Date sourceDate, TimeZone sourceTimezone,
                                       TimeZone targetTimezone) {


        // targetDate - sourceDate=targetTimezone-sourceTimezone
        // --->
        // targetDate=sourceDate + (targetTimezone-sourceTimezone)


        Calendar calendar = Calendar.getInstance();
        // date.getTime() 为时间戳, 为格林尼治到系统现在的时间差,世界各个地方获取的时间戳是一样的,
        // 格式化输出时,因为设置了不同的时区,所以输出不一样
        long sourceTime = sourceDate.getTime();


        calendar.setTimeZone(sourceTimezone);
        calendar.setTimeInMillis(sourceTime);// 设置之后,calendar会计算各种filed对应的值,并保存

        //获取源时区的到UTC的时区差
        int sourceZoneOffset = calendar.get(Calendar.ZONE_OFFSET);


        calendar.setTimeZone(targetTimezone);
        calendar.setTimeInMillis(sourceTime);

        int targetZoneOffset = calendar.get(Calendar.ZONE_OFFSET);
        int targetDaylightOffset = calendar.get(Calendar.DST_OFFSET); // 夏令时


        long targetTime = sourceTime + (targetZoneOffset + targetDaylightOffset) - sourceZoneOffset;

        return new Date(targetTime);

    }

    public static String getCurDateStr() {
        return convertToStr(getCurTime(), "YYYY-mm-dd");
    }

    /**
     * 获取当前月第一天：
     */
    public static Date getMonthStart(String date, String pattern) {
        Date src = DateUtil.convertToDate(date, pattern);
        Calendar monthStart = Calendar.getInstance();
        monthStart.setTime(src);
        monthStart.add(Calendar.MONTH, 0);
        monthStart.set(Calendar.DAY_OF_MONTH, 1);
        return monthStart.getTime();
    }

    /**
     * 获取当前月最后一天：23:59:59
     */
    public static Date getMonthEnd(String date, String pattern) {
        Date src = DateUtil.convertToDate(date, pattern);
        Calendar monthEnd = Calendar.getInstance();
        monthEnd.setTime(src);
        monthEnd.set(Calendar.DAY_OF_MONTH, monthEnd.getActualMaximum(Calendar.DAY_OF_MONTH));
        monthEnd.set(Calendar.SECOND, 59);
        monthEnd.set(Calendar.MINUTE, 59);
        monthEnd.set(Calendar.HOUR_OF_DAY, 23);
        return monthEnd.getTime();
    }

    /**
     * 获取当前月最后一天：
     */
    public static int getMonthMaxDays(String date, String pattern) {
        Date src = DateUtil.convertToDate(date, pattern);
        Calendar monthEnd = Calendar.getInstance();
        monthEnd.setTime(src);
        return monthEnd.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}
