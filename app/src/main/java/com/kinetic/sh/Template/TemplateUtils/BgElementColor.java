package com.kinetic.sh.Template.TemplateUtils;

import android.util.Log;

public class BgElementColor {
    private static final String TAG = "BgElementColor";
    private int color;
    private float[] intensity;
    private String[][] multiPath;
    private String[] path;

    public String[][] getMultiPath() {
        return this.multiPath;
    }

    public void setMultiPath(String[][] strArr) {
        this.multiPath = strArr;
    }

    public float[] getIntensity() {
        return this.intensity;
    }

    public void setIntensity(float[] fArr) {
        this.intensity = fArr;
    }

    public BgElementColor(int i, String[] strArr) {
        this.color = i;
        this.path = strArr;
    }

    public BgElementColor(int i, String[][] strArr) {
        this.color = i;
        this.multiPath = strArr;
    }

    public String[] getPath() {
        return this.path;
    }

    public void setPath(String[] strArr) {
        Log.i(TAG, "setPath: " + Log.getStackTraceString(new Exception()));
        this.path = strArr;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int i) {
        this.color = i;
    }
}
