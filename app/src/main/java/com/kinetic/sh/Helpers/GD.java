package com.kinetic.sh.Helpers;

import android.graphics.drawable.GradientDrawable;

public class GD extends GradientDrawable {
    private int[] clrs;
    private int position;

    public GD(GradientDrawable.Orientation orientation, int[] iArr) {
        super(orientation, iArr);
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int i) {
        this.position = i;
    }

    public int[] getClrs() {
        return this.clrs;
    }

    public void setClrs(int[] iArr) {
        this.clrs = iArr;
    }
}
