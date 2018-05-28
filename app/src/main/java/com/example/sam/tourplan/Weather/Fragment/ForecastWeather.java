package com.example.sam.tourplan.Weather.Fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sam.tourplan.R;
import com.example.sam.tourplan.Weather.GetMenu;
import com.example.sam.tourplan.Weather.WeatherActivity;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForecastWeather extends Fragment {
    private String url = "http://api.openweathermap.org/data/2.5/forecast/daily?q=dhaka&mode=jeson&units=metric&appid=8e3a5f8c16948a8c2c36fe44e9bb23ff";
    private GetMenu menuService;
    private String currentCity;
    private String country;
    private String type;
    private String api;


    public ForecastWeather() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        menuService = new WeatherActivity();
        country = menuService.getCountry();
        currentCity = menuService.getCity();
        type = menuService.getType();
        api = "http://api.openweathermap.org/data/2.5/forecast/daily?q="+currentCity+"a&mode=jeson&units="+type+"&appid=8e3a5f8c16948a8c2c36fe44e9bb23ff"
        View view = inflater.inflate(R.layout.fragment_forecast_weather, container, false);
        return view;
    }
    class getForecast extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... data) {
            StringBuilder getdata = new StringBuilder();
            URL url;
            HttpURLConnection connection;
            try {
                url=new URL(data[0]);
                connection= (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while((line=reader.readLine())!=null){
                    getdata.append(line);
                }



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return getdata.toString().trim();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject weather = new JSONObject(s);
                JSONObject city = weather.getJSONObject("city");
                JSONArray list =weather.getJSONArray("list");
                JSONObject temp = list[0].getJSONObject("temp");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
