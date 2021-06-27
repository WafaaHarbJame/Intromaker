package com.kinetic.sh.Effects.backgrounds;

import android.content.Context;
import android.graphics.Color;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Bg1 extends BaseBg {
    public Bg1(Context context, LottieAnimationView lottieAnimationView) {
        super(context, lottieAnimationView);
    }

    public void init() {
        init(R.raw.bg1);
        changePathColor(Color.parseColor("#50E3C2"), "Elements_01", "**");
        changePathColor(0xff000000, "Placeholder_01", "**");
        changePathColorLocal(0xff000000, "BG", "**");
        _load();
    }
}
