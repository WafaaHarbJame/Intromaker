package com.kinetic.sh.Effects.backgrounds;

import android.content.Context;
import android.graphics.Color;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Bg14 extends BaseBg {
    public Bg14(Context context, LottieAnimationView lottieAnimationView) {
        super(context, lottieAnimationView);
    }

    public void init() {
        init(R.raw.bg14);
        changePathColor(Color.parseColor("#FF8B06"), "Elements", "**");
        changePathColor(Color.parseColor("#312B26"), "Dark Orange Solid 1", "**");
        _load();
    }
}
