package com.kinetic.sh.Effects.TileEffects;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Tile93 extends BaseTile {
    private final LottieAnimationView animationView;
    private final Context context;
    private final LinearLayout linearLayout;



    public Tile93(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;


    }

    public void init() {
        init(R.raw.tile93);
        addTextDelegateField("every", "**");
        addTextDelegateField("moment", "**");
        addTextDelegateField("is a fresh", "**");
        addTextDelegateField("beginning", "**");




        //changeFont("THARWATEMARARUQAA.ttf");
        changePathColor(Color.parseColor("#327897"), "**");
        changePathColor(Color.parseColor("#9004A1"),  "068 Minimal Wants-4K","ENTRY","TITLE CONTAINER","ANIMA TITLE","Shape Layer 12", "**");


        _load();
    }

}
