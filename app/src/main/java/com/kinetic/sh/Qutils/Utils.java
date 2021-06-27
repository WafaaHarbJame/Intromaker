package com.kinetic.sh.Qutils;

import android.content.Context;
import android.os.Environment;

import androidx.core.content.ContextCompat;

import java.util.Locale;

public final class Utils {
    public static final String PREFS_CACHE_ASSETS = "cacheAssets";
    public static final String PREFS_FAVOURITE_LIST = "favouriteList";

    private Utils() {
    }

    public static String getRootDirPath(Context context) {
        if ("mounted".equals(Environment.getExternalStorageState())) {
            return ContextCompat.getExternalFilesDirs(context.getApplicationContext(), null)[0].getAbsolutePath();
        }
        return context.getApplicationContext().getFilesDir().getAbsolutePath();
    }

    public static String getProgressDisplayLine(long j, long j2) {
        return getBytesToMBString(j) + "/" + getBytesToMBString(j2);
    }

    private static String getBytesToMBString(long j) {
        return String.format(Locale.ENGLISH, "%.2fMb", ((double) j) / 1048576.0d);
    }
}
