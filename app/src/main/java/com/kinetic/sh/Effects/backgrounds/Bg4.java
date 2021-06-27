package com.kinetic.sh.Effects.backgrounds;

import android.content.Context;
import android.graphics.Color;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Bg4 extends BaseBg {
    public Bg4(Context context, LottieAnimationView lottieAnimationView) {
        super(context, lottieAnimationView);
    }

    public void init() {
        init(R.raw.bg4);
        changePathColor(Color.parseColor("#FFC110"), "Shape Layer 1", "Front 2", "**");
        changePathColor(Color.parseColor("#34312A"), "Dark Gray Solid 1", "**");
        _load();
    }
}
