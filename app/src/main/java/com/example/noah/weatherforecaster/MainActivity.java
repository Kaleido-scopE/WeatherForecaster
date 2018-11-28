package com.example.noah.weatherforecaster;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static class FetchItemsTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                WeatherEntity entity = WeatherInfoFetcher.getForecast("changsha")[0];
                Log.d("MainActivity", entity.getLocation());
                Log.d("MainActivity", String.valueOf(entity.getWeatherCode()));
                Log.d("MainActivity", entity.getWeatherName());
                Log.d("MainActivity", "Max: " + entity.getMaxDegree());
                Log.d("MainActivity", "Min: " + entity.getMinDegree());
                Log.d("MainActivity", entity.getDate().toString());
            } catch (Exception e) {
                Log.e("MainActivity", "Exception");
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new FetchItemsTask().execute();
    }
}
