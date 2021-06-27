package com.kinetic.sh.Effects.TileEffects;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Tile94 extends BaseTile {
    private final LottieAnimationView animationView;
    private final Context context;
    private final LinearLayout linearLayout;



    public Tile94(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;


    }

    public void init() {
        init(R.raw.tile94);
        addTextDelegateField("MAIN TITLE", "**");
        addTextDelegateField("GOES", "**");
        addTextDelegateField("OVER", "**");
        addTextDelegateField("HERE", "**");
        addTextDelegateField("www.busyboxx.com", "**");




        //changeFont("THARWATEMARARUQAA.ttf");
        changePathColor(Color.parseColor("#327897"), "**");
        changePathColor(Color.parseColor("#9004A1"),  "072 Brought Together-4K","ENTRY","TITLE CONTAINER","ANIMA TITLE 35","Shape Layer 7", "**");

        _load();
    }

}
