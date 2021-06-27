package com.kinetic.sh.Effects.TileEffects;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Tile85 extends BaseTile {
    private final LottieAnimationView animationView;
    private final Context context;
    private final LinearLayout linearLayout;



    public Tile85(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;


    }

    public void init() {
        init(R.raw.tile85);
        addTextDelegateField("MAIN TEXT HERE", "**");
        addTextDelegateField("HEADER TEXT HERE", "**");
        addTextDelegateField("FOOTER TEXT", "**");


        //changeFont("THARWATEMARARUQAA.ttf");

        //changeFont("comforta_regular.ttf");
        changePathColor(Color.parseColor("#327897"), "**");
        changePathColor(Color.parseColor("#321997"),  "056 Lead Them On-4K","ENTRY","TITLE CONTAINER","ANIMA TITLE 18","Shape Layer 1", "**");

        _load();
    }

}
