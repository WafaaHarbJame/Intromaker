package com.kinetic.sh.Effects.TileEffects;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Tile64 extends BaseTile {
    private final LottieAnimationView animationView;
    private final Context context;
    private final LinearLayout linearLayout;



    public Tile64(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;


    }

    public void init() {
        init(R.raw.tile64);
        addTextDelegateField("UNIQUE PACK", "**");
        addTextDelegateField("FOR YOUR ELEGANT MEDIAS", "**");

        //changeFont("comforta_regular.ttf");
        changePathColor(Color.parseColor("#FF00FF00"), "**");
        _load();
    }

}
