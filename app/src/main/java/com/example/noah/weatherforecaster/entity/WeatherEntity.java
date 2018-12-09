package com.example.noah.weatherforecaster.entity;

import java.io.Serializable;
import java.util.Date;

public class WeatherEntity implements Serializable {
    private String location; //所在位置
    private double latitude; //所在纬度
    private double longitude; //所在经度
    private int weatherCode; //天气代码
    private String weatherName; //天气名称
    private int minDegree; //最低温度
    private int maxDegree; //最高温度
    private int currentDegree; //当前温度
    private int humidity; //湿度
    private int atmoPressure; //大气压强
    private int windSpeed; //风速
    private String windScale; //风力等级
    private String windDir; //风向
    private int precipitation; //降水量
    private int precipRate; //降水概率
    private Date date; //对应日期

    public WeatherEntity() {
    }

    public WeatherEntity(String location, double latitude, double longitude, int weatherCode, String weatherName, int minDegree, int maxDegree, int currentDegree, int humidity, int atmoPressure, int windSpeed, String windScale, String windDir, int precipitation, int precipRate, Date date) {
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.weatherCode = weatherCode;
        this.weatherName = weatherName;
        this.minDegree = minDegree;
        this.maxDegree = maxDegree;
        this.currentDegree = currentDegree;
        this.humidity = humidity;
        this.atmoPressure = atmoPressure;
        this.windSpeed = windSpeed;
        this.windScale = windScale;
        this.windDir = windDir;
        this.precipitation = precipitation;
        this.precipRate = precipRate;
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getAtmoPressure() {
        return atmoPressure;
    }

    public void setAtmoPressure(int atmoPressure) {
        this.atmoPressure = atmoPressure;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindScale() {
        return windScale;
    }

    public void setWindScale(String windScale) {
        this.windScale = windScale;
    }

    public String getWindDir() {
        return windDir;
    }

    public void setWindDir(String windDir) {
        this.windDir = windDir;
    }

    public int getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(int precipitation) {
        this.precipitation = precipitation;
    }

    public int getPrecipRate() {
        return precipRate;
    }

    public void setPrecipRate(int precipRate) {
        this.precipRate = precipRate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
