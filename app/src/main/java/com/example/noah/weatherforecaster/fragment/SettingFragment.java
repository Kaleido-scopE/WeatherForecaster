package com.example.noah.weatherforecaster.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.noah.weatherforecaster.R;

public class SettingFragment extends Fragment {
    private LinearLayout curLocation; //当前位置
    private LinearLayout tempUnit; //温度单位
    private TextView curLocationText; //当前位置文本
    private TextView tempUnitText; //温度单位文本
    private TextView notificationState; //通知状态文本
    private CheckBox notificationBox; //通知勾选框

    private Intent resultIntent = new Intent();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_setting, container, false);
        initView(v);

        //设置默认resultIntent
        resultIntent.putExtra("curLocation", "Changsha");
        resultIntent.putExtra("unit", "摄氏");
        resultIntent.putExtra("notification", false);
        getActivity().setResult(0, resultIntent);

        return v;
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
                resultIntent.putExtra("curLocation", curLocationText.getText());
                getActivity().setResult(0, resultIntent);
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
                if (isChecked)
                    notificationState.setText("启用");
                else
                    notificationState.setText("关闭");
                resultIntent.putExtra("notification", isChecked);
                getActivity().setResult(0, resultIntent);
            }
        });
    }
}
