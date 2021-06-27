package com.kinetic.sh.Effects.TileEffects;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Tile12 extends BaseTile {
    private final LottieAnimationView animationView;
    private final Context context;
    private final LinearLayout linearLayout;

    public Tile12(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;
    }

    public void init() {
        init(R.raw.tile12);
        this.animationView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        addTextDelegateField("TRENDY DESIGN");
        changePathColor(-1, "**");
        changePathColor(Color.parseColor("#FF00FF00"), "line", "**");
        changeFont("Montserrat-Black.ttf");
        _load();
    }
}
