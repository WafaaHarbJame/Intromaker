package com.kinetic.sh.Effects.TileEffects;

import android.content.Context;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Tile35 extends BaseTile {
    private LottieAnimationView animationView;
    private Context context;
    private LinearLayout linearLayout;

    public Tile35(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;
    }

    public void init() {
        init(R.raw.tile35);
        addTextDelegateField("MODERN", "comp", "Text 03-1", "**");
        addTextDelegateField("CREATIVE", "comp", "Text 03-2", "**");
        changeFont("Montserrat-Black.ttf");
        changePathColor(-1, "**");
        _load();
    }
}
