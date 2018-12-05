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
        if (getIntent() != null)
            detail = (WeatherEntity) getIntent().getSerializableExtra("detail");
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("detail", detail);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

}
