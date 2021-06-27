package com.kinetic.sh.Effects.TileEffects;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Tile57 extends BaseTile {
    private final LottieAnimationView animationView;
    private final Context context;
    private final LinearLayout linearLayout;



    public Tile57(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;


    }

    public void init() {
        init(R.raw.tile57);
        addTextDelegateField("economy", "**");
        addTextDelegateField("News Text Titles Version sharq", "**");
        addTextDelegateField("BIG NEWS", "**");
        //changeFont("comforta_regular.ttf");

        changePathColor(-1, "09_Text Titles Comp","Shape Layer 1", "**");
        changePathColor(-1, "09_Text Titles Comp","03_Text Elements","Medium Red Solid 1", "**");
        changePathColor(Color.parseColor("#FF00FF00"), "**");
        _load();
    }

}
