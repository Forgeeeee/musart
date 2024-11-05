package com.example.musart1;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Permiso {

    public static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 1;


    public static boolean checkReadExternalStoragePermission(Context context) {
        return ContextCompat.checkSelfPermission(context, "android.permission.READ_EXTERNAL_STORAGE")
                == PackageManager.PERMISSION_GRANTED;
    }


    public static void requestReadExternalStoragePermission(Activity activity) {
        if (!checkReadExternalStoragePermission(activity)) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{"android.permission.READ_EXTERNAL_STORAGE"},
                    READ_EXTERNAL_STORAGE_REQUEST_CODE);
        }
    }

}
