package com.example.myapplication.Controller;

import android.util.Log;

import com.example.myapplication.Model.CityWeather;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.CheckedInputStream;

public class IOControllerImp implements IOController{

    @Override
    public void writeToDisk(String path, CityWeather cityWeather) throws IOException {
        Gson gson = new Gson();
        path = path + cityWeather.getName() + ".txt";
        Log.e("path",path);
        FileWriter fileWriter = new FileWriter(path);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(gson.toJson(cityWeather));
        bufferedWriter.close();
    }

    @Override
    public ArrayList<CityWeather> loadFromDisk(String path) throws IOException {
        ArrayList<CityWeather> cityWeathers = new ArrayList<>();
        File file = new File(path);
        File[] files = file.listFiles();
        if(files.length == 0)
            return null;
        else{
            for(File i : files){
                path = i.getAbsolutePath();
                FileReader fileReader = new FileReader(path);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                StringBuilder stringBuilder = new StringBuilder();
                String line = bufferedReader.readLine();
                while (line != null){
                    stringBuilder.append(line).append("\n");
                    line = bufferedReader.readLine();
                }
                bufferedReader.close();
                String responce = stringBuilder.toString();
                Log.e("Local Record", responce);
                Gson gson = new Gson();
                CityWeather cityWeather = gson.fromJson(responce, CityWeather.class);
                cityWeathers.add(cityWeather);
            }
        }

        return cityWeathers;

    }
}
