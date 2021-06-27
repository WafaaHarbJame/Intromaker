package com.kinetic.sh.Effects.TileEffects;

import android.content.Context;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Tile22 extends BaseTile {
    private LottieAnimationView animationView;
    private Context context;
    private LinearLayout linearLayout;

    public Tile22(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;
    }

    public void init() {
        init(R.raw.tile22);

        addTextDelegateField("INTROMAKER", "Text A", "**");
        addTextDelegateField("presents".toUpperCase(), "Text B", "**");
        changePathColor(-12303292, "**");
        _load();
    }
}
