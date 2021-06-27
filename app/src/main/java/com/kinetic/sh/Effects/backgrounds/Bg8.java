package com.kinetic.sh.Effects.backgrounds;

import android.content.Context;
import android.graphics.Color;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Bg8 extends BaseBg {
    public Bg8(Context context, LottieAnimationView lottieAnimationView) {
        super(context, lottieAnimationView);
    }

    public void init() {
        init(R.raw.bg8);
        int parseColor = Color.parseColor("#FFBE02");
        changeMultiPathColorAtDifferentIntensity(parseColor, new float[]{1.0f, 1.0f}, false, new String[]{"Shape Layer 3", "Front 2", "**"}, new String[]{"Shape Layer 3", "Rectangle 2}", "**"});
        changePathColor(Color.parseColor("#2D2D2D"), "Shape Layer 3", "Front", "**");
        _load();
    }
}
