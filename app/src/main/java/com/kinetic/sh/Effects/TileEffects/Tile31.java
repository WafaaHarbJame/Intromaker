package com.kinetic.sh.Effects.TileEffects;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Tile31 extends BaseTile {
    private LottieAnimationView animationView;
    private Context context;
    private LinearLayout linearLayout;

    public Tile31(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;
    }

    public void init() {
        init(R.raw.tile31);
        addTextDelegateField("ALWAYS", "comp", "ALWAYS", "**");
        addTextDelegateField("BE", "comp", "BE", "**");
        addTextDelegateField("HAPPY", "comp", "HAPPY", "**");
        changeFont("Montserrat-Black.ttf");
        changePathColor(Color.parseColor("#FF00FF00"), "**");
        _load();
    }
}
