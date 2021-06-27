package com.kinetic.sh.Helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class MyPreferences {
    private static final String MY_PREFERENCES = "my_preferences";

    public static boolean isFirst(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREFERENCES, 0);
        boolean z = sharedPreferences.getBoolean("is_first", true);
        if (z) {
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putBoolean("is_first", false);
            edit.apply();
        }
        return z;
    }
}
