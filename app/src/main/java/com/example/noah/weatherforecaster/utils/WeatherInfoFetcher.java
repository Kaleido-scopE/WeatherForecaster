package com.example.noah.weatherforecaster.utils;

import android.net.Uri;
import android.util.Log;
import com.example.noah.weatherforecaster.entity.CityEntity;
import com.example.noah.weatherforecaster.entity.WeatherEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
                forecastInfo[i].setLatitude(basic.getDouble("lat"));
                forecastInfo[i].setLongitude(basic.getDouble("lon"));
                forecastInfo[i].setWeatherCode(dailyForecast.getJSONObject(i).getInt("cond_code_d"));
                forecastInfo[i].setWeatherName(dailyForecast.getJSONObject(i).getString("cond_txt_d"));
                forecastInfo[i].setMinDegree(dailyForecast.getJSONObject(i).getInt("tmp_min"));
                forecastInfo[i].setMaxDegree(dailyForecast.getJSONObject(i).getInt("tmp_max"));
                forecastInfo[i].setHumidity(dailyForecast.getJSONObject(i).getInt("hum"));
                forecastInfo[i].setAtmoPressure(dailyForecast.getJSONObject(i).getInt("pres"));
                forecastInfo[i].setWindSpeed(dailyForecast.getJSONObject(i).getInt("wind_spd"));
                forecastInfo[i].setWindScale(dailyForecast.getJSONObject(i).getString("wind_sc"));
                forecastInfo[i].setWindDir(dailyForecast.getJSONObject(i).getString("wind_dir"));
                forecastInfo[i].setPrecipitation(dailyForecast.getJSONObject(i).getInt("pcpn"));
                forecastInfo[i].setPrecipRate(dailyForecast.getJSONObject(i).getInt("pop"));
                forecastInfo[i].setDate(TimeUtils.dateFromDateStr(dailyForecast.getJSONObject(i).getString("date")));
            }

            return forecastInfo;
        } catch (Exception e) {
            return forecastInfo;
        }
    }

    /**
     * 获取热门城市列表
     * @param number 列表长度，可选1-50
     * @param group 区域，可选国内(cn)、海外(overseas)、全球(world)
     * @return 城市实体列表
     */
    public static List<CityEntity> getCityList(int number, String group) {
        String endPoint = "https://search.heweather.com/top";
        String url = Uri.parse(endPoint).buildUpon()
                .appendQueryParameter("number", String.valueOf(number))
                .appendQueryParameter("group", group)
                .appendQueryParameter("key", key)
                .build().toString();

        List<CityEntity> cities = new ArrayList<>();

        try {
            CityEntity entity;
            String res = getResponseStr(url);
            JSONObject innerJSONObject = new JSONObject(res).getJSONArray("HeWeather6").getJSONObject(0);
            JSONArray basic = innerJSONObject.getJSONArray("basic");

            for (int i = 0; i < basic.length(); i++) {
                entity = new CityEntity();
                entity.setLocation(basic.getJSONObject(i).getString("location"));
                entity.setLatitude(basic.getJSONObject(i).getDouble("lat"));
                entity.setLongitude(basic.getJSONObject(i).getDouble("lon"));
                cities.add(entity);
            }

            return cities;
        } catch (Exception e) {
            return cities;
        }
    }
}
