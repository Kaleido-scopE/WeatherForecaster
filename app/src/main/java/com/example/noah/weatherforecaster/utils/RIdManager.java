package com.example.noah.weatherforecaster.utils;

import com.example.noah.weatherforecaster.R;

import java.lang.reflect.Field;

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
