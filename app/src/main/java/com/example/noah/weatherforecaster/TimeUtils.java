package com.example.noah.weatherforecaster;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {
    private static DateFormat DATE = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private static DateFormat DATE_MIN = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
    private static DateFormat WEEK = new SimpleDateFormat("E", Locale.getDefault());

    public static Date dateFromDateStr(String dateStr) throws ParseException {
        return DATE.parse(dateStr);
    }

    public static Date dateFromDateTimeStr(String dateTimeStr) throws ParseException {
        return DATE_MIN.parse(dateTimeStr);
    }

    public static String weekFromDate(Date date) {
        return WEEK.format(date);
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(weekFromDate(new Date()));
    }
}
