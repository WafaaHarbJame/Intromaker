package com.kinetic.sh.Effects.TileEffects;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Tile86 extends BaseTile {
    private final LottieAnimationView animationView;
    private final Context context;
    private final LinearLayout linearLayout;



    public Tile86(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;


    }

    public void init() {
        init(R.raw.tile86);
        addTextDelegateField("BRAND NEW", "**");
        addTextDelegateField("100 TITLES", "**");
        addTextDelegateField("EASY AND", "**");
        addTextDelegateField("FAST TO RENDER", "**");
        changeFont("THARWATEMARARUQAA.ttf");

        //changeFont("comforta_regular.ttf");
        changePathColor(Color.parseColor("#327897"), "**");
        changePathColor(Color.parseColor("#329791"),  "057 Clear Presence-4K","ENTRY","TITLE CONTAINER","ANIMA TITLE","Shape Layer 3", "**");

        _load();
    }

}
