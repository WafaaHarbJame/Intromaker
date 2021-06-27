package com.kinetic.sh.Effects.TileEffects;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Tile63 extends BaseTile {
    private final LottieAnimationView animationView;
    private final Context context;
    private final LinearLayout linearLayout;



    public Tile63(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;


    }

    public void init() {
        init(R.raw.tile63);
        addTextDelegateField("CLEAN DESIGN", "**");
        addTextDelegateField("DIGIPHIC STUDIO TITLES ANIMATION PACK", "**");
        addTextDelegateField("Titles Animation Pack Pres", "**");
        addTextDelegateField("Available Exclusively On Envato Marketplace", "**");


        //changeFont("comforta_regular.ttf");
        changePathColor(Color.parseColor("#FF00FF00"), "**");
        changePathColor(-1, "Title-020","Box-Shape", "**");
        changePathColor(-1, "Title-020","Line-Down", "**");
        _load();
    }

}
