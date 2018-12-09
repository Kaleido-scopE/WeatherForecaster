package com.example.noah.weatherforecaster.activity;

import android.support.v4.app.Fragment;
import com.example.noah.weatherforecaster.fragment.SettingFragment;

public class SettingActivity extends SingleFragmentActivity {
    public static final int activityReqCode = 3;

    @Override
    protected Fragment createFragment() {
        String notificationStr = "";

        if (getIntent() != null)
            notificationStr = getIntent().getStringExtra("notificationStr");

        return SettingFragment.newInstance(notificationStr);
    }

}
