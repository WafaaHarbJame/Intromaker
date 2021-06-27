package com.kinetic.sh.Effects.TileEffects;

import android.content.Context;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.core.internal.view.SupportMenu;

import com.airbnb.lottie.LottieAnimationView;
import com.kinetic.sh.R;

import java.util.ArrayList;
import java.util.List;

public class Tile5 extends BaseTile {
    private LottieAnimationView animationView;
    private Context context;
    private List<EditText> editTexts = new ArrayList();
    private LinearLayout linearLayout;

    public Tile5(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;
    }

    public void init() {
        init(R.raw.tile5);
        addTextDelegateField("TRANSPARENCY", "TRANSPARENCY 2");
        addTextDelegateField("background", "background 2");
        changePathColor(SupportMenu.CATEGORY_MASK, "**");
        changeFont("Montserrat-Black.ttf");
        _load();
    }
}
