package com.kinetic.sh.Effects;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.FontAssetDelegate;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.LottieOnCompositionLoadedListener;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.SimpleColorFilter;
import com.airbnb.lottie.TextDelegate;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieValueCallback;
import com.airbnb.lottie.value.ScaleXY;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.card.MaterialCardView;
import com.kinetic.sh.Adapters.AdapterColors;
import com.kinetic.sh.Adapters.AdapterTypefaces;
import com.kinetic.sh.Helpers.AddEditText;
import com.kinetic.sh.Helpers.ColorPickerHelper;
import com.kinetic.sh.Intefaces.TextEffectInterface;
import com.kinetic.sh.Models.AccentColor;
import com.kinetic.sh.Models.MainApplication;
import com.kinetic.sh.Models.MetaEditText;
import com.kinetic.sh.R;
import com.kinetic.sh.Template.Template;
import com.kinetic.sh.Template.TemplateUtils.EffectElementColor;
import com.kinetic.sh.Ui.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tile extends TileUtils implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    private static final String TAG = "Tile";
    private float _scale;
    private float _x;
    private float _y;
    private List<AddEditText> addEditTexts;
    public float animHeight = 2500.0f;
    public float animWidth = 1282.5f;
    public LottieAnimationView animationView;
    private LinearLayout bottomSheetTypeface;
    private BottomSheetBehavior bsbTypeface;
    private List<AccentColor> colors;
    public Context context;
    private List<EditText> editTexts = new ArrayList<>();
    private FrameLayout frameLayout;
    private MainApplication global;
    public Boolean isCalled = false;
    private final LinearLayout linearLayout;
    private int raw;
    private RecyclerView rvColors;
    private RecyclerView rvTypeface;
    public AppCompatSeekBar scaleSeekBar;
    private TapTargetSequence tapTargetSequence;
    private Template template;
    public TextDelegate textDelegate;
    public TextEffectInterface textEffectInterface;
    private LinearLayout typefaceLayout;
    private List<String> typefaces;
    private Float xFrame;
    public AppCompatSeekBar xPositionSeekBar;
    private Float yFrame;
    public AppCompatSeekBar yPositionSeekBar;

    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    public void selectSpecificLayer(String... strArr) {
    }

    public Tile(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;
    }

    public void setTextEffectInterface(TextEffectInterface textEffectInterface2) {
        this.isCalled = true;
        this.textEffectInterface = textEffectInterface2;
    }

    public void init(int i, String str) {
        changeFont(str);
        init(i);
    }

    public void init(int i) {
        this.raw = i;
        this.animationView.setAnimation(i);
        this.scaleSeekBar = ((AppCompatActivity) this.context).findViewById(R.id.scaleSeekBar);
        this.xPositionSeekBar = ((AppCompatActivity) this.context).findViewById(R.id.positionXSeekBar);
        this.yPositionSeekBar = ((AppCompatActivity) this.context).findViewById(R.id.positionYSeekBar);
        LinearLayout linearLayout2 = ((AppCompatActivity) this.context).findViewById(R.id.bottom_sheet_typeface);
        this.bottomSheetTypeface = linearLayout2;
        this.bsbTypeface = BottomSheetBehavior.from(linearLayout2);
        this.typefaceLayout = ((AppCompatActivity) this.context).findViewById(R.id.m_typeface);
        this.rvTypeface = ((AppCompatActivity) this.context).findViewById(R.id.bs_typeface_rv);
        this.frameLayout = ((AppCompatActivity) this.context).findViewById(R.id.frameLayout2);
        getAnimationSize(i);
        this.colors = new ArrayList<>();
        MainApplication mainApplication = ((MainActivity) this.context).global;
        this.global = mainApplication;
        Template template2 = mainApplication.getTemplate();
        this.template = template2;
        template2.setEffectName(this.context.getResources().getResourceName(i));
        this.animationView.playAnimation();
        this.textDelegate = new TextDelegate(this.animationView);
        this.addEditTexts = new ArrayList<>();
        this.tapTargetSequence = new TapTargetSequence((Activity) this.context);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) this.context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.global.getTemplate().setDPI(displayMetrics.densityDpi);
        this.animationView.addAnimatorListener(new Animator.AnimatorListener() {
            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
                if (Tile.this.isCalled) {
                    Tile.this.textEffectInterface.doneAnimation();
                }
            }
        });
        this.scaleSeekBar.setOnSeekBarChangeListener(this);
        this.xPositionSeekBar.setOnSeekBarChangeListener(this);
        this.typefaceLayout.setOnClickListener(this);
        this.yPositionSeekBar.setOnSeekBarChangeListener(this);
    }

    public KeyPath findKeyPath(String... strArr) {
        return new KeyPath(strArr);
    }

    public void addTextDelegateField(String str, Boolean bool, String... strArr) {
        AddEditText addEditText = new AddEditText(this.context, this.linearLayout, str, this.animationView);
        addEditText.setKeyPath(strArr);
        addEditText.setMasked(bool);
        setEditText(addEditText, str);
    }

    public void addTextDelegateField(String str, String... strArr) {
        AddEditText addEditText = new AddEditText(this.context, this.linearLayout, str, this.animationView);
        addEditText.setKeyPath(strArr);
        setEditText(addEditText, str);
    }

    public void addTextDelegateField(String str) {
        setEditText(new AddEditText(this.context, this.linearLayout, str, this.animationView), str);
    }

    private void setEditText(AddEditText addEditText, final String str) {
        this.addEditTexts.add(addEditText);
        _saveEditText(this.context, str, null);
        EditText addText = addEditText.addText();
        this.animationView.setTextDelegate(this.textDelegate);
        addText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                String valueOf = String.valueOf(editable);
                Tile tile = Tile.this;
                tile._saveTextValue(tile.context, str, valueOf);
                if (Tile.this.isRTL(valueOf)) {
                    Tile.this.textDelegate.setText(str, new StringBuilder(valueOf).reverse().toString());
                    Tile.this.animationView.setTextDelegate(Tile.this.textDelegate);
                    return;
                }
                Tile.this.textDelegate.setText(str, valueOf);
                Tile.this.animationView.setTextDelegate(Tile.this.textDelegate);
            }
        });
        this.editTexts.add(addText);
    }

    public void changePathColor(int i, String... strArr) {
        _saveEffectElementColor(this.context, strArr);
        this.animationView.addValueCallback(new KeyPath(strArr), LottieProperty.COLOR_FILTER, new LottieValueCallback(new SimpleColorFilter(i)));
        this.colors.add(new AccentColor(i, strArr));
        setAccentColors(this.colors);
    }


    public void changePathColorLocal(int i, String... strArr) {
        _saveEffectElementColorValue(this.context, i, strArr);
        this.animationView.addValueCallback(new KeyPath(strArr), LottieProperty.COLOR_FILTER, new LottieValueCallback(new SimpleColorFilter(i)));
        setAccentColors(this.colors);
    }

    public void logAllKeyPaths() {
        this.animationView.addLottieOnCompositionLoadedListener(new LottieOnCompositionLoadedListener() {
            public void onCompositionLoaded(LottieComposition lottieComposition) {
                for (KeyPath keyPath : Tile.this.animationView.resolveKeyPath(new KeyPath("**"))) {
                    Log.i(Tile.TAG, "logAllKeyPaths: " + keyPath);
                }
            }
        });
    }

    public void setAccentColors(List<AccentColor> list) {
        RecyclerView recyclerView = ((AppCompatActivity) this.context).findViewById(R.id.rvAccentColors);
        this.rvColors = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this.context, 0, false));
        AdapterColors adapterColors = new AdapterColors(list);
        this.rvColors.setAdapter(adapterColors);
        adapterColors.setColorAccentInterface(new AdapterColors.ColorAccentInterface() {
            public void colorPicked(final AccentColor accentColor, final MaterialCardView materialCardView) {
                ColorPickerHelper colorPickerHelper = new ColorPickerHelper((Activity) Tile.this.context);
                colorPickerHelper.setVisibility(true);
                colorPickerHelper.setPreviousColor(accentColor.getColor());
                colorPickerHelper.setColorPickerListener(new ColorPickerHelper.ColorPickerListener() {
                    public void onColorChanged(int i) {
                        accentColor.setColor(i);
                        Tile.this.changePathColorLocal(i, accentColor.getPathname());
                        ViewCompat.setBackgroundTintList(materialCardView, ColorStateList.valueOf(i));
                    }
                });
            }
        });
    }

    public boolean deleteDir(File file) {
        if (file != null && file.isDirectory()) {
            String[] list = file.list();
            for (String file2 : list) {
                if (!deleteDir(new File(file, file2))) {
                    return false;
                }
            }
            return file.delete();
        } else if (file == null || !file.isFile()) {
            return false;
        } else {
            return file.delete();
        }
    }

    private void setScale(float f) {
        this._scale = f;
        _savePosition(this.context, f, this._x, this._y);
        try {
            this.animationView.addValueCallback(this.animationView.resolveKeyPath(new KeyPath("**")).get(0), LottieProperty.TRANSFORM_SCALE, new LottieValueCallback(new ScaleXY(f, f)));
        } catch (IndexOutOfBoundsException unused) {
            Toast.makeText(this.context, "Something went wrong while setting scale", Toast.LENGTH_LONG).show();
        }
    }

    private void setPositionX(float f) {
        List<KeyPath> resolveKeyPath = this.animationView.resolveKeyPath(new KeyPath("**"));
        this.xFrame = f;
        Float f2 = this.yFrame;
        float floatValue = f2 != null ? f2 : this.animHeight;
        this._x = f;
        this._y = floatValue;
        try {
            this.animationView.addValueCallback(resolveKeyPath.get(0), LottieProperty.TRANSFORM_POSITION, new LottieValueCallback(new PointF(f, floatValue)));
            _savePosition(this.context, this._scale, this._x, this._y);
        } catch (IndexOutOfBoundsException unused) {
            Toast.makeText(this.context, "Something went wrong while setting Y axis", Toast.LENGTH_LONG).show();
        }
    }

    private void setPositionY(float f) {
        List<KeyPath> resolveKeyPath = this.animationView.resolveKeyPath(new KeyPath("**"));
        this.yFrame = f;
        Float f2 = this.xFrame;
        float floatValue = f2 != null ? f2 : this.animWidth;
        this._y = f;
        this._x = floatValue;
        try {
            this.animationView.addValueCallback(resolveKeyPath.get(0), LottieProperty.TRANSFORM_POSITION, new LottieValueCallback(new PointF(floatValue, f)));
            _savePosition(this.context, this._scale, this._x, this._y);
        } catch (IndexOutOfBoundsException unused) {
            Toast.makeText(this.context, "Something went wrong while setting X axis", Toast.LENGTH_LONG).show();
        }
    }

    public void setEditTexts(List<EditText> list) {
        this.editTexts = list;
    }

    public List<EditText> getEditTexts() {
        return this.editTexts;
    }

    public LottieComposition getComposition() {
        return this.animationView.getComposition();
    }

    private void getAnimationSize(int i) {
        InputStream openRawResource = this.context.getResources().openRawResource(i);
        StringWriter stringWriter = new StringWriter();
        char[] cArr = new char[1024];
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(openRawResource, StandardCharsets.UTF_8));
            while (true) {
                int read = bufferedReader.read(cArr);
                if (read != -1) {
                    stringWriter.write(cArr, 0, read);
                } else {
                    break;
                }
            }
            openRawResource.close();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                openRawResource.close();
            } catch (IOException e4) {
                e4.printStackTrace();
            }
        }
        try {
            JSONObject jSONObject = new JSONObject(stringWriter.toString());
            String string = jSONObject.getString("w");
            this.animHeight = (float) Integer.parseInt(jSONObject.getString("h"));
            this.animWidth = (float) Integer.parseInt(string);
        } catch (JSONException e5) {
            e5.printStackTrace();
        }
    }

    private void setTypeface(final int i) {
        this.typefaces = new ArrayList<>();
        try {
            this.typefaces.addAll(Arrays.asList(this.context.getAssets().list("fonts")));
            AdapterTypefaces adapterTypefaces = new AdapterTypefaces(this.typefaces);
            this.rvTypeface.setLayoutManager(new LinearLayoutManager(this.context));
            this.rvTypeface.setAdapter(adapterTypefaces);
            this.bsbTypeface.setState(3);
            adapterTypefaces.setAdapterTypefacesInter(new AdapterTypefaces.AdapterTypefacesInter() {
                public void onClicked(final String str) {
                    Tile.this.animationView.setCacheComposition(false);
                    Tile.this.animationView.setAnimation(R.raw.tile2);
                    Tile.this.animationView.setAnimation(R.raw.tile3);
                    Tile.this.animationView.setAnimation(R.raw.tile4);
                    Tile.this.animationView.setAnimation(i);
                    Tile.this.animationView.setFontAssetDelegate(new FontAssetDelegate() {
                        public Typeface fetchFont(String str) {
                            AssetManager assets = Tile.this.context.getAssets();
                            return Typeface.createFromAsset(assets, "fonts/" + str);
                        }
                    });
                    Log.i(Tile.TAG, "onClicked: " + str);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(TAG, "getFonts: error");
        }
    }

    public void changeFont(final String str) {
        Log.i(TAG, "changeFont: " + str);
        LottieCompositionFactory.clearCache(this.context);
        this.animationView.setFontAssetDelegate(new FontAssetDelegate() {
            public Typeface fetchFont(String _str) {
                Log.i(Tile.TAG, "fetchFont: inn");
                AssetManager assets = Tile.this.context.getAssets();
                return Typeface.createFromAsset(assets, "fonts/" + str);
            }

            public String getFontPath(String _str) {
                Toast.makeText(Tile.this.context, str, Toast.LENGTH_LONG).show();
                return super.getFontPath("fonts/" + str);
            }
        });
    }

    public void _load() {
        final Template template2 = ((MainActivity) this.context).global.getcTemplate();
        Log.i(TAG, "_load: " + template2);
        if (template2 != null && this.context.getResources().getResourceName(this.raw).equals(template2.getEffectName())) {
            for (int i = 0; i < template2.getMetaEditTexts().size(); i++) {
                MetaEditText metaEditText = template2.getMetaEditTexts().get(i);
                if (!(metaEditText == null || metaEditText.getValue() == null)) {
                    this.editTexts.get(i).setText(metaEditText.getValue());
                }
                AddEditText addEditText = this.addEditTexts.get(i);
                if (!(metaEditText == null || metaEditText.getFontColor() == 0)) {
                    addEditText.setTextColor(metaEditText.getFontColor());
                }
                if (metaEditText.getVisibility()) {
                    addEditText.setVisibility(metaEditText.getVisibility());
                }
            }
            this.animationView.addLottieOnCompositionLoadedListener(new LottieOnCompositionLoadedListener() {
                public void onCompositionLoaded(LottieComposition lottieComposition) {
                    if (template2.getEffectPosition() != null) {
                        float scale = template2.getEffectPosition().getScale();
                        if (template2.getEffectPosition().getScale() != 0.0f) {
                            Tile.this.scaleSeekBar.setMax(200);
                            Tile.this.scaleSeekBar.setProgress((int) (scale * 100.0f));
                        }
                        if (template2.getEffectPosition().getX() != 0.0f) {
                            Log.i(Tile.TAG, "onCompositionLoaded:  xLoaded" + template2.getEffectPosition().getX());
                            Tile.this.xPositionSeekBar.setMax(((int) Tile.this.animWidth) * 2);
                            Tile.this.xPositionSeekBar.setProgress((int) Tile.this.fixPointF(template2.getEffectPosition().getX()));
                        }
                        if (template2.getEffectPosition().getY() != 0.0f) {
                            Log.i(Tile.TAG, "onCompositionLoaded:  yLoaded" + template2.getEffectPosition().getY());
                            Tile.this.yPositionSeekBar.setMax(((int) Tile.this.animHeight) * 2);
                            Tile.this.yPositionSeekBar.setProgress((int) Tile.this.fixPointF(template2.getEffectPosition().getY()));
                        }
                    }
                }
            });
            if (template2.getEffectElementColors() != null) {
                for (int i2 = 0; i2 < template2.getEffectElementColors().size(); i2++) {
                    EffectElementColor effectElementColor = template2.getEffectElementColors().get(i2);
                    if (effectElementColor.getValue() != 0) {
                        this.colors.get(i2).setColor(effectElementColor.getValue());
                        changePathColorLocal(effectElementColor.getValue(), effectElementColor.getPathNames());
                        Log.i(TAG, "_load: " + Arrays.toString(effectElementColor.getPathNames()));
                    }
                }
            }
        }
    }


    public float fixPointF(float f) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) this.context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return (float) ((int) (f * (((float) displayMetrics.densityDpi) / (this.global.getcTemplate().getDPI() != 0 ? (float) this.global.getcTemplate().getDPI() : 380.0f))));
    }

    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        switch (seekBar.getId()) {
            case R.id.positionXSeekBar:
                Log.i(TAG, "onProgressChanged: " + i);
                setPositionX((float) i);
                return;
            case R.id.positionYSeekBar:
                Log.i(TAG, "onProgressChanged: " + i);
                setPositionY((float) i);
                return;
            case R.id.scaleSeekBar:
                Log.i(TAG, "onProgressChanged: " + i);
                setScale(((float) i) / 100.0f);
                return;
            default:
        }
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
        switch (seekBar.getId()) {
            case R.id.positionXSeekBar:
                seekBar.setMax(((int) this.animWidth) * 2);
                return;
            case R.id.positionYSeekBar:
                seekBar.setMax(((int) this.animHeight) * 2);
                return;
            case R.id.scaleSeekBar:
                seekBar.setMax(200);
                seekBar.setProgress(100);
                return;
            default:
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.m_typeface) {
            setTypeface(this.raw);
        }
    }
}
