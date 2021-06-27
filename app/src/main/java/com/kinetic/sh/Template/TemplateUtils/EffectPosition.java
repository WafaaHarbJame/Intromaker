package com.kinetic.sh.Template.TemplateUtils;

public class EffectPosition {
    private float scale;
    private float x;
    private float y;

    public EffectPosition(float f, float f2, float f3) {
        this.scale = f;
        this.x = f2;
        this.y = f3;
    }

    public float getScale() {
        return this.scale;
    }

    public void setScale(float f) {
        this.scale = f;
    }

    public float getX() {
        return this.x;
    }

    public void setX(float f) {
        this.x = f;
    }

    public float getY() {
        return this.y;
    }

    public void setY(float f) {
        this.y = f;
    }
}
