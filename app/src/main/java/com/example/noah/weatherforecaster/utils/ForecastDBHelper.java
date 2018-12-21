package com.example.noah.weatherforecaster.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.noah.weatherforecaster.entity.WeatherEntity;

import java.util.Date;

//数据库管理类
public class ForecastDBHelper extends SQLiteOpenHelper {

    private static final String CREATE_FORECAST_INFO = "create table forecast_info ("
                                                    + "id integer primary key autoincrement, "
                                                    + "location text, "
                                                    + "latitude real, "
                                                    + "longitude real, "
                                                    + "weather_code integer, "
                                                    + "weather_name text, "
                                                    + "min_degree integer, "
                                                    + "max_degree integer, "
                                                    + "humidity integer, "
                                                    + "atom_pressure integer, "
                                                    + "wind_speed integer, "
                                                    + "wind_scale text, "
                                                    + "wind_dir text, "
                                                    + "precipitation integer, "
                                                    + "precip_rate integer, "
                                                    + "date integer)";

    private Context mContext;

    public ForecastDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_FORECAST_INFO);

        //在表中插入默认数据
        ContentValues contentValues = new ContentValues();
        contentValues.put("location", "Beijing");
        contentValues.put("latitude", 39.90498734);
        contentValues.put("longitude", 116.40528870);
        contentValues.put("weather_code", 103);
        contentValues.put("weather_name", "晴间多云");
        contentValues.put("min_degree", 8);
        contentValues.put("max_degree", 16);
        contentValues.put("humidity", 57);
        contentValues.put("atom_pressure", 1020);
        contentValues.put("wind_speed", 5);
        contentValues.put("wind_scale", "微风");
        contentValues.put("wind_dir", "无持续风向");
        contentValues.put("precipitation", 0);
        contentValues.put("precip_rate", 0);
        contentValues.put("date", System.currentTimeMillis());

        for (int i = 0; i < 7; i++)
            db.insert("forecast_info", null, contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 获得数据库中的预报信息
     * @return 预报信息数组
     */
    public WeatherEntity[] loadSavedForecastInfo() {
        SQLiteDatabase db = getReadableDatabase();
        WeatherEntity[] forecast = new WeatherEntity[7];
        String location;
        double latitude;
        double longitude;
        int weatherCode;
        String weatherName;
        int minDegree;
        int maxDegree;
        int humidity;
        int atomPressure;
        int windSpeed;
        String windScale;
        String windDir;
        int precipitation;
        int precipRate;
        long date;

        int ind = 0;
        Cursor cursor = db.query("forecast_info", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                location = cursor.getString(cursor.getColumnIndex("location"));
                latitude = cursor.getInt(cursor.getColumnIndex("latitude"));
                longitude = cursor.getInt(cursor.getColumnIndex("longitude"));
                weatherCode = cursor.getInt(cursor.getColumnIndex("weather_code"));
                weatherName = cursor.getString(cursor.getColumnIndex("weather_name"));
                minDegree = cursor.getInt(cursor.getColumnIndex("min_degree"));
                maxDegree = cursor.getInt(cursor.getColumnIndex("max_degree"));
                humidity = cursor.getInt(cursor.getColumnIndex("humidity"));
                atomPressure = cursor.getInt(cursor.getColumnIndex("atom_pressure"));
                windSpeed = cursor.getInt(cursor.getColumnIndex("wind_speed"));
                windScale = cursor.getString(cursor.getColumnIndex("wind_scale"));
                windDir = cursor.getString(cursor.getColumnIndex("wind_dir"));
                precipitation = cursor.getInt(cursor.getColumnIndex("precipitation"));
                precipRate = cursor.getInt(cursor.getColumnIndex("precip_rate"));
                date = cursor.getLong(cursor.getColumnIndex("date"));
                forecast[ind++] = new WeatherEntity(location, latitude, longitude,
                                                weatherCode, weatherName, minDegree,
                                                maxDegree, 0, humidity, atomPressure,
                                                windSpeed, windScale, windDir,
                                                precipitation, precipRate, new Date(date));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return forecast;
    }

    /**
     * 将预报信息存入数据库中
     * @param forecast 传入的预报信息
     */
    public void updateForecastInfo(WeatherEntity[] forecast) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //建表时已经加入默认数据，直接更新即可
        for (int i = 0; i < forecast.length; i++) {
            contentValues.put("location", forecast[i].getLocation());
            contentValues.put("latitude", forecast[i].getLatitude());
            contentValues.put("longitude", forecast[i].getLongitude());
            contentValues.put("weather_code", forecast[i].getWeatherCode());
            contentValues.put("weather_name", forecast[i].getWeatherName());
            contentValues.put("min_degree", forecast[i].getMinDegree());
            contentValues.put("max_degree", forecast[i].getMaxDegree());
            contentValues.put("humidity", forecast[i].getHumidity());
            contentValues.put("atom_pressure", forecast[i].getAtmoPressure());
            contentValues.put("wind_speed", forecast[i].getWindSpeed());
            contentValues.put("wind_scale", forecast[i].getWindScale());
            contentValues.put("wind_dir", forecast[i].getWindDir());
            contentValues.put("precipitation", forecast[i].getPrecipitation());
            contentValues.put("precip_rate", forecast[i].getPrecipRate());
            contentValues.put("date", forecast[i].getDate().getTime());
            db.update("forecast_info", contentValues, "id = ?", new String[] {String.valueOf(i + 1)});
            contentValues.clear();
        }
    }
}
