package com.example.myapplication.Controller;

import com.example.myapplication.Model.CityWeather;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPIController {

    @GET("data/2.5/weather")
    Call<JsonObject> fetchWeatherResultByLatAndLon(@Query("lat") double lat,
                                                    @Query("lon") double lon,
                                                    @Query("appid") String key);

    @GET("data/2.5/weather")
    Call<JsonObject> fetchWeatherResultByCity(@Query("q") String city,
                                                   @Query("appid") String key);


}
