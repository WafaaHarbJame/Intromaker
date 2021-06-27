package com.kinetic.sh.Models;

import android.graphics.drawable.GradientDrawable;

public class GradientMeta {
    private GradientDrawable.Orientation Orientation = GradientDrawable.Orientation.LEFT_RIGHT;
    private int[] spectrum;

    public GradientMeta(int[] iArr) {
        this.spectrum = iArr;
    }
}
