package com.example.myapplication.Controller;

import com.example.myapplication.Model.CityWeather;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IOController {
    void writeToDisk(String path, CityWeather cityWeather) throws IOException;
    CityWeather loadFromDisk(String path) throws IOException;
}
