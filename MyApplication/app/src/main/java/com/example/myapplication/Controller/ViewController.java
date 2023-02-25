package com.example.myapplication.Controller;

import android.app.Activity;

import com.example.myapplication.Model.CityWeather;

import java.io.IOException;

public interface ViewController {
    public void updateView(CityWeather cityWeather);
    public void InitializeCardView(Activity activity) throws IOException;
    public void createTestData();
}
