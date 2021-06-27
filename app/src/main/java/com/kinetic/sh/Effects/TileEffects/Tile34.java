package com.kinetic.sh.Effects.TileEffects;

import android.content.Context;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Tile34 extends BaseTile {
    private LottieAnimationView animationView;
    private Context context;
    private LinearLayout linearLayout;

    public Tile34(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;
    }

    public void init() {
        init(R.raw.tile34);
        addTextDelegateField("EVERY MOMENT IS THE", "comp", "Text 02-1", "**");
        addTextDelegateField("FRESH BIGINING", "comp", "Text 02-2", "**");
        changeFont("Montserrat-Black.ttf");
        changePathColor(-1, "comp", "**");
        _load();
    }
}
