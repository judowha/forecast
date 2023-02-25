package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.myapplication.Controller.WeatherAPIController;
import com.example.myapplication.Model.CityWeather;
import com.example.myapplication.Viewer.CardAdapter;
import com.example.myapplication.Viewer.WeatherCards;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppActivity extends AppCompatActivity {
    private static final int PERMISSIONS_LOCATION = 66;
    private static final int PERMISSIONS_NETWORK = 67;
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE
    };
    private RecyclerView recyclerView;
    private CardAdapter adapter;
    private ArrayList<WeatherCards> weatherCards;

    private CityWeather currentCity;

    // Google API for GPS
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);

        locationRequest = new LocationRequest.Builder(Priority.PRIORITY_BALANCED_POWER_ACCURACY, 10000)
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(LocationRequest.Builder.IMPLICIT_MIN_UPDATE_INTERVAL)
                .setMaxUpdateDelayMillis(100000)
                .build();
        InitializeCardView();
        updateGPS();

    }

    private void retrieveWeather(double myLat, double myLong) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED) {
            TextView status = findViewById(R.id.status);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.openweathermap.org")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            WeatherAPIController weatherAPIController = retrofit.create(WeatherAPIController.class);
            Call<JsonObject> call = weatherAPIController.fetchWeatherResultByLatAndLon(myLat, myLong, "dec5b6caa939e884eeba958c4c8d4926");
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    status.setText("Internet Connected");
                    JsonObject result = response.body();
                    Log.d("permission",result.toString());
                    Gson gson = new Gson();
                    currentCity = gson.fromJson(result, CityWeather.class);
                    Log.d("city weather",currentCity.toString());
                    updateView();
                }
                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    status.setText("Http request error, Latest recorded data is displayed");
                }
            });
        }
        else{
            requestPermissions(new String[] {Manifest.permission.ACCESS_NETWORK_STATE}, PERMISSIONS_NETWORK);
        }
    }

    private void updateView() {
        WeatherCards weatherCards1 = new WeatherCards(currentCity.getName(), currentCity.getWeather().get(0).getMain(), currentCity.getDt()+"");
        weatherCards.set(0, weatherCards1);
        adapter.notifyItemChanged(0);
    }


    private void InitializeCardView(){
        recyclerView = findViewById(R.id.recyclerViewCard);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        weatherCards = new ArrayList<>();
        adapter = new CardAdapter(this, weatherCards);
        recyclerView.setAdapter(adapter);
        CreateTestData();
    }

    private void CreateTestData() {
        //currentCity.getName(), currentCity.getWeathers().get(0).getMain(), currentCity.getDt()+""
        WeatherCards weatherCard = new WeatherCards("British", "rain", "2023/2/24");
        weatherCards.add(weatherCard);
        weatherCard = new WeatherCards("British", "rain", "2023/2/24");
        weatherCards.add(weatherCard);
        weatherCard = new WeatherCards("Beijing", "rain", "2023/2/24");
        weatherCards.add(weatherCard);
    }


    private void updateGPS(){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(AppActivity.this);
        //check permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location == null){
                        return;
                    }
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    Log.d("test", latitude + " " +  longitude);
                    retrieveWeather(latitude,longitude);
                }
            });
        }
        else{
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case PERMISSIONS_LOCATION:
                if(!(grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                    finish();
                }
                break;
            case PERMISSIONS_NETWORK:
                if(!(grantResults[1] == PackageManager.PERMISSION_GRANTED)){
                    finish();
                }
                break;
        }
    }


}