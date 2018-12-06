package com.example.noah.weatherforecaster.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.noah.weatherforecaster.fragment.CityFragment;

public class CityActivity extends SingleFragmentActivity {
    public static final int activityReqCode = 4;

    @Override
    protected Fragment createFragment() {
        return new CityFragment();
    }
}
