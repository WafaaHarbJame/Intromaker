package com.kinetic.sh.Effects.TileEffects;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Tile59 extends BaseTile {
    private final LottieAnimationView animationView;
    private final Context context;
    private final LinearLayout linearLayout;



    public Tile59(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;


    }

    public void init() {
        init(R.raw.tile59);
        addTextDelegateField("Unique topic entries", "**");
        addTextDelegateField("You can use many places easily", "**");
        addTextDelegateField("NEWS TITLES PROJECT.", "**");
        //changeFont("comforta_regular.ttf");
        changePathColor(Color.parseColor("#FF00FF00"), "**");
        changePathColor(-1, "05_Text Titles Comp","Shape Layer 1", "**");
        changePathColor(-1, "05_Text Titles Comp","Shape Layer 2", "**");
        changePathColor(-1, "05_Text Titles Comp","01_Text Elements","Medium Red Solid 1", "**");
        _load();
    }

}
