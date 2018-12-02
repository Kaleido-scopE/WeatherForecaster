package com.example.noah.weatherforecaster.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.example.noah.weatherforecaster.R;
import com.example.noah.weatherforecaster.fragment.OverviewFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new OverviewFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment, "OverViewFragment").commit();
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack();
        fm.beginTransaction().show(fm.getFragments().get(0)).commit();
    }
}
