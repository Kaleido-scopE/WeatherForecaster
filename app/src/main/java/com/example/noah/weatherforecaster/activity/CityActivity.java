package com.example.noah.weatherforecaster.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import com.example.noah.weatherforecaster.entity.CityEntity;
import com.example.noah.weatherforecaster.fragment.CityFragment;

public class CityActivity extends SingleFragmentActivity {
    public static final int activityReqCode = 4;

    @Override
    protected Fragment createFragment() {
        CityEntity cityEntity = null;

        if (getIntent() != null)
            cityEntity = (CityEntity) getIntent().getSerializableExtra("curLocation");

        CityFragment cityFragment = new CityFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("originLocation", cityEntity);
        cityFragment.setArguments(bundle);
        return cityFragment;
    }
}
