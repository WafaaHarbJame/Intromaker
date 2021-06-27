package com.kinetic.sh.Effects.TileEffects;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Tile60 extends BaseTile {
    private final LottieAnimationView animationView;
    private final Context context;
    private final LinearLayout linearLayout;



    public Tile60(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;


    }

    public void init() {
        init(R.raw.tile60);
        addTextDelegateField("Unique topic entries", "**");
        addTextDelegateField("NEWDDDD", "**");
        //changeFont("comforta_regular.ttf");
        changePathColor(Color.parseColor("#FF00FF00"), "**");
        changePathColor(-1, "08_Text Titles Comp","Dark Blue Solid 1", "**");
        changePathColor(-1, "08_Text Titles Comp","Medium Red Solid 1", "**");
        changePathColor(-1, "08_Text Titles Comp","Pale Lime Green Solid 1", "**");
        _load();
    }

}
