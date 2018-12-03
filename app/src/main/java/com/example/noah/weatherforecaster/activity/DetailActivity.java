package com.example.noah.weatherforecaster.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.example.noah.weatherforecaster.fragment.DetailFragment;

public class DetailActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        int day = 0;
        if (getIntent() != null)
            day = getIntent().getIntExtra("day", 0);
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("day", day);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

}
