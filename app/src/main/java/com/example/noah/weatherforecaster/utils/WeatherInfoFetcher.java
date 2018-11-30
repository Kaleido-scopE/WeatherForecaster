package com.example.noah.weatherforecaster.utils;

import android.net.Uri;
import com.example.noah.weatherforecaster.entity.WeatherEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class WeatherInfoFetcher {
    private static final String key = "e2ac4a73247349b1956710b971f98c16";

    private static String getResponseStr(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = conn.getInputStream();

            int bytesRead;
            byte[] bytes = new byte[1024];
            while ((bytesRead = in.read(bytes)) > 0)
                out.write(bytes, 0, bytesRead);
            out.close();

            return new String(out.toByteArray());
        } finally {
            conn.disconnect();
        }
    }

    /**
     * 获取今天的实时天气信息（包括当前温度），显示在APP上部
     * @return 对应Entity
     */
    public static WeatherEntity getToday(String location) {
        String endPoint = "https://free-api.heweather.com/s6/weather/now";
        String url = Uri.parse(endPoint).buildUpon()
                    .appendQueryParameter("location", location)
                    .appendQueryParameter("key", key)
                    .build().toString();

        try {
            String res = getResponseStr(url);
            JSONObject innerJSONObject = new JSONObject(res).getJSONArray("HeWeather6").getJSONObject(0);

            JSONObject basic = innerJSONObject.getJSONObject("basic");
            JSONObject now = innerJSONObject.getJSONObject("now");
            JSONObject update = innerJSONObject.getJSONObject("update");

            WeatherEntity entity = new WeatherEntity();
            entity.setLocation(basic.getString("location"));
            entity.setWeatherCode(now.getInt("cond_code"));
            entity.setWeatherName(now.getString("cond_txt"));
            entity.setCurrentDegree(now.getInt("tmp"));
            entity.setDate(TimeUtils.dateFromDateTimeStr(update.getString("loc")));//更新时间

            return entity;
        } catch (Exception e) {
            return new WeatherEntity();
        }
    }

    /**
     * 获取7天的预报（包括最高最低温度），显示在APP下部
     * @return 对应Entity数组，长度固定为7
     */
    public static WeatherEntity[] getForecast(String location) {
        String endPoint = "https://free-api.heweather.com/s6/weather/forecast";
        String url = Uri.parse(endPoint).buildUpon()
                .appendQueryParameter("location", location)
                .appendQueryParameter("key", key)
                .build().toString();

        WeatherEntity[] forecastInfo = new WeatherEntity[7];

        try {
            String res = getResponseStr(url);
            JSONObject innerJSONObject = new JSONObject(res).getJSONArray("HeWeather6").getJSONObject(0);

            JSONObject basic = innerJSONObject.getJSONObject("basic");
            JSONArray dailyForecast = innerJSONObject.getJSONArray("daily_forecast");

            for (int i = 0; i < 7; i++) {
                forecastInfo[i] = new WeatherEntity();
                forecastInfo[i].setLocation(basic.getString("location"));
                forecastInfo[i].setWeatherCode(dailyForecast.getJSONObject(i).getInt("cond_code_d"));
                forecastInfo[i].setWeatherName(dailyForecast.getJSONObject(i).getString("cond_txt_d"));
                forecastInfo[i].setMinDegree(dailyForecast.getJSONObject(i).getInt("tmp_min"));
                forecastInfo[i].setMaxDegree(dailyForecast.getJSONObject(i).getInt("tmp_max"));
                forecastInfo[i].setDate(TimeUtils.dateFromDateStr(dailyForecast.getJSONObject(i).getString("date")));
            }

            return forecastInfo;
        } catch (Exception e) {
            return forecastInfo;
        }
    }
}
