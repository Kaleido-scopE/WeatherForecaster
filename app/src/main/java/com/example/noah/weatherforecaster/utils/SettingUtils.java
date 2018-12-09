package com.example.noah.weatherforecaster.utils;

import com.example.noah.weatherforecaster.entity.CityEntity;

public class SettingUtils {
    private static CityEntity setLocation = new CityEntity();
    private static String setTempUnit = "摄氏";
    private static Boolean setNotificationState = true;

    public static CityEntity getSetLocation() {
        return setLocation;
    }

    public static void setSetLocation(CityEntity setLocation) {
        SettingUtils.setLocation = setLocation;
    }

    public static String getSetTempUnit() {
        return setTempUnit;
    }

    public static void setSetTempUnit(String setTempUnit) {
        SettingUtils.setTempUnit = setTempUnit;
    }

    public static Boolean getSetNotificationState() {
        return setNotificationState;
    }

    public static void setSetNotificationState(Boolean setNotificationState) {
        SettingUtils.setNotificationState = setNotificationState;
    }

    /**
     * 温度单位转换
     * @param toType 目标类型 ("摄氏"/"华氏")
     * @param value 原始温度
     * @return 转化结果
     */
    public static int transformTemperature(String toType, int value) {
        int res = 0;
        if (toType.equals("摄氏"))
            res = Double.valueOf((value - 32) / 1.8).intValue();
        if (toType.equals("华氏"))
            res = Double.valueOf(value * 1.8 + 32).intValue();
        return res;
    }
}
