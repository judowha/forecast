package com.example.myapplication.Util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionUtil {
    public static boolean checkPermission(Activity activity, String[] permissions, int requestCode){
        int check = PackageManager.PERMISSION_GRANTED;
        for(String permission : permissions){
            check = ContextCompat.checkSelfPermission(activity, permission);
            if (check != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(activity, permissions, requestCode);
                return false;
            }
        }
        return true;
    }

    public static boolean checkGrant(int[] grantResults){
        if(grantResults!=null){
            for(int grant : grantResults){
                if(grant != PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

}
