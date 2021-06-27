package com.kinetic.sh.Effects.TileEffects;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Tile90 extends BaseTile {
    private final LottieAnimationView animationView;
    private final Context context;
    private final LinearLayout linearLayout;



    public Tile90(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;


    }

    public void init() {
        init(R.raw.tile90);
        addTextDelegateField("MODERN", "**");
        //addTextDelegateField("TITLES DESIGN", "**");
        addTextDelegateField("BRAND NEW", "**");

        //changeFont("comforta_regular.ttf");
        changePathColor(Color.parseColor("#327897"), "**");
        changePathColor(Color.parseColor("#329791"),  "062 Level Play-4K","ENTRY","TITLE CONTAINER","ANIMA TITLE","Rectangle 4", "**");
        changePathColor(Color.parseColor("#327897"),  "038 The Right Fit-4K","Entry","TITLE CONTAINER","ANIMA TITLE 07","Shape Layer 2", "**");

        _load();
    }

}
