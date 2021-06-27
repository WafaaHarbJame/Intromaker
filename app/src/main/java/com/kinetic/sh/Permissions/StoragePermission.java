package com.kinetic.sh.Permissions;

import android.app.Activity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class StoragePermission {
    private final String TAG = "StoragePermission";
    private final Activity activity;

    public StoragePermission(Activity activity2) {
        this.activity = activity2;
    }

    public Boolean checkPermissions() {
        String[] strArr = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
        if (ContextCompat.checkSelfPermission(this.activity, strArr[0]) == 0 && ContextCompat.checkSelfPermission(this.activity, strArr[1]) == 0) {
            return true;
        }
        ActivityCompat.requestPermissions(this.activity, strArr, 1);
        return false;
    }
}
