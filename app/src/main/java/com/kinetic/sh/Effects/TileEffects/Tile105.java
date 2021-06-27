package com.kinetic.sh.Effects.TileEffects;

import android.content.Context;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Tile105 extends BaseTile {
    private final LottieAnimationView animationView;
    private final Context context;
    private final LinearLayout linearLayout;



    public Tile105(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;


    }

    public void init() {
        init(R.raw.tile105);
        addTextDelegateField("SUBSCRIBE", "**");
        addTextDelegateField("your twitter", "**");

        addTextDelegateField("SUBSCRIBE 2", "**");
        addTextDelegateField("your facebook", "**");


        //changeFont("THARWATEMARARUQAA.ttf");

       // changePathColor(Color.parseColor("#327897"), "**");
        _load();
    }

}
