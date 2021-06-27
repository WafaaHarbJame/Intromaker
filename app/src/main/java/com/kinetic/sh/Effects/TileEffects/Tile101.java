package com.kinetic.sh.Effects.TileEffects;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Tile101 extends BaseTile {
    private final LottieAnimationView animationView;
    private final Context context;
    private final LinearLayout linearLayout;



    public Tile101(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;


    }

    public void init() {
        init(R.raw.tile101);
        addTextDelegateField("oriental kingdom art production", "**");
        addTextDelegateField("@alhamalaisharq", "**");

        //changeFont("THARWATEMARARUQAA.ttf");
        changePathColor(Color.parseColor("#FFFFFF"),  "Opener_08 1","Shape Layer 1", "**");
        changePathColor(Color.parseColor("#FF0000"),  "Opener_08 1","3d YT logo","Layer 4 Outlines", "**");
        changePathColor(Color.parseColor("#FFFFFF"),  "Opener_08 1","3d YT logo","youtu", "**");

        _load();
    }

}
