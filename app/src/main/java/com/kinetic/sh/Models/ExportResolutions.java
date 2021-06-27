package com.kinetic.sh.Models;

public class ExportResolutions {
    private int height;
    private String resType;

    public ExportResolutions(String str, int i) {
        this.resType = str;
        this.height = i;
    }

    public String getResType() {
        return this.resType;
    }

    public int getHeight() {
        return this.height;
    }

    public void setResType(String str) {
        this.resType = str;
    }

    public void setHeight(int i) {
        this.height = i;
    }
}
