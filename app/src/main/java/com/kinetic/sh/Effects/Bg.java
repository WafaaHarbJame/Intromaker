package com.kinetic.sh.Effects;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieOnCompositionLoadedListener;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.SimpleColorFilter;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieValueCallback;
import com.google.android.material.card.MaterialCardView;
import com.kinetic.sh.Adapters.AdapterDynamicBgColor;
import com.kinetic.sh.Helpers.ColorPickerHelper;
import com.kinetic.sh.Models.AccentColor;
import com.kinetic.sh.R;
import com.kinetic.sh.Template.Template;
import com.kinetic.sh.Template.TemplateUtils.BgElementColor;
import com.kinetic.sh.Ui.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bg extends BgUtils implements SeekBar.OnSeekBarChangeListener {
    private static final String TAG = "Bg";
    private List<AccentColor> accentColors;
    private AdapterDynamicBgColor adapterDynamicBgColor;

    public LottieAnimationView animationView;

    public Context context;
    private FrameLayout frameLayout;
    private int raw;
    private RecyclerView rvDynamicColorPicker;
    private SeekBar transparencySeekBar;

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    public Bg(Context context2, LottieAnimationView lottieAnimationView) {
        this.context = context2;
        this.animationView = lottieAnimationView;
    }

    public void init(int i) {
        this.raw = i;
        this.transparencySeekBar = ((AppCompatActivity) this.context).findViewById(R.id.transparency_seekbar);
        this.frameLayout = ((AppCompatActivity) this.context).findViewById(R.id.frameLayout2);
        this.rvDynamicColorPicker = ((AppCompatActivity) this.context).findViewById(R.id.rv_dynamic_color_property);
        this.animationView.setAnimation(i);
        this.animationView.playAnimation();
        this.transparencySeekBar.setOnSeekBarChangeListener(this);
        this.accentColors = new ArrayList<>();
        _setBgType(this.context, 1);
        Context context2 = this.context;
        _setDynamic(context2, context2.getResources().getResourceName(i));
        Log.i(TAG, "init: " + this.context.getResources().getResourceName(i));
    }

    private void setTransparency(float f) {
        this.frameLayout.setBackgroundColor(0xff000000);
        this.animationView.setAlpha(f);
    }

    private void logAllKeyPaths() {
        this.animationView.addLottieOnCompositionLoadedListener(new LottieOnCompositionLoadedListener() {
            public void onCompositionLoaded(LottieComposition lottieComposition) {
                for (KeyPath keyPath : Bg.this.animationView.resolveKeyPath(new KeyPath("**"))) {
                    Log.i(Bg.TAG, "logAllKeyPaths: " + keyPath);
                }
            }
        });
    }

    public void changePathColor(int i, String... strArr) {
        _addDynamicElementBackgroundColor(this.context, i, strArr);
        this.animationView.addValueCallback(new KeyPath(strArr), LottieProperty.COLOR_FILTER, new LottieValueCallback(new SimpleColorFilter(i)));
        this.accentColors.add(new AccentColor(i, strArr));
        setUpColorPicker();
    }

    public void changePathColorLocal(int i, String... strArr) {
        _addDynamicElementBackgroundColorValue(this.context, i, strArr);
        this.animationView.addValueCallback(new KeyPath(strArr), LottieProperty.COLOR_FILTER, new LottieValueCallback(new SimpleColorFilter(i)));
        setUpColorPicker();
    }

    public int randomColor() {
        Random random = new Random();
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    public void changeMultiPathColorAtDifferentIntensity(int i, float[] fArr, Boolean bool, String[]... strArr) {
        AccentColor accentColor = new AccentColor(i, (String[]) null);
        accentColor.setIntensity(fArr);
        accentColor.setMultiPathsNames(strArr);
        if (!bool) {
            this.accentColors.add(accentColor);
            _addMultiPathColorAtDifferentIntensity(this.context, i, fArr, strArr);
        } else {
            _addMultiPathColorAtDifferentIntensityValue(this.context, i, strArr);
        }
        for (int i2 = 0; i2 < strArr.length; i2++) {
            float f = fArr[i2];
            String[] strArr2 = strArr[i2];
            this.animationView.addValueCallback(new KeyPath(strArr2), LottieProperty.COLOR_FILTER, new LottieValueCallback(new SimpleColorFilter(darker(i, f))));
        }
        setUpColorPicker();
    }

    public void setUpColorPicker() {
        this.adapterDynamicBgColor = new AdapterDynamicBgColor(this.accentColors);
        this.rvDynamicColorPicker.setLayoutManager(new LinearLayoutManager(this.context, 0, false));
        this.rvDynamicColorPicker.setAdapter(this.adapterDynamicBgColor);
        this.adapterDynamicBgColor.setDynamicBgListener(new AdapterDynamicBgColor.DynamicBgListener() {
            public void colorPicked(final AccentColor accentColor, final MaterialCardView materialCardView) {
                ColorPickerHelper colorPickerHelper = new ColorPickerHelper((Activity) Bg.this.context);
                colorPickerHelper.setVisibility(true);
                colorPickerHelper.setPreviousColor(accentColor.getColor());
                colorPickerHelper.setColorPickerListener(new ColorPickerHelper.ColorPickerListener() {
                    public void onColorChanged(int i) {
                        if (accentColor.getPathname() != null) {
                            accentColor.setColor(i);
                            Bg.this.changePathColorLocal(i, accentColor.getPathname());
                            ViewCompat.setBackgroundTintList(materialCardView, ColorStateList.valueOf(i));
                            return;
                        }
                        accentColor.setColor(i);
                        Bg.this.changeMultiPathColorAtDifferentIntensity(i, accentColor.getIntensity(), true, accentColor.getMultiPathsNames());
                        ViewCompat.setBackgroundTintList(materialCardView, ColorStateList.valueOf(i));
                    }
                });
            }
        });
    }

    public void removeColorPickers() {
        this.accentColors.clear();
        setUpColorPicker();
    }

    public static int darker(int i, float f) {
        return Color.argb(Color.alpha(i), Math.max((int) (((float) Color.red(i)) * f), 0), Math.max((int) (((float) Color.green(i)) * f), 0), Math.max((int) (((float) Color.blue(i)) * f), 0));
    }

    public void _load() {
        Template template = ((MainActivity) this.context).global.getcTemplate();
        if (template != null && template.getBgType() == 1) {
            Log.i(TAG, "_load: okay");
            Log.i(TAG, "_load_title: " + template.getDynamicBgMeta().getName());
            if (this.context.getResources().getResourceName(this.raw).equals(template.getDynamicBgMeta().getName())) {
                List<BgElementColor> bgElementColors = template.getDynamicBgMeta().getBgElementColors();
                Log.i(TAG, "_load: " + bgElementColors.size());
                for (int i = 0; i < bgElementColors.size(); i++) {
                    BgElementColor bgElementColor = bgElementColors.get(i);
                    if (bgElementColor.getColor() != 0) {
                        this.accentColors.get(i).setColor(bgElementColor.getColor());
                        if (bgElementColor.getIntensity() == null || bgElementColor.getIntensity().length == 0) {
                            changePathColorLocal(bgElementColor.getColor(), bgElementColor.getPath());
                        } else {
                            changeMultiPathColorAtDifferentIntensity(bgElementColor.getColor(), bgElementColor.getIntensity(), true, bgElementColor.getMultiPath());
                        }
                    }
                }
                float transperency = template.getDynamicBgMeta().getTransperency();
                Log.i(TAG, "_load: " + transperency);
                this.transparencySeekBar.setProgress((int) transperency);
            }
        }
    }

    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        if (seekBar.getId() == R.id.transparency_seekbar) {
            float f = (float) i;
            setTransparency(Math.abs((f / 100.0f) - 1.0f));
            _setTransperency(this.context, f);
        }
    }
}
