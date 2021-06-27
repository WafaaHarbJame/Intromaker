package com.kinetic.sh.Effects.TileEffects;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.TextDelegate;
import com.kinetic.sh.R;

public class Tile4 extends BaseTile {
    private final LottieAnimationView animationView;
    private final Context context;
    private final LinearLayout linearLayout;

    public Tile4(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;
    }

    public void init() {
        init(R.raw.tile4);
        new TextDelegate(this.animationView);
        addTextDelegateField("THANK YOU");
        changePathColor(Color.parseColor("#FF00FF00"), "pur", "**");
        changePathColor(-1, "whi", "**");
        changeFont("Montserrat-Black.ttf");
        _load();
    }
}
