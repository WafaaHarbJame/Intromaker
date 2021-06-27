package com.kinetic.sh.Effects.backgrounds;

import android.content.Context;
import android.graphics.Color;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Bg7 extends BaseBg {
    private LottieAnimationView animationView;
    private Context context;

    public Bg7(Context context2, LottieAnimationView lottieAnimationView) {
        super(context2, lottieAnimationView);
    }

    public void init() {
        init(R.raw.bg7);
        changePathColor(Color.parseColor("#2398BC"), "Shape Layer 1", "Front", "**");
        changePathColor(Color.parseColor("#A2E9FF"), "Shape Layer 1", "Front 2", "**");
        _load();
    }
}
