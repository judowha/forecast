package com.example.myapplication.Controller;

import static androidx.core.app.ActivityCompat.requestPermissions;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.myapplication.Model.CityWeather;
import com.example.myapplication.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrieveFromAPIControllerImp implements RetrieveDataFromAPIController{

    private static final int PERMISSIONS_NETWORK = 67;
    private static final ViewController viewController = new ViewControllerImp();
    private static final IOController ioController = new IOControllerImp();
    @Override
    public void retrieveWeather(double myLat, double myLong, Activity activity, String cityName) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED) {
            TextView status = activity.findViewById(R.id.status);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.openweathermap.org")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            WeatherAPIController weatherAPIController = retrofit.create(WeatherAPIController.class);
            Call<JsonObject> call;
            if(cityName == null){
                call = weatherAPIController.fetchWeatherResultByLatAndLon(myLat, myLong, "dec5b6caa939e884eeba958c4c8d4926");
            }
            else{
                call = weatherAPIController.fetchWeatherResultByCity(cityName, "dec5b6caa939e884eeba958c4c8d4926");
            }
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    status.setText("Internet Connected");
                    JsonObject result = response.body();
                    Log.e("Retrieved",result.toString());
                    Gson gson = new Gson();
                    CityWeather currentLocationWeather = gson.fromJson(result, CityWeather.class);
                    if(cityName == null){
                        currentLocationWeather.setCurrentLocation(true);
                    }
                    else currentLocationWeather.setCurrentLocation(false);

                    //write to disk
                    String dir = activity.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + "/";
                    try {
                        ioController.writeToDisk(dir, currentLocationWeather);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    //update view
                    viewController.updateView(currentLocationWeather);
                }
                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    status.setText("Http request error, Latest recorded data is displayed");
                }
            });
        }
        else{
            requestPermissions(activity, new String[] {Manifest.permission.ACCESS_NETWORK_STATE}, PERMISSIONS_NETWORK);
        }
    }

}
