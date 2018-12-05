package com.example.noah.weatherforecaster.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import com.example.noah.weatherforecaster.fragment.SettingFragment;

public class SettingActivity extends SingleFragmentActivity {
    public static final int activityReqCode = 3;

    @Override
    protected Fragment createFragment() {
        return new SettingFragment();
    }

}
