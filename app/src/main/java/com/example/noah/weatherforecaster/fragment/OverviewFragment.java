package com.example.noah.weatherforecaster.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.noah.weatherforecaster.R;
import com.example.noah.weatherforecaster.activity.DetailActivity;
import com.example.noah.weatherforecaster.activity.SettingActivity;
import com.example.noah.weatherforecaster.entity.WeatherEntity;
import com.example.noah.weatherforecaster.utils.RIdManager;
import com.example.noah.weatherforecaster.utils.TimeUtils;
import com.example.noah.weatherforecaster.utils.WeatherInfoFetcher;

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
    private LinearLayout[] dayLayout; //各日期的主体Layout，用于点击进入详细信息视图
    private View.OnClickListener layoutClickListener; //Layout的点击监听器，用于启动详细视图Fragment

    private WeatherEntity today; //当前天气
    private WeatherEntity[] forecast; //预报信息

    //-------------------------异步请求类-------------------------
    private class FetchItemsTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                today = WeatherInfoFetcher.getToday("changsha");
                forecast = WeatherInfoFetcher.getForecast("changsha");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void param) {
            super.onPostExecute(param);
            updateWeatherInfo();
        }
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
        View v = inflater.inflate(R.layout.fragment_overview, container, false);
        initView(v);
        new FetchItemsTask().execute();
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_overview, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.map_location:
                Log.d("OverviewFragment", "map location");
                return true;
            case R.id.settings:
                Intent intent = new Intent(getContext(), SettingActivity.class);
                startActivityForResult(intent, SettingActivity.activityReqCode);
                Log.d("OverviewFragment", "settings");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SettingActivity.activityReqCode) {
            Log.d("Overview loc", data.getStringExtra("curLocation"));
            Log.d("Overview unit", data.getStringExtra("unit"));
            Log.d("Overview ntf", data.getBooleanExtra("notification", true) + "");
        }
    }
    //-------------------------其他函数-------------------------

    /**
     * 初始化私有成员
     * @param v 当前fragment的View对象
     */
    private void initView(View v) {
        layoutClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickerId = 0;
                for (int i = 0; i < 7; i++)
                    if (RIdManager.getRes("id", "day" + i) == v.getId()) {
                        clickerId = i;
                        break;
                    }

                //带参数启动承载详细视图的Activity
                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("detail", forecast[clickerId]);
                startActivity(intent);
            }
        };

        dateNext = new TextView[7];
        weekNext = new TextView[7];
        minTemNext = new TextView[7];
        maxTemNext = new TextView[7];
        weatherText = new TextView[7];
        weatherIcon = new ImageView[7];
        dayLayout = new LinearLayout[7];

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
            dayLayout[i] = v.findViewById(RIdManager.getRes("id", "day" + i));
            dayLayout[i].setOnClickListener(layoutClickListener);
        }
    }

    /**
     * 刷新天气信息
     */
    private void updateWeatherInfo() {
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
            weatherIcon[i].setImageResource(RIdManager.getRes("drawable", "i" + forecast[i].getWeatherCode()));
            weatherText[i].setText(forecast[i].getWeatherName());
        }
    }
}
