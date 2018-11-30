package com.example.noah.weatherforecaster.entity;

import java.util.Date;

public class WeatherEntity {
    private String location; //所在位置
    private int weatherCode; //天气代码
    private String weatherName; //天气名称
    private int minDegree; //最低温度
    private int maxDegree; //最高温度
    private int currentDegree; //当前温度
    private Date date; //对应日期

    public WeatherEntity() {
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(int weatherCode) {
        this.weatherCode = weatherCode;
    }

    public String getWeatherName() {
        return weatherName;
    }

    public void setWeatherName(String weatherName) {
        this.weatherName = weatherName;
    }

    public int getMinDegree() {
        return minDegree;
    }

    public void setMinDegree(int minDegree) {
        this.minDegree = minDegree;
    }

    public int getMaxDegree() {
        return maxDegree;
    }

    public void setMaxDegree(int maxDegree) {
        this.maxDegree = maxDegree;
    }

    public int getCurrentDegree() {
        return currentDegree;
    }

    public void setCurrentDegree(int currentDegree) {
        this.currentDegree = currentDegree;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
