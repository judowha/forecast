package com.example.myapplication.Controller;

import android.app.Activity;

public interface RetrieveDataFromAPIController {
    void retrieveWeather(double myLat, double myLong, Activity activity, String cityName);


}
