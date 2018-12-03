package com.example.noah.weatherforecaster.activity;

import android.support.v4.app.Fragment;
import com.example.noah.weatherforecaster.fragment.OverviewFragment;

public class OverviewActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new OverviewFragment();
    }

}
