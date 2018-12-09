package com.example.noah.weatherforecaster.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import com.example.noah.weatherforecaster.R;
import com.example.noah.weatherforecaster.fragment.OverviewFragment;

public class OverviewActivity extends AppCompatActivity {
    public static final int activityReqCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.overview_container);

        if (fragment == null) {
            fragment = OverviewFragment.newInstance();
            fm.beginTransaction().add(R.id.overview_container, fragment).commit();
        }
    }
}
