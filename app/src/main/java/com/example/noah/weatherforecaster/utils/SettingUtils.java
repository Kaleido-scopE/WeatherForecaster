package com.example.noah.weatherforecaster.utils;

import com.example.noah.weatherforecaster.entity.CityEntity;

//管理全局的设置状态
public class SettingUtils {
    private static CityEntity setLocation = new CityEntity();
    private static String setTempUnit = "摄氏";
    private static Boolean setNotificationState = true;
    private static Boolean isTwoPane = false;

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

    public static Boolean getIsTwoPane() {
        return isTwoPane;
    }

    public static void setIsTwoPane(Boolean isTwoPane) {
        SettingUtils.isTwoPane = isTwoPane;
    }

    /**
     * 温度单位转换
     * @param toType 目标类型 ("摄氏"/"华氏")
     * @param value 原始温度
     * @return 转化结果
     */
    public static int transformTemperature(String toType, int value) {
        int res = 0;

        //四舍五入计算
        if (toType.equals("摄氏"))
            res = (int) Math.round((value - 32) / 1.8);;
        if (toType.equals("华氏"))
            res = (int) Math.round(value * 1.8 + 32);
        return res;
    }
}
