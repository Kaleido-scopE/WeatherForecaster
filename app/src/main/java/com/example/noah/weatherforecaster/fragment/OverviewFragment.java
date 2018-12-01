package com.example.noah.weatherforecaster.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.noah.weatherforecaster.R;
import com.example.noah.weatherforecaster.entity.WeatherEntity;
import com.example.noah.weatherforecaster.utils.RIdManager;
import com.example.noah.weatherforecaster.utils.TimeUtils;
import com.example.noah.weatherforecaster.utils.WeatherInfoFetcher;

import java.util.HashMap;
import java.util.Map;

public class OverviewFragment extends Fragment {
    private TextView dateToday; //今天的日期
    private TextView curTemToday; //当前温度
    private TextView rangeTemToday; //今天的最高最低温度
    private TextView location; //当前位置
    private TextView updateTime; //当前天气更新时间
    private TextView[] dateNext; //预报日期（月-日）数组
    private TextView[] weekNext; //预报日期（星期）数组
    private TextView[] minTemNext; //预报日期最低温度数组
    private TextView[] maxTemNext; //预报日期最高温度数组
    private TextView[] weatherText; //天气描述数组
    private ImageView[] weatherIcon; //天气图标数组


    //-------------------------异步请求类-------------------------
    private class FetchItemsTask extends AsyncTask<Void, Void, Map<String, Object>> {
        @Override
        protected Map<String, Object> doInBackground(Void... params) {
            try {
                Map<String, Object> map = new HashMap<>();
                WeatherEntity today = WeatherInfoFetcher.getToday("changsha");
                WeatherEntity[] forecast = WeatherInfoFetcher.getForecast("changsha");
                map.put("today", today);
                map.put("forecast", forecast);
                return map;
            } catch (Exception e) {
                Log.e("OF", "Exception");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Map<String, Object> map) {
            super.onPostExecute(map);
            WeatherEntity today = new WeatherEntity();
            WeatherEntity[] forecast = new WeatherEntity[7];
            if (map.containsKey("today"))
                today = (WeatherEntity) map.get("today");
            if (map.containsKey("forecast"))
                forecast = (WeatherEntity[]) map.get("forecast");
            updateWeatherInfo(today, forecast);
        }
    }

    //-------------------------生命周期函数-------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_overview, container, false);
        initView(v);
        new FetchItemsTask().execute();
        return v;
    }

    //-------------------------其他函数-------------------------

    /**
     * 初始化私有成员
     * @param v 当前fragment的View对象
     */
    private void initView(View v) {
        dateNext = new TextView[7];
        weekNext = new TextView[7];
        minTemNext = new TextView[7];
        maxTemNext = new TextView[7];
        weatherText = new TextView[7];
        weatherIcon = new ImageView[7];

        dateToday = v.findViewById(R.id.date_t);
        curTemToday = v.findViewById(R.id.cur_t);
        rangeTemToday = v.findViewById(R.id.ran_t);
        location = v.findViewById(R.id.location);
        updateTime = v.findViewById(R.id.update_time);
        for (int i = 0; i < 7; i++) {
            if (i != 0) {
                dateNext[i] = v.findViewById(RIdManager.getRes("id", "md" + i));
                weekNext[i] = v.findViewById(RIdManager.getRes("id", "week" + i));
                minTemNext[i] = v.findViewById(RIdManager.getRes("id", "min_t" + i));
                maxTemNext[i] = v.findViewById(RIdManager.getRes("id", "max_t" + i));
            }
            weatherIcon[i] = v.findViewById(RIdManager.getRes("id", "w_icon" + i));
            weatherText[i] = v.findViewById(RIdManager.getRes("id", "w_text" + i));
        }
    }

    /**
     * 刷新天气信息
     * @param today 当前天气
     * @param forecast 预报天气数组
     */
    private void updateWeatherInfo(WeatherEntity today, WeatherEntity[] forecast) {
        //设置今日日期
        String dateTodayStr = TimeUtils.mdFromDate(forecast[0].getDate()) + TimeUtils.weekFromDate(forecast[0].getDate());
        dateToday.setText(dateTodayStr);

        //设置当前温度
        String curTemTodayStr = " " + today.getCurrentDegree() + "°";
        curTemToday.setText(curTemTodayStr);

        //设置当前的位置
        location.setText(today.getLocation());

        //设置更新时间
        String updateTimeStr = "   更新时间: " + TimeUtils.hmFromDate(today.getDate());
        updateTime.setText(updateTimeStr);

        //设置今天的最高最低温度
        String rangeTemTodayStr = forecast[0].getMaxDegree() + "°/" + forecast[0].getMinDegree() + "°";
        rangeTemToday.setText(rangeTemTodayStr);

        //批量设置天气图标、天气描述、预报信息等
        String minTem, maxTem;
        for (int i = 0; i < 7; i++) {
            if (i != 0) {
                minTem = forecast[i].getMinDegree() + "°";
                maxTem = forecast[i].getMaxDegree() + "°";
                dateNext[i].setText(TimeUtils.mdFromDate(forecast[i].getDate()));
                weekNext[i].setText(TimeUtils.weekFromDate(forecast[i].getDate()));
                minTemNext[i].setText(minTem);
                maxTemNext[i].setText(maxTem);
            }
            weatherIcon[i].setImageDrawable(getResources().
                    getDrawable(RIdManager.getRes("drawable", "i" + forecast[i].getWeatherCode())));
            weatherText[i].setText(forecast[i].getWeatherName());
        }
    }
}
