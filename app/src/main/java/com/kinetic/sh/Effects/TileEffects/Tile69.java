package com.kinetic.sh.Effects.TileEffects;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Tile69 extends BaseTile {
    private final LottieAnimationView animationView;
    private final Context context;
    private final LinearLayout linearLayout;



    public Tile69(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;


    }

    public void init() {
        init(R.raw.tile69);
        addTextDelegateField("MODERN TITLES", "**");
        addTextDelegateField("ULTIMATE", "**");
        addTextDelegateField("SOLUTION", "**");

        //changeFont("comforta_regular.ttf");
        changePathColor(Color.parseColor("#FF00FF00"), "**");
        changePathColor(-3,  "010 Tight Pack-4K","Entry","TITLE CONTAINER","ANIMA TITLE 02","Line 2", "**");
        changePathColor(Color.parseColor("#329791"),  "010 Tight Pack-4K","Entry","TITLE CONTAINER","ANIMA TITLE 02","Line", "**");
        changePathColor(-3,  "010 Tight Pack-4K","Entry","TITLE CONTAINER","ANIMA TITLE 02","Line 3", "**");





        _load();
    }

}
