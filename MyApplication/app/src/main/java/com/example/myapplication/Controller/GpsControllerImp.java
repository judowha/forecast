package com.example.myapplication.Controller;

import static androidx.core.app.ActivityCompat.requestPermissions;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

public class GpsControllerImp implements GpsController {
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int PERMISSIONS_LOCATION = 66;
    LocationRequest locationRequest;
    LocationCallback locationCallback;
    private static final RetrieveDataFromAPIController retrieveDataFromAPIController = new RetrieveFromAPIControllerImp();

    public void updateGPS(Activity activity){
        locationRequest = new LocationRequest.Builder(Priority.PRIORITY_BALANCED_POWER_ACCURACY, 1000000)
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(LocationRequest.Builder.IMPLICIT_MIN_UPDATE_INTERVAL)
                .setMaxUpdateDelayMillis(100000)
                .build();
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location location = locationResult.getLastLocation();
                if(location == null){
                    return;
                }
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                Log.d("test2", latitude + " " +  longitude);
            }
        };
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);
        //check permission
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(activity, location -> {
                if (location == null) {
                    return;
                }
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                Log.d("test", latitude + " " + longitude);
                retrieveDataFromAPIController.retrieveWeather(latitude, longitude, activity, null);
            });
        }
        else{
            requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_LOCATION);
        }
        //fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,null);
    }
}
