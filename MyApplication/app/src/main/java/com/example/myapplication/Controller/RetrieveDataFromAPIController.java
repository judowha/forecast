package com.example.myapplication.Controller;

import android.app.Activity;

import com.example.myapplication.Model.CityWeather;

public interface RetrieveDataFromAPIController {
    void retrieveWeather(double myLat, double myLong, Activity activity);
}
