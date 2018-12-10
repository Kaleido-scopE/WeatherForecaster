package com.example.noah.weatherforecaster.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.noah.weatherforecaster.R;
import com.example.noah.weatherforecaster.entity.CityEntity;
import com.example.noah.weatherforecaster.utils.SettingUtils;
import com.example.noah.weatherforecaster.utils.WeatherInfoFetcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CityFragment extends Fragment {
    private RecyclerView cityList;

    //新建Fragment实例
    public static CityFragment newInstance() {
        return new CityFragment();
    }

    //-------------------------生命周期函数-------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_city, container, false);

        cityList = v.findViewById(R.id.city_list);
        new FetchCityItemsTask().execute();

        return v;
    }

    //-------------------------必要的内部类-------------------------

    //Item布局的管理类
    private class CityHolder extends RecyclerView.ViewHolder {
        private TextView cityName;
        private TextView latitude;
        private TextView longitude;

        public CityHolder(LayoutInflater inflater, ViewGroup viewGroup) {
            super(inflater.inflate(R.layout.city_item, viewGroup, false));

            cityName = itemView.findViewById(R.id.city_name);
            latitude = itemView.findViewById(R.id.lat);
            longitude = itemView.findViewById(R.id.lon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String location = cityName.getText().toString();
                    double lat = Double.valueOf(latitude.getText().toString().substring(5));
                    double lon = Double.valueOf(longitude.getText().toString().substring(5));

                    SettingUtils.setSetLocation(new CityEntity(location, lat, lon));
                    getActivity().finish();
                }
            });
        }

        //设定每一项的显示效果
        public void bind(CityEntity city) {
            String latitudeStr = "lat: " + String.format(Locale.US, "%.3f", city.getLatitude());
            String longitudeStr = "lon: " + String.format(Locale.US, "%.3f", city.getLongitude());
            cityName.setText(city.getLocation());
            latitude.setText(latitudeStr);
            longitude.setText(longitudeStr);
        }
    }

    //RecyclerView的监听器
    private class CityAdapter extends RecyclerView.Adapter<CityHolder> {
        private List<CityEntity> cityEntities;

        public CityAdapter(List<CityEntity> cities) {
            cityEntities = cities;
        }

        @NonNull
        @Override
        public CityHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CityHolder(layoutInflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull CityHolder cityHolder, int i) {
            CityEntity city = cityEntities.get(i);
            cityHolder.bind(city);
        }

        @Override
        public int getItemCount() {
            return cityEntities.size();
        }
    }

    //网络请求类
    private class FetchCityItemsTask extends AsyncTask<Void, Void, Void> {
        private List<CityEntity> cities = new ArrayList<>();
        private boolean isNetworkOn = WeatherInfoFetcher.isNetworkOn(getActivity());

        @Override
        protected Void doInBackground(Void... voids) {
            if (isNetworkOn) {
                try {
                    cities = WeatherInfoFetcher.getCityList(50, "cn");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void param) {
            super.onPostExecute(param);
            if (isNetworkOn) {
                cityList.setLayoutManager(new LinearLayoutManager(getActivity()));
                cityList.setAdapter(new CityAdapter(cities));
            }
            else
                Toast.makeText(getActivity(), "没有网络连接，请打开网络！", Toast.LENGTH_LONG).show();
        }
    }
}
