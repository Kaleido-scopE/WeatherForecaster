package com.example.noah.weatherforecaster.activity;

import android.support.v4.app.Fragment;
import com.example.noah.weatherforecaster.fragment.CityFragment;

public class CityActivity extends SingleFragmentActivity {
    public static final int activityReqCode = 4;

    @Override
    protected Fragment createFragment() {
        return CityFragment.newInstance();
    }
}
