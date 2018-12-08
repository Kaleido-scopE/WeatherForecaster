package com.example.noah.weatherforecaster.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.noah.weatherforecaster.R;
import com.example.noah.weatherforecaster.activity.CityActivity;
import com.example.noah.weatherforecaster.entity.CityEntity;

public class SettingFragment extends Fragment {
    private LinearLayout curLocation; //当前位置
    private LinearLayout tempUnit; //温度单位
    private TextView curLocationText; //当前位置文本
    private TextView tempUnitText; //温度单位文本
    private TextView notificationState; //通知状态文本
    private CheckBox notificationBox; //通知勾选框

    private CityEntity setLocation; //当前设置的位置
    private String setTempUnit; //当前设置的温度单位

    private Intent resultIntent = new Intent();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_setting, container, false);
        initView(v);

        if (getArguments() != null) {
            setLocation = (CityEntity) getArguments().getSerializable("setLocation");
            setTempUnit = getArguments().getString("setUnit");
        }

        curLocationText.setText(setLocation.getLocation());
        tempUnitText.setText(setTempUnit);

        //设置默认resultIntent
        resultIntent.putExtra("curLocation", setLocation);
        resultIntent.putExtra("unit", setTempUnit);
        resultIntent.putExtra("notification", true);
        getActivity().setResult(0, resultIntent);

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CityActivity.activityReqCode) {
            CityEntity cityEntity = (CityEntity) data.getSerializableExtra("location");
            curLocationText.setText(cityEntity.getLocation());

            resultIntent.putExtra("curLocation", cityEntity);
            getActivity().setResult(0, resultIntent);
        }
    }

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
                intent.putExtra("curLocation", setLocation);
                startActivityForResult(intent, CityActivity.activityReqCode);
            }
        });

        tempUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curUnit = tempUnitText.getText().toString();
                if (curUnit.equals("摄氏")) {
                    tempUnitText.setText("华氏");
                    resultIntent.putExtra("unit", "华氏");
                }
                if (curUnit.equals("华氏")) {
                    tempUnitText.setText("摄氏");
                    resultIntent.putExtra("unit", "摄氏");
                }
                getActivity().setResult(0, resultIntent);
            }
        });

        notificationBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    notificationState.setText("启用");
                }
                else {
                    notificationState.setText("关闭");
                }
                resultIntent.putExtra("notification", isChecked);
                getActivity().setResult(0, resultIntent);
            }
        });
    }
}
