package com.kinetic.sh.Effects.backgrounds;

import android.content.Context;
import android.graphics.Color;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Bg5 extends BaseBg {
    public Bg5(Context context, LottieAnimationView lottieAnimationView) {
        super(context, lottieAnimationView);
    }

    public void init() {
        init(R.raw.bg5);
        changePathColor(Color.parseColor("#639E2A"), "Shape Layer 1", "Front", "**");
        changePathColor(-1, "Shape Layer 1", "Front 2", "**");
        _load();
    }
}
