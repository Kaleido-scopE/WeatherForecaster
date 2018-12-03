package com.example.noah.weatherforecaster.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.noah.weatherforecaster.R;


public class DetailFragment extends Fragment {
    private int day; //新建对象时需要传入，表示需要显示哪一天的详细信息（0表示今天，1表示明天，以此类推）

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail, container, false);
        if (getArguments() != null && getArguments().containsKey("day"))
            day = getArguments().getInt("day");
        Log.d("DetailFragment", day + "");
        return v;
    }
}
