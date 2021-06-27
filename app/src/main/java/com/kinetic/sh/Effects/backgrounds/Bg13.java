package com.kinetic.sh.Effects.backgrounds;

import android.content.Context;
import android.graphics.Color;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Bg13 extends BaseBg {
    public Bg13(Context context, LottieAnimationView lottieAnimationView) {
        super(context, lottieAnimationView);
    }

    public void init() {
        init(R.raw.bg13);
        changePathColor(Color.parseColor("#23F9A9"), "elm", "**");
        changePathColor(Color.parseColor("#3D77FF"), "Background", "**");
        _load();
    }
}
