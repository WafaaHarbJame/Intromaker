package com.kinetic.sh.Helpers;

import android.content.Context;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.Effects.backgrounds.BaseBg;
import com.kinetic.sh.Effects.backgrounds.BgBuilder;

public class DynamicBg {
    private LottieAnimationView animationView;
    private BaseBg classInstance;
    private String className;
    private Context context;

    public DynamicBg(Context context2, String str, LottieAnimationView lottieAnimationView) {
        this.context = context2;
        this.className = str;
        this.animationView = lottieAnimationView;
    }

    public String getClassName() {
        return this.className;
    }

    public void init() {
        this.classInstance = BgBuilder.create(this.className, this.context, this.animationView);
    }

    public void startAnimation() {
        this.classInstance.init();
    }
}
