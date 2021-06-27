package com.kinetic.sh.Effects.TileEffects;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Tile87 extends BaseTile {
    private final LottieAnimationView animationView;
    private final Context context;
    private final LinearLayout linearLayout;



    public Tile87(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;


    }

    public void init() {
        init(R.raw.tile87);
        addTextDelegateField("MAIN TEXT", "**");
        addTextDelegateField("FOOTER TEXT", "**");
        addTextDelegateField("HEADER TEXT", "**");

        //changeFont("comforta_regular.ttf");
        changePathColor(Color.parseColor("#327897"), "**");
        changePathColor(Color.parseColor("#329791"),  "058 Plain Stop-4K","ENTRY","TITLE CONTAINER","ANIMA TITLE 20","Shape Layer 7", "**");
        changePathColor(Color.parseColor("#327897"),  "058 Plain Stop-4K","ENTRY","TITLE CONTAINER","ANIMA TITLE 20","Shape Layer 1", "**");

        _load();
    }

}
