package com.example.sam.tourplan.Weather.DataModel;


import com.example.sam.tourplan.Weather.ModelClassCurrentWeather.CurrentWeatherMain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by r3l0ad3d on 4/20/17.
 */

public class CurrentWeaherAllReport {
    protected String CountryName;
    protected String CityName;
    protected String Date;
    protected String WeatherRepor;
    protected String WeatherIcon;
    protected String Cloud;
    protected String Temperature;
    protected String Temperature_Max;
    protected String Temperature_Mini;
    protected String Humidity;
    protected String Pressure;
    protected String Wind;
    protected String Direction;
    protected String SunSetTime;
    protected String SunRiseTime;

    public CurrentWeaherAllReport(CurrentWeatherMain currentWeather) {
        this.Convert(currentWeather);
    }

    protected void Convert(CurrentWeatherMain currentWeather) {
        setCloud(String.valueOf(currentWeather.getClouds()));
        setTemperature(String.valueOf(currentWeather.getMain().getTemp()));
        setTemperature_Max(String.valueOf(currentWeather.getMain().getTempMax()));
        setTemperature_Mini(String.valueOf(currentWeather.getMain().getTempMin()));
        setHumidity(String.valueOf(currentWeather.getMain().getHumidity()));
        setPressure(String.valueOf(currentWeather.getMain().getPressure()));
        setWind(String.valueOf(currentWeather.getWind().getSpeed()));
        setDirection(String.valueOf(currentWeather.getWind().getDeg()));
        //convert Date
        java.util.Date date = new Date((currentWeather.getSys().getSunrise())*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+6"));
        String formattedDate = sdf.format(date);
        setSunRiseTime(formattedDate);
        //COnvert Date
        /*java.util.Date date1 = new Date((currentWeather.getSys().getSunset())*1000L);
        SimpleDateFormat sdf1 = new SimpleDateFormat("h:mm a");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+6"));
        String formattedDate1 = sdf.format(date1);*/
        setSunSetTime(formattedDate);

        setWeatherRepor(currentWeather.getWeather().get(0).getDescription());
        setWeatherIcon(currentWeather.getWeather().get(0).getIcon());

        //convert Date
        java.util.Date date2 = new Date((currentWeather.getDt())*1000L);
        SimpleDateFormat sdf2 = new SimpleDateFormat("h:mm a");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+6"));
        String formattedDate2 = sdf.format(date2);
        setDate(formattedDate2);

        setCityName(currentWeather.getName());

    }

    public String getCityName() {
        return CityName;
    }

    protected void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getDate() {
        return Date;
    }

    protected void setDate(String date) {
        Date = date;
    }

    public String getWeatherIcon() {
        return WeatherIcon;
    }

    protected void setWeatherIcon(String weatherIcon) {
        WeatherIcon = weatherIcon;
    }

    public String getWeatherRepor() {
        return WeatherRepor;
    }

    protected void setWeatherRepor(String weatherRepor) {
        WeatherRepor = weatherRepor;
    }

    public String getCloud() {
        return Cloud;
    }

    protected void setCloud(String cloud) {
        Cloud = cloud;
    }

    public String getTemperature() {
        return Temperature;
    }

    protected void setTemperature(String temperature) {
        Temperature = temperature;
    }

    public String getTemperature_Max() {
        return Temperature_Max;
    }

    protected void setTemperature_Max(String temperature_Max) {
        Temperature_Max = temperature_Max;
    }

    public String getTemperature_Mini() {
        return Temperature_Mini;
    }

    protected void setTemperature_Mini(String temperature_Mini) {
        Temperature_Mini = temperature_Mini;
    }

    public String getHumidity() {
        return Humidity;
    }

    protected void setHumidity(String humidity) {
        Humidity = humidity;
    }

    public String getPressure() {
        return Pressure;
    }

    protected void setPressure(String pressure) {
        Pressure = pressure;
    }

    public String getWind() {
        return Wind;
    }

    protected void setWind(String wind) {
        Wind = wind;
    }

    public String getDirection() {
        return Direction;
    }

    protected void setDirection(String direction) {
        Direction = direction;
    }

    public String getSunSetTime() {
        return SunSetTime;
    }

    protected void setSunSetTime(String sunSetTime) {
        SunSetTime = sunSetTime;
    }

    public String getSunRiseTime() {
        return SunRiseTime;
    }

    protected void setSunRiseTime(String sunRiseTime) {
        SunRiseTime = sunRiseTime;
    }
}
