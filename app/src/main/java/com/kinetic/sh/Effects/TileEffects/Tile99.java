package com.kinetic.sh.Effects.TileEffects;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Tile99 extends BaseTile {
    private final LottieAnimationView animationView;
    private final Context context;
    private final LinearLayout linearLayout;



    public Tile99(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;


    }

    public void init() {
        init(R.raw.tile99);
        addTextDelegateField("THOMAS O’BRIEN", "**");

        //changeFont("THARWATEMARARUQAA.ttf");
        changePathColor(Color.parseColor("#327897"), "Social_media_006 1 1","Line","**");
        changePathColor(Color.parseColor("#F04524"),  "Social_media_006 1 1","YT_Logo", "**");
        changePathColor(Color.parseColor("#FFFFFF"),  "Social_media_006 1 1","YT_Logo","Group 2", "**");

        _load();
    }

}
