package com.kinetic.sh.Helpers;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.Effects.TileEffects.BaseTile;
import com.kinetic.sh.Effects.TileEffects.TileEffectBuilder;

import java.util.List;

public class TextEffect {
    private final String TAG = "TextEffect";
    private final LottieAnimationView animationView;
    private final Context context;
    private BaseTile effectInstance;
    private final String effectName;
    private final LinearLayout linearLayout;

    public TextEffect(Context context2, String str, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        this.context = context2;
        this.effectName = str;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;
    }

    public String getEffectName() {
        return this.effectName;
    }

    public void init() {
        Log.e(TAG, "INIT: " + this.effectName);
        this.effectInstance = TileEffectBuilder.create(this.effectName, this.context, this.animationView, this.linearLayout);
    }

    public void startAnimation() {
        this.effectInstance.init();
    }

    public List<EditText> getEditFields() {
        return this.effectInstance.getEditTexts();
    }

    public void setEditFields(List<EditText> list) {
        this.effectInstance.setEditTexts(list);
    }
}
