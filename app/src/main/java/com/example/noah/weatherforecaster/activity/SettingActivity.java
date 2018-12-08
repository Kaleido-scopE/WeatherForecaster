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

        if (getIntent() != null) {
            setLocation = (CityEntity) getIntent().getSerializableExtra("setLocation");
            setUnit = getIntent().getStringExtra("setUnit");
        }

        SettingFragment settingFragment = new SettingFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("setLocation", setLocation);
        bundle.putString("setUnit", setUnit);
        settingFragment.setArguments(bundle);
        return settingFragment;
    }

}
