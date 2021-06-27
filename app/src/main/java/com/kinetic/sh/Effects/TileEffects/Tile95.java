package com.kinetic.sh.Effects.TileEffects;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Tile95 extends BaseTile {
    private final LottieAnimationView animationView;
    private final Context context;
    private final LinearLayout linearLayout;



    public Tile95(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;


    }

    public void init() {
        init(R.raw.tile95);
        addTextDelegateField("Yeah!", "**");
        addTextDelegateField("Need Content?", "**");
        addTextDelegateField("www.busyboxx.com", "**");
        addTextDelegateField("Yes!", "**");


        //changeFont("THARWATEMARARUQAA.ttf");
        changePathColor(Color.parseColor("#327897"), "**");
        changePathColor(Color.parseColor("#329791"),  "078 Directed Towards-4K","ENTRY","TITLE CONTAINER","ANIMA TITLE 37","BUBBLE","Deep Cyan Solid 1", "**");


        _load();
    }

}
