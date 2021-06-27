package com.kinetic.sh.Effects.backgrounds;

import android.content.Context;
import android.graphics.Color;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Bg10 extends BaseBg {
    public Bg10(Context context, LottieAnimationView lottieAnimationView) {
        super(context, lottieAnimationView);
    }

    public void init() {
        init(R.raw.bg10);
        changePathColor(Color.parseColor("#5EFFBD"), "Elements", "**");
        changePathColor(Color.parseColor("#202020"), "Dark Gray Solid 1", "**");
        _load();
    }
}
