package com.kinetic.sh.Template.TemplateUtils;

import java.util.ArrayList;
import java.util.List;

public class DynamicBgMeta {
    private List<BgElementColor> bgElementColors = new ArrayList<>();
    private String name;
    private float transperency = 0.0f;

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public float getTransperency() {
        return this.transperency;
    }

    public void setTransperency(float f) {
        this.transperency = f;
    }

    public List<BgElementColor> getBgElementColors() {
        return this.bgElementColors;
    }

    public void setBgElementColors(List<BgElementColor> list) {
        this.bgElementColors = list;
    }
}
