package com.example.noah.weatherforecaster.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.noah.weatherforecaster.R;
import com.example.noah.weatherforecaster.activity.CityActivity;
import com.example.noah.weatherforecaster.service.NotificationService;
import com.example.noah.weatherforecaster.utils.SettingUtils;

public class SettingFragment extends Fragment {
    private LinearLayout curLocation; //当前位置
    private LinearLayout tempUnit; //温度单位
    private TextView curLocationText; //当前位置文本
    private TextView tempUnitText; //温度单位文本
    private TextView notificationState; //通知状态文本
    private CheckBox notificationBox; //通知勾选框

    private String notificationStr; //通知字符串

    //新建Fragment实例
    public static SettingFragment newInstance(String notification) {
        SettingFragment settingFragment = new SettingFragment();
        Bundle bundle = new Bundle();
        bundle.putString("notificationStr", notification);
        settingFragment.setArguments(bundle);
        return settingFragment;
    }

    //-------------------------生命周期函数-------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_setting, container, false);
        initView(v);

        if (getArguments() != null)
            notificationStr = getArguments().getString("notificationStr");

        curLocationText.setText(SettingUtils.getSetLocation().getLocation());
        tempUnitText.setText(SettingUtils.getSetTempUnit());
        notificationBox.setChecked(SettingUtils.getSetNotificationState());
        if (SettingUtils.getSetNotificationState())
            notificationState.setText("启用");
        else
            notificationState.setText("关闭");

        return v;
    }

    //-------------------------其他函数-------------------------

    /**
     * 初始化私有成员
     * @param v 当前fragment的View对象
     */
    private void initView(View v) {
        curLocation = v.findViewById(R.id.cur_location);
        tempUnit = v.findViewById(R.id.t_unit);
        curLocationText = v.findViewById(R.id.cur_location_txt);
        tempUnitText = v.findViewById(R.id.t_unit_txt);
        notificationState = v.findViewById(R.id.ntf_state);
        notificationBox = v.findViewById(R.id.ntf_box);

        curLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CityActivity.class);
                startActivity(intent);
            }
        });

        tempUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curUnit = SettingUtils.getSetTempUnit();

                if (curUnit.equals("摄氏"))
                    SettingUtils.setSetTempUnit("华氏");
                if (curUnit.equals("华氏"))
                    SettingUtils.setSetTempUnit("摄氏");

                tempUnitText.setText(SettingUtils.getSetTempUnit());
            }
        });

        notificationBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SettingUtils.setSetNotificationState(isChecked);

                if (isChecked)
                    notificationState.setText("启用");
                else
                    notificationState.setText("关闭");

                NotificationService.setServiceAlarm(getActivity(), isChecked, notificationStr);
            }
        });
    }
}
