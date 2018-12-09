package com.example.noah.weatherforecaster.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.example.noah.weatherforecaster.entity.CityEntity;
import com.example.noah.weatherforecaster.fragment.SettingFragment;

public class SettingActivity extends SingleFragmentActivity {
    public static final int activityReqCode = 3;

    @Override
    protected Fragment createFragment() {
        CityEntity setLocation = null;
        String setUnit = "";
        boolean setNotification = true;

        if (getIntent() != null) {
            setLocation = (CityEntity) getIntent().getSerializableExtra("setLocation");
            setUnit = getIntent().getStringExtra("setUnit");
            setNotification = getIntent().getBooleanExtra("setNotification", true);
        }

        SettingFragment settingFragment = new SettingFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("setLocation", setLocation);
        bundle.putString("setUnit", setUnit);
        bundle.putBoolean("setNotification", setNotification);
        settingFragment.setArguments(bundle);
        return settingFragment;
    }

}
