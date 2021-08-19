package com.example.framedemo.util;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
/**
 * Time工具类
 */
public class TimeUtil {

    public static long getStartTimeInMillisOfDay() {
        Calendar startDate = new GregorianCalendar();
        startDate.set(Calendar.HOUR_OF_DAY, 0);
        startDate.set(Calendar.MINUTE, 0);
        startDate.set(Calendar.SECOND, 0);
        return startDate.getTimeInMillis();
    }


    /**
     * 获取过去几天的时间戳，以天为单位，包括今天。
     *
     * @param dayCount 天数
     */
    public static List<Long> getSeveralPastDays(int dayCount) {
        List<Long> days = new ArrayList<>(dayCount);
        long oneDay = 1000 * 60 * 60 * 24;//一天对应的毫秒数。
        long current = System.currentTimeMillis();
        for (int i = 0; i < dayCount; i++) {
            days.add(current / oneDay - i);
        }
        return days;
    }

    /**
     * 获取当周开始时间戳
     *
     * @param timeStamp 毫秒级时间戳
     */
    public static Long getWeekStartTime(Long timeStamp) {
        return getWeekStartTime(timeStamp, TimeZone.getDefault());
    }

    /**
     * 获取当周开始时间戳
     *
     * @param timeStamp 毫秒级时间戳
     * @param timeZone  如 GMT+8:00
     */
    public static Long getWeekStartTime(Long timeStamp, TimeZone timeZone) {
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.setTimeZone(timeZone);
        calendar.setTimeInMillis(timeStamp);
        calendar.add(Calendar.YEAR, 0);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);// 设置为周日,当前日期即本周第一天
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取当月开始时间戳
     *
     * @param timeStamp 毫秒级时间戳
     */
    public static Long getMonthStartTime(Long timeStamp) {
        return getMonthStartTime(timeStamp, TimeZone.getDefault());
    }

    /**
     * 获取当月开始时间戳
     *
     * @param timeStamp 毫秒级时间戳
     * @param timeZone  如 GMT+8:00
     */
    public static Long getMonthStartTime(Long timeStamp, TimeZone timeZone) {
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.setTimeZone(timeZone);
        calendar.setTimeInMillis(timeStamp);
        calendar.add(Calendar.YEAR, 0);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期即本月第一天
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }
    public static boolean isToday(long when) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String time =  formatter.format(new Date(when));
        String curTime = formatter.format(new Date(System.currentTimeMillis()));
        return time.equals(curTime);

    }
}
