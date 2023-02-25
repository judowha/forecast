package com.example.myapplication.Viewer;

public class WeatherCards {
    private String city;
    private String weather;
    private String time;

    public WeatherCards(String city, String weather, String time) {
        this.city = city;
        this.weather = weather;
        this.time = time;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWeather() {
        return weather;
    }

    public String getTime() {
        return time;
    }
}
