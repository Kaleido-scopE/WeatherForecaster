package com.example.noah.weatherforecaster.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
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
        if (getArguments() != null) {
            day = getArguments().getInt("day");
            Log.d("detail", day + "");
        }

        v.setFocusable(true);
        v.requestFocus();
        v.setFocusableInTouchMode(true);
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

                    return true;
                }
                return false;
            }
        });
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("detail", "destroy");
    }

    public static DetailFragment newInstance(int day) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("day", day);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }
}
