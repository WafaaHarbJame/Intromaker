package com.kinetic.sh.Effects.TileEffects;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Tile88 extends BaseTile {
    private final LottieAnimationView animationView;
    private final Context context;
    private final LinearLayout linearLayout;



    public Tile88(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;


    }

    public void init() {
        init(R.raw.tile88);
        addTextDelegateField("STYLISHHHH", "**");
        addTextDelegateField("MODERN TITLES", "**");
        addTextDelegateField("100+ DESIGNS MADE", "**");
        addTextDelegateField("EASY FOR YOU", "**");
        addTextDelegateField("BUY NOW!", "**");
        addTextDelegateField("busyboxx.com", "**");


        //changeFont("comforta_regular.ttf");
        changePathColor(Color.parseColor("#327897"), "**");
        changePathColor(Color.parseColor("#329791"),  "060 All Over Again-4K","ENTRY","TITLE CONTAINER","ANIMA TITLE","Shape Layer 2", "**");

        _load();
    }

}
