package com.kinetic.sh.Effects.backgrounds;

import android.content.Context;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Bg9 extends BaseBg {
    public Bg9(Context context, LottieAnimationView lottieAnimationView) {
        super(context, lottieAnimationView);
    }

    public void init() {
        init(R.raw.bg9);
        removeColorPickers();
        _load();
    }
}
