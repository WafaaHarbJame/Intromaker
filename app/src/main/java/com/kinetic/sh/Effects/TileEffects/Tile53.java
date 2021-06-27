package com.kinetic.sh.Effects.TileEffects;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Tile53 extends BaseTile {
    private final LottieAnimationView animationView;
    private final Context context;
    private final LinearLayout linearLayout;



    public Tile53(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;


    }

    public void init() {
        init(R.raw.tile53);
        addTextDelegateField("New Trend Dynamic Text Animation", "**");
        addTextDelegateField("DYNAMIC TEXT ANIMATION", "**");
        addTextDelegateField("Text Animation", "**");
        //changeFont("roboto_regular.ttf");
        changePathColor(-1, "04_Text Titles Comp","Shape Layer 3", "**");
        changePathColor(-1, "04_Text Titles Comp","Shape Layer 4", "**");
        changePathColor(-1, "04_Text Titles Comp","Medium Red Solid 1", "**");
        changePathColor(Color.parseColor("#FF00FF00"), "**");
        _load();
    }

}
