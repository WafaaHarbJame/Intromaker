package com.kinetic.sh.Effects.TileEffects;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Tile110 extends BaseTile {
    private final LottieAnimationView animationView;
    private final Context context;
    private final LinearLayout linearLayout;



    public Tile110(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;


    }

    public void init() {
        init(R.raw.tile110);
        addTextDelegateField("EasyEdit", "**");
        //changeFont("THARWATEMARARUQAA.ttf");

       // changePathColor(Color.parseColor("#327897"), "**");
        changePathColor(Color.parseColor("#DA542E"),  "Elements_016 1 1","Shape Layer 1", "**");
        changePathColor(Color.parseColor("#FFFFFF"),  "Elements_016 1 1","Social_Media_25 Outlines","Group 7" ,"**");
        changePathColor(Color.parseColor("#DA542E"),  "Elements_016 1 1","Box_01", "**");



        _load();
    }

}
