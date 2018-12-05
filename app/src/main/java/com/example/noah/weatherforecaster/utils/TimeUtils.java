package com.example.noah.weatherforecaster.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {
    private static DateFormat DATE = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    private static DateFormat DATE_MIN = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
    private static DateFormat WEEK = new SimpleDateFormat("E", Locale.CHINA);
    private static DateFormat MONTH = new SimpleDateFormat("MM月", Locale.CHINA);
    private static DateFormat DAY = new SimpleDateFormat("dd日", Locale.CHINA);
    private static DateFormat HM = new SimpleDateFormat("HH:mm", Locale.CHINA);

    public static Date dateFromDateStr(String dateStr) throws ParseException {
        return DATE.parse(dateStr);
    }

    public static Date dateFromDateTimeStr(String dateTimeStr) throws ParseException {
        return DATE_MIN.parse(dateTimeStr);
    }

    public static String weekFromDate(Date date) {
        return WEEK.format(date);
    }

    public static String mdFromDate(Date date) {
        String month =  MONTH.format(date);
        String day = DAY.format(date);
        if (day.charAt(0) == '0')
            day = day.substring(1);
        return month + day;
    }

    public static String hmFromDate(Date date) {
        return HM.format(date);
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(hmFromDate(new Date()));
    }
}
