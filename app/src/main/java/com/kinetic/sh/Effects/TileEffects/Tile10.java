package com.kinetic.sh.Effects.TileEffects;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Tile10 extends BaseTile {
    private LottieAnimationView animationView;
    private Context context;
    private LinearLayout linearLayout;

    public Tile10(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;
    }

    public void init() {
        init(R.raw.tile10);
        this.animationView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        addTextDelegateField("YOUR TITLE", "YOUR TITLE");
        addTextDelegateField("YOUR SUBTITLE", "YOUR SUBTITLE");
        changePathColor(-1, "Shape Layer 1", "**");
        changePathColor(Color.parseColor("#FF00FF00"), "Shape Layer 2", "**");
        changeFont("Montserrat-Black.ttf");
        _load();
    }
}
