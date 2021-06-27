package com.kinetic.sh.Effects.TileEffects;

import android.content.Context;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Tile68 extends BaseTile {
    private final LottieAnimationView animationView;
    private final Context context;
    private final LinearLayout linearLayout;



    public Tile68(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;


    }

    public void init() {
        init(R.raw.tile68);
        addTextDelegateField("TITLES", "**");
        addTextDelegateField("RENDER", "**");
        addTextDelegateField("EASY-TO-USE & FAST TO", "**");
        addTextDelegateField("BRAND NEW 100+ TITLES. GET IT NOW!", "**");

        //changeFont("comforta_regular.ttf");
        //changePathColor(Color.parseColor("#FF00FF00"), "**");
               changePathColor(-1,  "ENTRY","TITLE CONTAINER","ANIMA TITLE","Shape Layer 10", "**");
        changePathColor(-1,  "ENTRY","TITLE CONTAINER","ANIMA TITLE","Shape Layer 8", "**");

        _load();
    }

}
