package com.liyuji.app.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author L
 */
public class MyTimeUtils {

    /**
     * Ymd
     * @param date
     * @return
     */
    public static String DateToYmd(Date date) {
        //加8个时区
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        Date getDate = calendar.getTime();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(getDate);
    }

    /**
     * YmdHm
     * @param date
     * @return
     */
    public static String DateToYmdHm(Date date) {
        //加8个时区
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 8);
        Date getDate = calendar.getTime();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(getDate);
    }

    /**
     * YmdHms
     * @param date
     * @return
     */
    public static String DateToYmdHms(Date date) {
        //加8个时区
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 8);
        Date getDate = calendar.getTime();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(getDate);
    }

    /**
     * TimePickerYmd
     * @param date
     * @return
     */
    public static String DateToTimePickerYmd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Date getDate = calendar.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(getDate);
    }

    /**
     * PickerYmdHm
     * @param date
     * @return
     */
    public static String DateToTimePickerYmdHm(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Date getDate = calendar.getTime();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        return format.format(getDate);
    }
}
