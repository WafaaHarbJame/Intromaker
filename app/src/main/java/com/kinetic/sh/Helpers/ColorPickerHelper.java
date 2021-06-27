package com.kinetic.sh.Helpers;

import android.app.Activity;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.jaredrummler.android.colorpicker.ColorPickerView;
import com.kinetic.sh.Adapters.AdapterColorHistory;
import com.kinetic.sh.Models.AccentColor;
import com.kinetic.sh.Models.MainApplication;
import com.kinetic.sh.R;

public class ColorPickerHelper {
    private static final String TAG = "ColorPickerHelper";
    private final Activity activity;
    private AdapterColorHistory adapterColorHistory;

    public BottomSheetBehavior bsb;

    public ColorPickerListener colorPickerListener;

    public ColorPickerView colorPickerView;
    private LinearLayout container;
    private MainApplication global;
    private LinearLayout header;

    public EditText hexET;
    private TextView hexPlaceHolder;
    private ImageButton hexValidBtn;

    public AccentColor pickedColor;
    private int previousColor = 0xff000000;
    private RecyclerView rvColorHistory;

    public interface ColorPickerListener {
        void onColorChanged(int i);
    }

    public ColorPickerHelper(Activity activity2) {
        this.activity = activity2;
        init();
    }

    private void init() {
        this.colorPickerView = this.activity.findViewById(R.id.cpv);
        this.container = this.activity.findViewById(R.id.cpv_container);
        this.header = this.activity.findViewById(R.id.cpv_header);
        this.rvColorHistory = this.activity.findViewById(R.id.cpv_rv);
        this.hexET = this.activity.findViewById(R.id.cpv_hex_edit_text);
        this.hexPlaceHolder = this.activity.findViewById(R.id.cpv_hex_place_holder);
        this.hexValidBtn = this.activity.findViewById(R.id.cpv_hex_valid_btn);
        this.hexET.setFilters(new InputFilter[]{new InputFilter.LengthFilter(7), new InputFilter.AllCaps()});
        this.bsb = BottomSheetBehavior.from(this.container);
        MainApplication mainApplication = (MainApplication) this.activity.getApplication();
        this.global = mainApplication;
        this.adapterColorHistory = new AdapterColorHistory(mainApplication.getColorHistory());
        this.rvColorHistory.setLayoutManager(new LinearLayoutManager(this.activity, 0, true));
        this.rvColorHistory.setAdapter(this.adapterColorHistory);
        this.adapterColorHistory.setColorHistoryListener(new AdapterColorHistory.ColorHistoryListener() {
            public void OnClick(AccentColor accentColor) {
                if (ColorPickerHelper.this.colorPickerListener != null) {
                    ColorPickerHelper.this.colorPickerView.setColor(accentColor.getColor());
                    ColorPickerHelper.this.colorPickerListener.onColorChanged(accentColor.getColor());
                }
            }
        });
        setListeners();
    }

    private void setListeners() {
        this.colorPickerView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.i("TAG_ggg", "onTouch: " + motionEvent.getX());
                ColorPickerHelper.this.bsb.setHideable(false);
                return false;
            }
        });
        this.header.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ColorPickerHelper.this.bsb.setHideable(true);
                return false;
            }
        });
        this.colorPickerView.setOnColorChangedListener(new ColorPickerView.OnColorChangedListener() {
            public void onColorChanged(int i) {
                if (ColorPickerHelper.this.colorPickerListener != null) {
                    ColorPickerHelper.this.pickedColor = new AccentColor(i);
                    ColorPickerHelper.this.colorPickerListener.onColorChanged(i);
                }
            }
        });
        this.hexET.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                ColorPickerHelper.this.setHex(String.valueOf(charSequence));
            }
        });
        this.hexValidBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ColorPickerHelper.this.doneHexET();
            }
        });
        this.hexET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i != 66) {
                    return false;
                }
                ColorPickerHelper.this.hexET.clearFocus();
                return false;
            }
        });
        this.bsb.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            public void onSlide(View view, float f) {
            }

            public void onStateChanged(View view, int i) {
                if (i == 5 && ColorPickerHelper.this.pickedColor != null) {
                    ColorPickerHelper colorPickerHelper = ColorPickerHelper.this;
                    colorPickerHelper.setPreviousColor(colorPickerHelper.pickedColor.getColor());
                }
            }
        });
    }


    public void doneHexET() {
        this.hexET.onEditorAction(66);
        this.hexET.clearFocus();
    }


    public void setHex(String str) {
        StringBuilder sb = new StringBuilder(str);
        if (sb.length() > 0) {
            if (String.valueOf(sb.charAt(0)).equals("#")) {
                this.hexPlaceHolder.setVisibility(View.GONE);
            } else {
                str = "#" + str;
                this.hexPlaceHolder.setVisibility(View.VISIBLE);
            }
            try {
                int parseColor = Color.parseColor(str);
                if (this.colorPickerListener != null) {
                    this.colorPickerListener.onColorChanged(parseColor);
                    this.colorPickerView.setColor(parseColor);
                    setPreviousColor(parseColor);
                }
                this.hexValidBtn.setVisibility(View.VISIBLE);
            } catch (IllegalArgumentException unused) {
                this.hexValidBtn.setVisibility(View.GONE);
            }
        } else {
            this.hexPlaceHolder.setVisibility(View.VISIBLE);
            this.hexValidBtn.setVisibility(View.GONE);
        }
    }

    public void setVisibility(boolean z) {
        if (z) {
            this.bsb.setState(3);
            if (this.adapterColorHistory.getItemCount() > 0) {
                this.rvColorHistory.scrollToPosition(this.adapterColorHistory.getItemCount() - 1);
                return;
            }
            return;
        }
        this.bsb.setState(5);
    }

    public void setPreviousColor(int i) {
        this.previousColor = i;
        this.colorPickerView.setColor(i);
        this.global.setNewColorInHistory(new AccentColor(i));
        this.adapterColorHistory.setColors(this.global.getColorHistory());
        this.adapterColorHistory.notifyDataSetChanged();
    }

    public void setColorPickerListener(ColorPickerListener colorPickerListener2) {
        this.colorPickerListener = colorPickerListener2;
    }
}
