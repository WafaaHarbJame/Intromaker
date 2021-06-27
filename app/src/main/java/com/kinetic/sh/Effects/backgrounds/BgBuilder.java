package com.kinetic.sh.Effects.backgrounds;

import android.content.Context;

import com.airbnb.lottie.LottieAnimationView;

public class BgBuilder {
    public static BaseBg create(String name, Context context, LottieAnimationView view) {
        switch (name) {
            case "Bg1":
                return new Bg1(context, view);
            case "Bg2":
                return new Bg2(context, view);
            case "Bg3":
                return new Bg3(context, view);
            case "Bg4":
                return new Bg4(context, view);
            case "Bg5":
                return new Bg5(context, view);
            case "Bg6":
                return new Bg6(context, view);
            case "Bg7":
                return new Bg7(context, view);
            case "Bg8":
                return new Bg8(context, view);
            case "Bg9":
                return new Bg9(context, view);
            case "Bg10":
                return new Bg10(context, view);
            case "Bg11":
                return new Bg11(context, view);
            case "Bg12":
                return new Bg12(context, view);
            case "Bg13":
                return new Bg13(context, view);
            case "Bg14":
                return new Bg14(context, view);
            default:
                return null;
        }
    }

}
