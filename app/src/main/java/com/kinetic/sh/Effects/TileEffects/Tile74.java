package com.kinetic.sh.Effects.TileEffects;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Tile74 extends BaseTile {
    private final LottieAnimationView animationView;
    private final Context context;
    private final LinearLayout linearLayout;



    public Tile74(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;


    }

    public void init() {
        init(R.raw.tile74);
        addTextDelegateField("FROM NOW ON", "**");
        addTextDelegateField("Live the", "**");
        addTextDelegateField("Life you", "**");
        addTextDelegateField("Want in", "**");
        addTextDelegateField("AND BE HAPPY", "**");
        addTextDelegateField("DO IT NOW!", "**");



        //changeFont("comforta_regular.ttf");
        changePathColor(Color.parseColor("#327897"), "**");
        changePathColor(Color.parseColor("#329791"),  "034 Living Dream-4K","ENTRY","TITLE CONTAINER","ANIMA TITLE 41","Shape Layer 9", "**");
        changePathColor(Color.parseColor("#327897"),  "034 Living Dream-4K","ENTRY","TITLE CONTAINER","ANIMA TITLE 41","Shape Layer 11", "**");
        changePathColor(Color.parseColor("#329791"),  "034 Living Dream-4K","ENTRY","TITLE CONTAINER","ANIMA TITLE 41","Shape Layer 13", "**");

        _load();
    }

}
