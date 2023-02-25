package com.example.myapplication.Controller;

import android.app.Activity;
import android.os.Environment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.CityWeather;
import com.example.myapplication.R;
import com.example.myapplication.Viewer.CardAdapter;
import com.example.myapplication.Viewer.WeatherCards;

import java.io.IOException;
import java.util.ArrayList;

public class ViewControllerImp implements ViewController{
    private static RecyclerView recyclerView;
    private static CardAdapter adapter;
    private static ArrayList<WeatherCards> weatherCards;
    private static IOController  ioController = new IOControllerImp();

    @Override
    public void updateView(CityWeather currentCity) {
        String date = new java.util.Date((currentCity.getDt() )*1000).toString();
        String weather = currentCity.getWeather().get(0).getMain();
        String temp = currentCity.getMain().getTemp()+"";
        String humidity = currentCity.getMain().getHumidity()+"";
        WeatherCards weatherCards1 = new WeatherCards(currentCity.getName(), weather, date, temp, humidity);
        weatherCards.set(0, weatherCards1);
        adapter.notifyItemChanged(0);
    }


    @Override
    public void InitializeCardView(Activity activity) throws IOException {
        recyclerView = activity.findViewById(R.id.recyclerViewCard);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        weatherCards = new ArrayList<>();
        adapter = new CardAdapter(activity, weatherCards);
        recyclerView.setAdapter(adapter);
        String dir = activity.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + "/";
        CityWeather cityWeather = ioController.loadFromDisk(dir);
        createTestData();
        if(cityWeather != null){
            updateView(cityWeather);
        }
    }

    @Override
    public void createTestData() {
        //currentCity.getName(), currentCity.getWeathers().get(0).getMain(), currentCity.getDt()+""
        WeatherCards weatherCard = new WeatherCards("null", "null", "null", "null", "null");
        weatherCards.add(weatherCard);
        weatherCard = new WeatherCards("null", "null", "null", "null", "null");
        weatherCards.add(weatherCard);
        weatherCard = new WeatherCards("null", "null", "null", "null", "null");
        weatherCards.add(weatherCard);
    }
}
