package com.kinetic.sh.Effects.backgrounds;

import android.content.Context;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.Effects.Bg;

public abstract class BaseBg extends Bg {
    public BaseBg(Context context, LottieAnimationView lottieAnimationView) {
        super(context, lottieAnimationView);
    }

    public abstract void init();
}
