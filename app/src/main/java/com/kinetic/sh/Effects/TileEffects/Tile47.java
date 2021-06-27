package com.kinetic.sh.Effects.TileEffects;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Tile47 extends BaseTile {
    private final LottieAnimationView animationView;
    private final Context context;
    private final LinearLayout linearLayout;



    public Tile47(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;


    }

    @Override
    public void init() {
        init(R.raw.tile47);
        addTextDelegateField("sharq", "**");
        addTextDelegateField("sharq1", "**");
        changeFont("Montserrat-Black.ttf");
        changePathColor(-1, "Quotes_05","Shape Layer 3", "**");
        changePathColor(Color.parseColor("#FF00FF00"), "**");
        _load();
    }
}
