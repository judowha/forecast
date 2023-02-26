package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.myapplication.Controller.GpsControllerImp;
import com.example.myapplication.Controller.RetrieveDataFromAPIController;
import com.example.myapplication.Controller.RetrieveFromAPIControllerImp;
import com.example.myapplication.Controller.ViewController;
import com.example.myapplication.Controller.ViewControllerImp;
import com.example.myapplication.Controller.WeatherAPIController;
import com.example.myapplication.Model.CityWeather;
import com.example.myapplication.Viewer.CardAdapter;
import com.example.myapplication.Viewer.WeatherCards;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppActivity extends AppCompatActivity {

    private static final int PERMISSIONS_LOCATION = 66;
    private static final int PERMISSIONS_NETWORK = 67;
    private final GpsControllerImp gpsControllerImp = new GpsControllerImp();
    private final RetrieveDataFromAPIController retrieveDataFromAPIController = new RetrieveFromAPIControllerImp();
    private final ViewController viewController = new ViewControllerImp();
    private final static String ACTION_NETWORK = "android.net.conn.CONNECTIVITY_CHANGE";
    private static NetworkReceiver netWorkReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);
        try {
            viewController.InitializeCardView(AppActivity.this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        netWorkReceiver = new NetworkReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netWorkReceiver, filter);

        Timer timer = new Timer();
        timer.schedule(new MyTimerTask(), 0, 600000);
    }

    private class NetworkReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)){
                Log.e("Tag","network changes");
                gpsControllerImp.updateGPS(AppActivity.this);
                requestWeatherForCities();
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (netWorkReceiver != null) {
            unregisterReceiver(netWorkReceiver);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case PERMISSIONS_LOCATION:
                if(!(grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                    finish();
                }
                break;
            case PERMISSIONS_NETWORK:
                if(!(grantResults[1] == PackageManager.PERMISSION_GRANTED)){
                    finish();
                }
                break;
        }
    }

    public void requestWeatherForCities(){
        retrieveDataFromAPIController.retrieveWeather(0,0, AppActivity.this,"Singapore");
        retrieveDataFromAPIController.retrieveWeather(0,0, AppActivity.this,"New York");
        retrieveDataFromAPIController.retrieveWeather(0,0, AppActivity.this,"Mumbai");
        retrieveDataFromAPIController.retrieveWeather(0,0, AppActivity.this,"Delhi");
        retrieveDataFromAPIController.retrieveWeather(0,0, AppActivity.this,"Sydney");
        retrieveDataFromAPIController.retrieveWeather(0,0, AppActivity.this,"Melbourne");

    }


    public class MyTimerTask extends TimerTask{
        @Override
        public void run() {
            gpsControllerImp.updateGPS(AppActivity.this);
            requestWeatherForCities();
        }
    }

}