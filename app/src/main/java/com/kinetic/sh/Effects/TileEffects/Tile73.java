package com.kinetic.sh.Effects.TileEffects;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Tile73 extends BaseTile {
    private final LottieAnimationView animationView;
    private final Context context;
    private final LinearLayout linearLayout;



    public Tile73(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;


    }

    public void init() {
        init(R.raw.tile73);
        addTextDelegateField("HIGH QUALITY", "**");
        addTextDelegateField("SECOND TEXT HERE", "**");

        //changeFont("comforta_regular.ttf");
        changePathColor(Color.parseColor("#327897"), "**");
        changePathColor(Color.parseColor("#329791"),  "020 Eligible Stance-4K","Entry","TITLE CONTAINER","ANIMA TITLE 03","Line", "**");
        changePathColor(Color.parseColor("#973292"),  "020 Eligible Stance-4K","Entry","TITLE CONTAINER","ANIMA TITLE 03","Shape Layer 1", "**");



        _load();
    }

}
