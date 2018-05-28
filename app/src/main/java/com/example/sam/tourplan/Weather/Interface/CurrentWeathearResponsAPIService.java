package com.example.sam.tourplan.Weather.Interface;

import com.example.sam.tourplan.Weather.ModelClassCurrentWeather.CurrentWeatherMain;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by Mobile App Develop on 4/17/2017.
 */

public interface CurrentWeathearResponsAPIService {
    /*@GET("/data/2.5/weather?q=Dhaka&units=metric&appid=8e3a5f8c16948a8c2c36fe44e9bb23ff")
    Call<CurrentWeatherMain> getRespons();*/
    @POST()
    Call<CurrentWeatherMain> getRespons(@Url String url);
}
