package com.kinetic.sh.Effects.TileEffects;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Tile83 extends BaseTile {
    private final LottieAnimationView animationView;
    private final Context context;
    private final LinearLayout linearLayout;



    public Tile83(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;


    }

    public void init() {
        init(R.raw.tile83);
        addTextDelegateField("CREATIVE", "**");
        addTextDelegateField("INSPIRATION", "**");
        addTextDelegateField("YOU NEED", "**");
        addTextDelegateField("100", "**");
        addTextDelegateField("MODERN", "**");
        addTextDelegateField("TITLES", "**");
        addTextDelegateField("www.busyboxx.com", "**");


        //changeFont("comforta_regular.ttf");
        changePathColor(Color.parseColor("#327897"), "**");

        _load();
    }

}
