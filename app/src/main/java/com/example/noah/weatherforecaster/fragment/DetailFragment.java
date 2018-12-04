package com.example.noah.weatherforecaster.fragment;


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


public class DetailFragment extends Fragment {
    private TextView week; //选中日期的星期
    private TextView date; //选中的日期
    private TextView maxTemp; //选中日期的最高温度
    private TextView minTemp; //选中日期的最低温度
    private TextView humidity; //湿度
    private TextView atomPressure; //大气压强
    private TextView windSpeed; //风速
    private TextView windScale; //风力等级
    private TextView windDir; //风向
    private TextView precipitation; //降水量
    private TextView precipRate; //降水概率
    private TextView weatherText; //天气描述
    private ImageView weatherIcon; //天气图标

    private WeatherEntity detail; //承载详情的entity

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail, container, false);
        initView(v);

        if (getArguments() != null)
            detail = (WeatherEntity) getArguments().get("detail");

        updateDetail();
        return v;
    }

    /**
     * 初始化私有成员
     * @param v 当前fragment的View对象
     */
    private void initView(View v) {
        week = v.findViewById(R.id.d_week);
        date = v.findViewById(R.id.d_md);
        maxTemp = v.findViewById(R.id.d_max_t);
        minTemp = v.findViewById(R.id.d_min_t);
        humidity = v.findViewById(R.id.d_hum);
        atomPressure = v.findViewById(R.id.d_pres);
        windSpeed = v.findViewById(R.id.d_wind_spd);
        windScale = v.findViewById(R.id.d_wind_sc);
        windDir = v.findViewById(R.id.d_wind_dir);
        precipitation = v.findViewById(R.id.d_pcpn);
        precipRate = v.findViewById(R.id.d_pop);
        weatherText = v.findViewById(R.id.d_wtext);
        weatherIcon = v.findViewById(R.id.d_wicon);
    }

    /**
     * 更新视图
     */
    private void updateDetail() {
        week.setText(TimeUtils.weekFromDate(detail.getDate()));

        date.setText(TimeUtils.mdFromDate(detail.getDate()));

        String maxTempStr = " " + detail.getMaxDegree() + "°";
        maxTemp.setText(maxTempStr);

        String minTempStr = detail.getMinDegree() + "°";
        minTemp.setText(minTempStr);

        String humidityStr = "湿度: " + detail.getHumidity() + " %";
        humidity.setText(humidityStr);

        String atomPressureStr = "大气压强: " + detail.getAtmoPressure() + " hPa";
        atomPressure.setText(atomPressureStr);

        String windSpeedStr = "风速: " + detail.getWindSpeed() + " km/h";
        windSpeed.setText(windSpeedStr);

        String windScaleStr = "风力: " + detail.getWindScale() + " 级";
        windScale.setText(windScaleStr);

        String windDirStr = "风向: " + detail.getWindDir();
        windDir.setText(windDirStr);

        String precipitationStr = "降水量: " + detail.getPrecipitation() + " mm";
        precipitation.setText(precipitationStr);

        String precipRateStr = "降水概率: " + detail.getPrecipRate() + " %";
        precipRate.setText(precipRateStr);

        weatherText.setText(detail.getWeatherName());

        weatherIcon.setImageResource(RIdManager.getRes("drawable", "i" + detail.getWeatherCode()));
    }
}
