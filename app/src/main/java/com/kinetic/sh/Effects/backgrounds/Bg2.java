package com.kinetic.sh.Effects.backgrounds;

import android.content.Context;
import android.graphics.Color;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Bg2 extends BaseBg {
    public Bg2(Context context, LottieAnimationView lottieAnimationView) {
        super(context, lottieAnimationView);
    }

    public void init() {
        init(R.raw.bg2);
        changePathColor(Color.parseColor("#DAE349"), "Elements", "**");
        changePathColor(Color.parseColor("#43938D"), "BG 2", "**");
        _load();
    }
}
