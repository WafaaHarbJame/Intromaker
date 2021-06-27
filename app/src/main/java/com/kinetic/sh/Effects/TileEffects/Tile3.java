package com.kinetic.sh.Effects.TileEffects;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.core.internal.view.SupportMenu;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

public class Tile3 extends BaseTile {
    private LottieAnimationView animationView;
    private Context context;
    private LinearLayout linearLayout;

    public Tile3(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;
    }

    public void init() {
        init(R.raw.tile3);
        addTextDelegateField("T");
        addTextDelegateField("TRENDY");
        addTextDelegateField("TYPOGRAPHY");
        changePathColor(SupportMenu.CATEGORY_MASK, "**");
        changeFont("Montserrat-Black.ttf");
        _load();
    }

    public void addTextDelegateField(String str) {
        super.addTextDelegateField(str);
        this.animationView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }
}
