package com.kinetic.sh.Effects.TileEffects;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Tile67 extends BaseTile {
    private final LottieAnimationView animationView;
    private final Context context;
    private final LinearLayout linearLayout;



    public Tile67(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;


    }

    public void init() {
        init(R.raw.tile67);
        addTextDelegateField("SLEEK", "**");
        addTextDelegateField("TYPOGRAPHY", "**");
        addTextDelegateField("USEFUL", "**");

        //changeFont("comforta_regular.ttf");
        changePathColor(Color.parseColor("#FF00FF00"), "**");
        changePathColor(-1,  "ENTRY","TITLE CONTAINER","ANIMA TITLE 15","Shape Layer 1", "**");

        _load();
    }

}
