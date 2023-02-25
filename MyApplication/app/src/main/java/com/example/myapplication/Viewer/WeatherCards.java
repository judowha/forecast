package com.example.myapplication.Viewer;

public class WeatherCards {
    private String city;
    private String weather;
    private String time;
    private String temp;
    private String humidity;

    public WeatherCards(String city, String weather, String time, String temp, String humidity) {
        this.city = city;
        this.weather = weather;
        this.time = time;
        this.temp = temp;
        this.humidity = humidity;
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

    public String getHumidity() {
        return humidity;
    }

    public String getTemp() {
        return temp;
    }
}
