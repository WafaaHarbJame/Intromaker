package com.kinetic.sh.Effects.backgrounds;

import android.content.Context;
import android.graphics.Color;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Bg12 extends BaseBg {
    public Bg12(Context context, LottieAnimationView lottieAnimationView) {
        super(context, lottieAnimationView);
    }

    public void init() {
        init(R.raw.bg12);
        changePathColor(Color.parseColor("#D8A5FF"), "Shape Layer 1", "Front", "**");
        changePathColor(Color.parseColor("#8000E3"), "Purple Solid 1", "**");
        _load();
    }
}
