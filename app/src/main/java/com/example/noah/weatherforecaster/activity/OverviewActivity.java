package com.example.noah.weatherforecaster.activity;

import android.support.v4.app.Fragment;
import com.example.noah.weatherforecaster.fragment.OverviewFragment;

public class OverviewActivity extends SingleFragmentActivity {
    public static final int activityReqCode = 1;

    @Override
    protected Fragment createFragment() {
        return new OverviewFragment();
    }

}
