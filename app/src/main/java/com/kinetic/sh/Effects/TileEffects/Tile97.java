package com.kinetic.sh.Effects.TileEffects;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Tile97 extends BaseTile {
    private final LottieAnimationView animationView;
    private final Context context;
    private final LinearLayout linearLayout;



    public Tile97(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;


    }

    public void init() {
        init(R.raw.tile97);
        addTextDelegateField("HAVE THE TIME", "**");
        addTextDelegateField("OF YOUR LIFE", "**");

        //changeFont("THARWATEMARARUQAA.ttf");
        changePathColor(Color.parseColor("#329791"), "Comp 1", "Scene 21","trimShape 03", "**");
        changePathColor(Color.parseColor("#02FFA9"),  "Comp 1","Scene 21","trimShape 04", "**");

        _load();
    }

}
