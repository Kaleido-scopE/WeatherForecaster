package com.example.noah.weatherforecaster.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.example.noah.weatherforecaster.entity.WeatherEntity;
import com.example.noah.weatherforecaster.fragment.DetailFragment;

public class DetailActivity extends SingleFragmentActivity {
    public static final int activityReqCode = 2;

    @Override
    protected Fragment createFragment() {
        WeatherEntity detail = null;
        String tempUnit = "摄氏";
        boolean notification = true;

        if (getIntent() != null) {
            detail = (WeatherEntity) getIntent().getSerializableExtra("detail");
            tempUnit = getIntent().getStringExtra("unit");
            notification = getIntent().getBooleanExtra("notification", true);
        }

        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("detail", detail);
        bundle.putString("unit", tempUnit);
        bundle.putBoolean("notification", notification);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

}
