package com.kinetic.sh.Effects.TileEffects;

import android.content.Context;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Tile24 extends BaseTile {
    private LottieAnimationView animationView;
    private Context context;
    private LinearLayout linearLayout;

    public Tile24(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;
    }

    public void init() {
        init(R.raw.tile24);
        addTextDelegateField("MOTION", true, new String[0]);
        addTextDelegateField("ALPHA", "Pre-comp", "title7_text2", "**");
        addTextDelegateField("PRESENTS", "Pre-comp", "title7_text3", "**");
        changePathColor(-1, "**");
        _load();
    }
}
