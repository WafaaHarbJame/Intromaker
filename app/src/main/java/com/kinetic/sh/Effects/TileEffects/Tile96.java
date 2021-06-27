package com.kinetic.sh.Effects.TileEffects;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Tile96 extends BaseTile {
    private final LottieAnimationView animationView;
    private final Context context;
    private final LinearLayout linearLayout;



    public Tile96(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;


    }

    public void init() {
        init(R.raw.tile96);
        addTextDelegateField("ULTIMATE", "**");
        addTextDelegateField("CORPORATE TITLES", "**");
        addTextDelegateField("MINIMALIST STYLE", "**");

        //changeFont("THARWATEMARARUQAA.ttf");
        changePathColor(Color.parseColor("#327897"), "**");
        changePathColor(Color.parseColor("#329791"),  "082 Freedom Exchange-4K","ENTRY","TITLE CONTAINER","ANIMA TITLE","Shape Layer 11", "**");
        changePathColor(Color.parseColor("#02FFA9"),  "082 Freedom Exchange-4K","ENTRY","TITLE CONTAINER","ANIMA TITLE","Shape Layer 12", "**");

        _load();
    }

}
