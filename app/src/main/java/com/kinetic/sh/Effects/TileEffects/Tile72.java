package com.kinetic.sh.Effects.TileEffects;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Tile72 extends BaseTile {
    private final LottieAnimationView animationView;
    private final Context context;
    private final LinearLayout linearLayout;



    public Tile72(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;

    }

    public void init() {
        init(R.raw.tile72);
        addTextDelegateField("MODERN", "**");
        addTextDelegateField("TITLES PACK", "**");
        addTextDelegateField("MINIMALIST STYLE", "**");

        //changeFont("comforta_regular.ttf");
        changePathColor(Color.parseColor("#327897"), "**");
        changePathColor(Color.parseColor("#329791"),  "019 Ground Reality-4K","ENTRY","TITLE CONTAINER","ANIMA TITLE 13","Line 4", "**");
        changePathColor(Color.parseColor("#329791"),  "019 Ground Reality-4K","ENTRY","TITLE CONTAINER","ANIMA TITLE 13","Line 6", "**");



        _load();
    }

}
