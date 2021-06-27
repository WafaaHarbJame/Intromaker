package com.kinetic.sh.Effects.backgrounds;

import android.content.Context;
import android.graphics.Color;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Bg11 extends BaseBg {
    public Bg11(Context context, LottieAnimationView lottieAnimationView) {
        super(context, lottieAnimationView);
    }

    public void init() {
        init(R.raw.bg11);
        int parseColor = Color.parseColor("#50E3C2");
        changeMultiPathColorAtDifferentIntensity(parseColor, new float[]{1.0f, 0.5f}, false, new String[]{"Shape Layer 1", "Front 2", "**"}, new String[]{"Shape Layer 1", "Front", "**"});
        changePathColor(Color.parseColor("#23073C"), "Shape Layer 1", "Front 3", "**");
        _load();
    }
}
