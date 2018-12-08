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

        if (getIntent() != null) {
            detail = (WeatherEntity) getIntent().getSerializableExtra("detail");
            tempUnit = getIntent().getStringExtra("unit");
        }

        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("detail", detail);
        bundle.putString("unit", tempUnit);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

}
