package com.kinetic.sh.Effects.TileEffects;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Tile75 extends BaseTile {
    private final LottieAnimationView animationView;
    private final Context context;
    private final LinearLayout linearLayout;



    public Tile75(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;


    }

    public void init() {
        init(R.raw.tile75);
        addTextDelegateField("REALLY", "**");
        addTextDelegateField("PERFECT FOR", "**");
        addTextDelegateField("CORPORATE & BUSINESS", "**");
        addTextDelegateField("STYLE", "**");

        //changeFont("comforta_regular.ttf");
        changePathColor(Color.parseColor("#327897"), "**");
        changePathColor(Color.parseColor("#329791"),  "038 The Right Fit-4K","Entry","TITLE CONTAINER","ANIMA TITLE 07","Shape Layer 1", "**");
        changePathColor(Color.parseColor("#327897"),  "038 The Right Fit-4K","Entry","TITLE CONTAINER","ANIMA TITLE 07","Shape Layer 2", "**");

        _load();
    }

}
