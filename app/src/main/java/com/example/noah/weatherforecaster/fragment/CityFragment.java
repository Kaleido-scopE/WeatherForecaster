package com.example.noah.weatherforecaster.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.noah.weatherforecaster.R;
import com.example.noah.weatherforecaster.entity.CityEntity;
import com.example.noah.weatherforecaster.utils.WeatherInfoFetcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CityFragment extends Fragment {
    private RecyclerView cityList;

    private Intent resultIntent = new Intent();

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
                    resultIntent.putExtra("location", location);
                    getActivity().setResult(0, resultIntent);
                    getActivity().finish();
                }
            });
        }

        public void bind(CityEntity city) {
            String latitudeStr = "lat: " + String.format(Locale.US, "%.2f", city.getLatitude());
            String longitudeStr = "lon: " + String.format(Locale.US, "%.2f", city.getLongitude());
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

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                cities = WeatherInfoFetcher.getCityList(50, "overseas");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void param) {
            super.onPostExecute(param);
            cityList.setLayoutManager(new LinearLayoutManager(getActivity()));
            cityList.setAdapter(new CityAdapter(cities));
        }
    }

    //-------------------------生命周期函数-------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_city, container, false);

        resultIntent.putExtra("location", "北京");
        getActivity().setResult(0, resultIntent);

        new FetchCityItemsTask().execute();

        cityList = v.findViewById(R.id.city_list);

        return v;
    }

}
