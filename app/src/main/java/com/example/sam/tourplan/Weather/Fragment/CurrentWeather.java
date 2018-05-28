package com.example.sam.tourplan.Weather.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sam.tourplan.R;
import com.example.sam.tourplan.Weather.DataModel.CurrentWeaherAllReport;
import com.example.sam.tourplan.Weather.GetMenu;
import com.example.sam.tourplan.Weather.Interface.CurrentWeathearResponsAPIService;
import com.example.sam.tourplan.Weather.ModelClassCurrentWeather.CurrentWeatherMain;
import com.example.sam.tourplan.Weather.WeatherActivity;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentWeather extends Fragment {

    private GetMenu menuService;

    private TextView pressureTV,humidityTV;
    private TextView temperatureTV,temperatureMaxTV,temperatureMiniTV;
    private TextView sunSetOrRiseTV,sunSetOrRiseDownTV,sunSetOrRiseTime,sunSetOrRiseTimeDown;
    private TextView windTV,directionTV,weatherReportTV,dateTV,locationTV;
    private TextView tvTestCW;


    private ImageView weatherIconIV,sunSetOrRiseIconIV,humidityIconIV;
    private ImageView windIconIV,directionIconIV,pressureIV;

    private final String BASE_URL = "http://api.openweathermap.org";
    private CurrentWeathearResponsAPIService apiRespons;

    private String dataType;
    private String currentCountry;
    private String currentCity;
    private String data = " ";

    public CurrentWeather() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_weather, container, false);

        menuService = new WeatherActivity();
        dataType = menuService.getType();
        currentCity = menuService.getCity();
        currentCountry = menuService.getCountry();


        //tvTestCW = (TextView) view.findViewById(R.id.tvTestCW);

        locationTV = (TextView) view.findViewById(R.id.tvPlaceLocation);
        dateTV = (TextView) view.findViewById(R.id.tvTime);

        weatherReportTV = (TextView) view.findViewById(R.id.tvWeatherReport);

        //pressure
        pressureTV = (TextView) view.findViewById(R.id.tvPressure);
        pressureIV = (ImageView) view.findViewById(R.id.ivPressureIcon);

        //Temperature
        temperatureTV = (TextView) view.findViewById(R.id.tvTemperature);
        temperatureMaxTV = (TextView) view.findViewById(R.id.tvTemperatureMax);
        temperatureMiniTV = (TextView) view.findViewById(R.id.tvTemperatureMini);
        weatherIconIV = (ImageView) view.findViewById(R.id.ivWeatherIcon);

        //Sun set Sun Rise
        sunSetOrRiseTV = (TextView) view.findViewById(R.id.tvSunsetOrRise);
        sunSetOrRiseDownTV = (TextView) view.findViewById(R.id.tvSunsetOrRiseDown);
        sunSetOrRiseTime = (TextView) view.findViewById(R.id.tvSunSetRiseTime);
        sunSetOrRiseTimeDown = (TextView) view.findViewById(R.id.tvSunSetRiseTime);
        sunSetOrRiseIconIV = (ImageView) view.findViewById(R.id.ivSunSetRiseIcon);

        //Humidity
        humidityTV = (TextView) view.findViewById(R.id.tvHumidity);
        humidityIconIV = (ImageView) view.findViewById(R.id.ivHumidityIcon);

        //Wind
        windTV = (TextView) view.findViewById(R.id.tvWind);
        windIconIV = (ImageView) view.findViewById(R.id.ivWindIcon);

        //Direction
        directionTV = (TextView) view.findViewById(R.id.tvDirection);
        directionIconIV = (ImageView) view.findViewById(R.id.ivDirectionIcon);


        RequestForCurrentWeatherData();

        return view;
    }

   /* @Override
    public void onStart() {
        Toast.makeText(getContext(),menuService.getType(),Toast.LENGTH_LONG).show();
        super.onStart();
    }*/

    private void RequestForCurrentWeatherData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiRespons = retrofit.create(CurrentWeathearResponsAPIService.class);

        Call<CurrentWeatherMain> arrayListCall = apiRespons
                .getRespons("/data/2.5/weather?q="+currentCity+"&units="+dataType+"&appid=8e3a5f8c16948a8c2c36fe44e9bb23ff");

        arrayListCall.enqueue(new Callback<CurrentWeatherMain>() {
            @Override
            public void onResponse(Call<CurrentWeatherMain> call, Response<CurrentWeatherMain> response) {
                if(response.code()==200){
                    //tvTestCW.setEnabled(false);
                    CurrentWeatherMain currentWeatherMain = response.body(); //error here nothing get from server

                    setData(currentWeatherMain);
                    data = currentWeatherMain.getBase();
                    //Toast.makeText(getContext(),currentWeatherMain.getName(),Toast.LENGTH_LONG).show();
                }else {
                    //tvTestCW.setText("Something is error , from server side");
                    Toast.makeText(getContext(),"Here is error form server side",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CurrentWeatherMain> call, Throwable t) {
               // tvTestCW.setText(t.getMessage());
            }
        });
    }

    private void setData(CurrentWeatherMain currentWeatherMain) {

        CurrentWeaherAllReport report = new CurrentWeaherAllReport(currentWeatherMain);

        //set Temperature
        if(dataType.equals("metric")){
            temperatureTV.setText(report.getTemperature()+"° C");
            temperatureMiniTV.setText(report.getTemperature_Mini()+"° C");
            temperatureMaxTV.setText(report.getTemperature_Max()+"° C");
        }else {
            temperatureTV.setText(report.getTemperature()+" F");
            temperatureMiniTV.setText(report.getTemperature_Mini()+" F");
            temperatureMaxTV.setText(report.getTemperature_Max()+" F");
        }
        Picasso.with(getContext()).load("http://openweathermap.org/img/w/"+
                currentWeatherMain.getWeather().get(0).getIcon()+".png")
                .into(weatherIconIV);


        pressureTV.setText(report.getPressure()+" hPa");

        locationTV.setText(currentCity+","+currentCountry);

        humidityTV.setText(report.getHumidity()+" %");
        windTV.setText(report.getWind()+" mps");
        directionTV.setText(report.getDirection()+" degrees");

        sunSetOrRiseTime.setText(report.getSunRiseTime());
        sunSetOrRiseTV.setText("Sun Rise Time");
        sunSetOrRiseTimeDown.setText(report.getSunSetTime());
        sunSetOrRiseDownTV.setText("Sun Set Time");

        dateTV.setText(report.getDate());
        weatherReportTV.setText(report.getWeatherRepor());

        pressureIV.setImageResource(R.drawable.pressure);
        sunSetOrRiseIconIV.setImageResource(R.drawable.sunrise);
        windIconIV.setImageResource(R.drawable.wind);
        directionIconIV.setImageResource(R.drawable.direction);
        humidityIconIV.setImageResource(R.drawable.humidity);
    }

}
