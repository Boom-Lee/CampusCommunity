package com.liyuji.app.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 * @author L
 */
public class MyTimeUtils {

    /**
     * 将日期转换成Ymd
     * @param date
     * @return
     */
    public static String dateToYmd(Date date) {
        //加8个时区
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        Date getDate = calendar.getTime();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(getDate);
    }

    /**
     * 将日期转换成YmdHm
     * @param date
     * @return
     */
    public static String dateToYmdHm(Date date) {
        //加8个时区
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 8);
        Date getDate = calendar.getTime();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(getDate);
    }

    /**
     * 将日期转换成YmdHms
     * @param date
     * @return
     */
    public static String dateToYmdHms(Date date) {
        //加8个时区
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 8);
        Date getDate = calendar.getTime();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(getDate);
    }

    /**
     * TimePicker将日期转换成Ymd
     * @param date
     * @return
     */
    public static String dateToTimePickerYmd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Date getDate = calendar.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(getDate);
    }

    /**
     * Picker将日期转换成YmdHm
     * @param date
     * @return
     */
    public static String dateToTimePickerYmdHm(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Date getDate = calendar.getTime();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        return format.format(getDate);
    }
}
