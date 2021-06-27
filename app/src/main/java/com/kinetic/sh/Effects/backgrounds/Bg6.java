package com.kinetic.sh.Effects.backgrounds;

import android.content.Context;
import android.graphics.Color;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Bg6 extends BaseBg {
    public Bg6(Context context, LottieAnimationView lottieAnimationView) {
        super(context, lottieAnimationView);
    }

    public void init() {
        init(R.raw.bg6);
        int parseColor = Color.parseColor("#0083BD");
        changeMultiPathColorAtDifferentIntensity(parseColor, new float[]{1.0f, 0.5f}, false, new String[]{"Shape Layer 1", "Front 3", "**"}, new String[]{"Shape Layer 1", "Front 2", "**"});
        changePathColor(Color.parseColor("#000849"), "Shape Layer 1", "Front", "**");
        _load();
    }
}
