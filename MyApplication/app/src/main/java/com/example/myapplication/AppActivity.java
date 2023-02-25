package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.myapplication.Controller.GpsControllerImp;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppActivity extends AppCompatActivity {

    private static final int PERMISSIONS_LOCATION = 66;
    private static final int PERMISSIONS_NETWORK = 67;
    private final GpsControllerImp gpsControllerImp = new GpsControllerImp();
    private final ViewController viewController = new ViewControllerImp();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);
        try {
            viewController.InitializeCardView(AppActivity.this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        gpsControllerImp.updateGPS(AppActivity.this);

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
}