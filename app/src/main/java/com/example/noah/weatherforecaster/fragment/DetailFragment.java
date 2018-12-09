package com.example.noah.weatherforecaster.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.noah.weatherforecaster.R;
import com.example.noah.weatherforecaster.activity.SettingActivity;
import com.example.noah.weatherforecaster.entity.WeatherEntity;
import com.example.noah.weatherforecaster.utils.RIdManager;
import com.example.noah.weatherforecaster.utils.SettingUtils;
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

    //新建Fragment实例
    public static DetailFragment newInstance(WeatherEntity weatherEntity) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("Detail", weatherEntity);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    //-------------------------生命周期函数-------------------------
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail, container, false);
        initView(v);

        if (getArguments() != null)
            detail = (WeatherEntity) getArguments().get("Detail");

        updateDetail();
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_detail, menu);
        if (SettingUtils.getIsTwoPane()) //若是平板视图，则隐藏设置菜单
            menu.findItem(R.id.settings).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                createShareList();
                return true;
            case R.id.settings:
                Intent intent = new Intent(getContext(), SettingActivity.class);
                startActivityForResult(intent, SettingActivity.activityReqCode);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SettingActivity.activityReqCode)
            getActivity().finish();
    }

    //-------------------------其他函数-------------------------

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

    /**
     * 拼接整个UI上的信息
     * @return 拼接好的字符串
     */
    private String contacUIText() {
        StringBuilder builder = new StringBuilder();

        //拼接地点
        builder.append("地区：").append(detail.getLocation()).append('\n');

        //拼接日期
        builder.append("日期：").append(date.getText()).append(week.getText()).append('\n');

        //拼接温度
        builder.append("温度：").append(maxTemp.getText().toString().substring(1)).append('/').append(minTemp.getText().toString().substring(1)).append('\n');

        //拼接天气
        builder.append("天气：").append(weatherText.getText()).append('\n');

        //拼接其他信息
        builder.append(humidity.getText()).append('\n')
                .append(atomPressure.getText()).append('\n')
                .append(windSpeed.getText()).append('\n')
                .append(windScale.getText()).append('\n')
                .append(windDir.getText()).append('\n')
                .append(precipitation.getText()).append('\n')
                .append(precipRate.getText());

        return builder.toString();
    }

    /**
     * 利用隐式Intent启动分享应用选择列表
     */
    private void createShareList() {
        String title = detail.getLocation() + " " + date.getText().toString() + "的天气";
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, contacUIText());
        intent = Intent.createChooser(intent, "发送天气");
        startActivity(intent);
    }
}
