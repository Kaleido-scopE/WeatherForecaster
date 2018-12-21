package com.example.noah.weatherforecaster.utils;

import com.example.noah.weatherforecaster.R;

import java.lang.reflect.Field;

//RID管理类，可以将id以字符串形式表示
public class RIdManager {
    public static int getRes(String type, String name) {
        try {
            Field f = null;

            if (type.equals("drawable"))
                f = R.drawable.class.getField(name);
            if (type.equals("id"))
                f = R.id.class.getField(name);

            if (f != null)
                return f.getInt(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
