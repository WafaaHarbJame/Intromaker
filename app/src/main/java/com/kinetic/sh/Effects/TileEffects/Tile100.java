package com.kinetic.sh.Effects.TileEffects;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Tile100 extends BaseTile {
    private final LottieAnimationView animationView;
    private final Context context;
    private final LinearLayout linearLayout;



    public Tile100(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;


    }

    public void init() {
        init(R.raw.tile100);
        addTextDelegateField("THIS", "**");
        addTextDelegateField("IS", "**");
        addTextDelegateField("YOUTUBE", "**");
        addTextDelegateField("NEW", "**");
        addTextDelegateField("ESSENTIAL", "**");
        addTextDelegateField("LIBRARY", "**");
        addTextDelegateField("#", "**");
        addTextDelegateField("#CHANNEL", "**");
        addTextDelegateField("#DESIGN", "**");


        //changeFont("THARWATEMARARUQAA.ttf");

        changePathColor(Color.parseColor("#327897"),"**");
        changePathColor(Color.parseColor("#FFFFFF"),  "Opener_04 1 1","Layer 5 Outlines", "**");
        changePathColor(Color.parseColor("#FF0000"),  "Opener_04 1 1","Layer 5 Outliness","Group 1", "**");
        changePathColor(Color.parseColor("#1B1B1B"),  "Opener_04 1 1","Layer 3 Outlines 3", "**");
        changePathColor(Color.parseColor("#FFFFFF"),  "Opener_04 1 1","white", "**");
        changePathColor(Color.parseColor("#1B1B1B"),  "Opener_04 1 1","Layer 5 Outlines 3", "**");
        changePathColor(Color.parseColor("#FF0000"),  "Opener_04 1 1","Layer 3 Outlines 4", "**");
        changePathColor(Color.parseColor("#FFFFFF"),  "Opener_04 1 1","Shape Layer 1", "**");
        changePathColor(Color.parseColor("#1B1B1B"),  "Opener_04 1 1","black bg", "**");
        changePathColor(Color.parseColor("#FFFFFF"),  "Opener_04 1 1","Shape Layer 3", "**");
        changePathColor(Color.parseColor("#FFFFFF"),  "Opener_04 1 1","Shape Layer 4", "**");
        changePathColor(Color.parseColor("#FFFFFF"),  "Opener_04 1 1","Layer 5 Outlines 2", "**");
        changePathColor(Color.parseColor("#FFFFFF"),  "Opener_04 1 1","Shape Layer 5", "**");
        changePathColor(Color.parseColor("#FF0000"),  "Opener_04 1 1","Shape Layer 6", "**");
        changePathColor(Color.parseColor("#FFFFFF"),  "Opener_04 1 1","Shape Layer 10", "**");
        changePathColor(Color.parseColor("#1B1B1B"),  "Opener_04 1 1","black bg1", "**");
        changePathColor(Color.parseColor("#F04524"),  "Opener_04 1 1","black bg", "**");





        _load();
    }

}
