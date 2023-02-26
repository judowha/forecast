package com.example.myapplication.Controller;

import com.example.myapplication.Model.CityWeather;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public interface IOController {
    void writeToDisk(String path, CityWeather cityWeather) throws IOException;
    ArrayList<CityWeather> loadFromDisk(String path) throws IOException;
}
