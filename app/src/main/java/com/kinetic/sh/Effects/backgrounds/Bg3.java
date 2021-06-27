package com.kinetic.sh.Effects.backgrounds;

import android.content.Context;
import android.graphics.Color;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Bg3 extends BaseBg {
    public Bg3(Context context, LottieAnimationView lottieAnimationView) {
        super(context, lottieAnimationView);
    }

    public void init() {
        init(R.raw.bg3);
        changePathColor(Color.parseColor("#1C0133"), "Shape Layer 1", "Front 2", "**");
        _load();
    }
}
