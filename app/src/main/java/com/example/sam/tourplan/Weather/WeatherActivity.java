package com.example.sam.tourplan.Weather;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.sam.tourplan.MainActivity;
import com.example.sam.tourplan.R;
import com.example.sam.tourplan.Weather.Fragment.ViewPageAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;

public class WeatherActivity extends AppCompatActivity implements GetMenu,
        GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,
        LocationListener{


    public static String dataType = "metric";
    private GoogleApiClient apiClient;
    private LocationRequest locationRequest;
    private Geocoder geocoder;
    private List<Address> addressList;
    private static String SearchByCity="";
    private static String currentCity="Dhaka";
    private static String currentCountry="Bangladesh";
    /*
    * Layout Element Inti
     */
    private TabLayout tab;
    private ViewPager mypager;
    private ViewPageAdapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        tab = (TabLayout) findViewById(R.id.tab);
        mypager = (ViewPager) findViewById(R.id.viewPager);
        adapter = new ViewPageAdapter(getSupportFragmentManager());
        mypager.setAdapter(adapter);
        tab.setupWithViewPager(mypager);




        apiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
        geocoder = new Geocoder(this);
    }

    @Override
    protected void onStart() {
        apiClient.connect();
        super.onStart();
    }

    @Override
    protected void onPause() {
        apiClient.disconnect();
        super.onPause();
    }

    @Override
    public String getCity() {
        return currentCity;
    }

    @Override
    public String getType() {
        return dataType;
    }

    @Override
    public String getCountry() {
        return currentCountry;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = LocationRequest.create()
                .setInterval(1000)
                .setFastestInterval(1000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            Toast.makeText(getApplicationContext(),"Please enable your Location Service",Toast.LENGTH_LONG).show();

            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(apiClient,locationRequest,this);



    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        try {
            addressList = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            currentCity = addressList.get(0).getLocality();
            currentCountry = addressList.get(0).getCountryName();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.weather_menu, menu);
        MenuItem item = menu.findItem(R.id.we_search_button);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchByCity = query;
                currentCity=SearchByCity;
                mypager.setAdapter(adapter);
                tab.setupWithViewPager(mypager);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void getDataType(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.celsius:
                dataType = "metric";
                mypager.setAdapter(adapter);
                tab.setupWithViewPager(mypager);
                break;
            case R.id.fahrenheit:
                dataType = "imperial";
                mypager.setAdapter(adapter);
                tab.setupWithViewPager(mypager);
                break;
            case R.id.reset:

                 mypager.setAdapter(adapter);
                tab.setupWithViewPager(mypager);
                break;
            case R.id.home:
                startActivity(new Intent(WeatherActivity.this, MainActivity.class));
                break;
        }
    }




}
