package com.example.myapplication.Controller;

import android.app.Activity;
import android.os.Environment;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.CityWeather;
import com.example.myapplication.R;
import com.example.myapplication.Viewer.CardAdapter;
import com.example.myapplication.Viewer.WeatherCards;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ViewControllerImp implements ViewController{
    private static RecyclerView recyclerView;
    private static CardAdapter adapter;
    private static ArrayList<WeatherCards> weatherCards;
    private static final IOController  ioController = new IOControllerImp();

    private static final HashMap<String, Integer> cityIndexMap = new HashMap<>();

    @Override
    public void updateView(CityWeather currentCity) {
        int index = 0;
        boolean isCurrentLoc = currentCity.isCurrentLocation();
        String date = new java.util.Date((currentCity.getDt() )*1000).toString();
        String weather = currentCity.getWeather().get(0).getMain();
        String temp = currentCity.getMain().getTemp()+"";
        String humidity = currentCity.getMain().getHumidity()+"";
        WeatherCards weatherCards1 = new WeatherCards(currentCity.getName(), weather, date, temp, humidity);
        if(!isCurrentLoc){
            if(cityIndexMap.containsKey(currentCity.getName())){
                index = cityIndexMap.get(currentCity.getName());
            }
        }
        weatherCards.set(index, weatherCards1);
        adapter.notifyItemChanged(index);
    }


    @Override
    public void InitializeCardView(Activity activity){
        recyclerView = activity.findViewById(R.id.recyclerViewCard);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        weatherCards = new ArrayList<>();
        adapter = new CardAdapter(activity, weatherCards);
        recyclerView.setAdapter(adapter);
        createTestData();
        String dir = activity.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + "/";
        try{
            ArrayList<CityWeather> cityWeathers = ioController.loadFromDisk(dir);
            if(cityWeathers == null)
                return;
            for (CityWeather i : cityWeathers){
                updateView(i);
            }
        }catch (IOException e){
            TextView status = activity.findViewById(R.id.status);
            status.setText("No local record found");
        }
    }

    @Override
    public void createTestData() {
        //currentCity.getName(), currentCity.getWeathers().get(0).getMain(), currentCity.getDt()+""
        WeatherCards curr = new WeatherCards("null", "null", "null", "null", "null");
        weatherCards.add(curr);

        WeatherCards sg = new WeatherCards("Singapore", "null", "null", "null", "null");
        weatherCards.add(sg);
        cityIndexMap.put("Singapore", 1);

        WeatherCards ny = new WeatherCards("New York", "null", "null", "null", "null");
        weatherCards.add(ny);
        cityIndexMap.put("New York", 2);

        WeatherCards Mb = new WeatherCards("Mumbai", "null", "null", "null", "null");
        weatherCards.add(Mb);
        cityIndexMap.put("Mumbai", 3);

        WeatherCards dh = new WeatherCards("Delhi", "null", "null", "null", "null");
        weatherCards.add(dh);
        cityIndexMap.put("Delhi", 4);

        WeatherCards sy = new WeatherCards("Sydney", "null", "null", "null", "null");
        weatherCards.add(sy);
        cityIndexMap.put("Sydney", 5);

        WeatherCards mbn = new WeatherCards("Melbourne", "null", "null", "null", "null");
        weatherCards.add(mbn);
        cityIndexMap.put("Melbourne", 6);

    }
}
