package com.kinetic.sh.Effects.TileEffects;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Tile92 extends BaseTile {
    private final LottieAnimationView animationView;
    private final Context context;
    private final LinearLayout linearLayout;



    public Tile92(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;


    }

    public void init() {
        init(R.raw.tile92);
        addTextDelegateField("TEMPLATMMES", "**");
        addTextDelegateField("GET IT NOW", "**");
        addTextDelegateField("busyboxx.com", "**");



        //changeFont("THARWATEMARARUQAA.ttf");
        changePathColor(Color.parseColor("#327897"), "**");
        changePathColor(Color.parseColor("#329791"),  "066 Visual Reads-4K","ENTRY","TITLE CONTAINER","ANIMA TITLE 26","Medium Orange Solid 1", "**");
        changePathColor(Color.parseColor("#9004A1"),  "066 Visual Reads-4K","ENTRY","TITLE CONTAINER","ANIMA TITLE 26","Medium Cyan Solid 1", "**");


        _load();
    }

}
