package com.kinetic.sh.Models;

public class AccentColor {
    private int color;
    private float[] intensity;
    private String[][] multiPathsNames;
    private String[] pathnames;

    public float[] getIntensity() {
        return this.intensity;
    }

    public String[][] getMultiPathsNames() {
        return this.multiPathsNames;
    }

    public AccentColor(int i, String... strArr) {
        this.color = i;
        this.pathnames = strArr;
    }

    public void setIntensity(float[] fArr) {
        this.intensity = fArr;
    }

    public void setPathnames(String[] strArr) {
        this.pathnames = strArr;
    }

    public void setMultiPathsNames(String[][] strArr) {
        this.multiPathsNames = strArr;
    }

    public String[] getPathnames() {
        return this.pathnames;
    }

    public int getColor() {
        return this.color;
    }

    public String[] getPathname() {
        return this.pathnames;
    }

    public void setColor(int i) {
        this.color = i;
    }
}
