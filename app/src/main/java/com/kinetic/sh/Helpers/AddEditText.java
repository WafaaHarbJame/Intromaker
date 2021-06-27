package com.kinetic.sh.Helpers;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.TransitionDrawable;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieValueCallback;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.kinetic.sh.Effects.TileUtils;
import com.kinetic.sh.R;
import com.kinetic.sh.Template.Template;
import com.kinetic.sh.Ui.MainActivity;

import java.util.Arrays;

public class AddEditText extends TileUtils implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private static final String TAG = "AddEditText";

    public LottieAnimationView animationView;

    public MaterialCardView colorPickerCard;
    private ColorPickerHelper colorPickerHelper;

    public Context context;
    private LinearLayout controllerContainer;
    private String currentText;
    private Button doneBtn;
    private TextInputEditText et;
    private Boolean isControlsVisible = true;
    private Boolean isMasked = false;

    public KeyPath keyPath;
    private final LinearLayout linearLayout;
    private ImageButton moreBtn;
    private LinearLayout parentContainer;
    private String[] path;

    public String placeholder;

    public int previousColor = -1;
    private int rotationAngle = 180;
    private Template template;
    private TransitionDrawable transition;
    private ToggleButton visibilityToggle;

    public AddEditText(Context context2, LinearLayout linearLayout2, String str, LottieAnimationView lottieAnimationView) {
        this.context = context2;
        this.linearLayout = linearLayout2;
        this.placeholder = str;
        this.animationView = lottieAnimationView;
    }

    public void setKeyPath(String[] strArr) {
        Log.i(TAG, "setKeyPath: " + Arrays.toString(strArr));
        this.path = strArr;
        this.keyPath = new KeyPath(strArr);
    }

    public void setMasked(Boolean bool) {
        this.isMasked = bool;
    }

    public Boolean getMasked() {
        return this.isMasked;
    }

    public String[] getPath() {
        return this.path;
    }

    public EditText addText() {
        AppCompatActivity appCompatActivity = (AppCompatActivity) this.context;
        View inflate = ((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.card_text_controler, null);
        this.et = inflate.findViewById(R.id.card_text_control_text);
        this.moreBtn = inflate.findViewById(R.id.card_text_control_more_btn);
        this.controllerContainer = inflate.findViewById(R.id.card_text_control_container);
        this.doneBtn = inflate.findViewById(R.id.card_text_control_done_btn);
        this.colorPickerCard = inflate.findViewById(R.id.textColorCard2);
        this.parentContainer = inflate.findViewById(R.id.card_text_controller_main_wrapper);
        this.visibilityToggle = inflate.findViewById(R.id.text_toggle_visibility);
        init();
        this.et.setSingleLine();
        this.et.setHint(this.placeholder);
        this.et.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        this.et.setImeOptions(6);
        this.linearLayout.addView(inflate);
        return this.et;
    }

    private void setTextColor() {
        int i = this.previousColor;
        if (i != -1) {
            this.colorPickerHelper.setPreviousColor(i);
        }
        this.colorPickerHelper.setColorPickerListener(new ColorPickerHelper.ColorPickerListener() {
            public void onColorChanged(int i) {
                if (AddEditText.this.keyPath != null) {
                    AddEditText.this.animationView.addValueCallback(AddEditText.this.keyPath, LottieProperty.COLOR, new LottieValueCallback(i));
                } else {
                    AddEditText.this.animationView.addValueCallback(new KeyPath(AddEditText.this.placeholder, "**"), LottieProperty.COLOR, new LottieValueCallback(i));
                }
                AddEditText.this.previousColor = i;
                AddEditText addEditText = AddEditText.this;
                addEditText._saveTextColor(addEditText.context, AddEditText.this.placeholder, i);
                AddEditText.this.colorPickerCard.setCardBackgroundColor(i);
            }
        });
    }

    public void setTextColor(int i) {
        Log.i(TAG, "setTextColor: " + i);
        _saveTextColor(this.context, this.placeholder, i);
        KeyPath keyPath2 = this.keyPath;
        if (keyPath2 != null) {
            this.animationView.addValueCallback(keyPath2, LottieProperty.COLOR, new LottieValueCallback(i));
        } else {
            this.animationView.addValueCallback(new KeyPath(this.placeholder, "**"), LottieProperty.COLOR, new LottieValueCallback(i));
        }
        this.previousColor = i;
        this.colorPickerCard.setCardBackgroundColor(i);
    }

    private void init() {
        this.moreBtn.setOnClickListener(this);
        this.doneBtn.setOnClickListener(this);
        this.colorPickerCard.setOnClickListener(this);
        this.visibilityToggle.setOnCheckedChangeListener(this);
        this.transition = (TransitionDrawable) this.parentContainer.getBackground();
        this.template = ((MainActivity) this.context).global.getTemplate();
        toggleExpandIcon();
    }

    private void toggleExpandIcon() {
        this.rotationAngle = this.rotationAngle == 0 ? 180 : 0;
        this.moreBtn.animate().rotation((float) this.rotationAngle).setDuration(300).start();
        if (this.isControlsVisible) {
            this.transition.startTransition(300);
            this.controllerContainer.setVisibility(View.GONE);
            return;
        }
        this.transition.reverseTransition(300);
        this.controllerContainer.setVisibility(View.VISIBLE);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.card_text_control_done_btn:
                this.isControlsVisible = false;
                this.controllerContainer.setVisibility(View.GONE);
                return;
            case R.id.card_text_control_more_btn:
                if (this.isControlsVisible) {
                    this.isControlsVisible = false;
                    toggleExpandIcon();
                    return;
                }
                this.isControlsVisible = true;
                toggleExpandIcon();
                return;
            case R.id.textColorCard2:
                this.colorPickerHelper = new ColorPickerHelper((Activity) this.context);
                if (!this.isMasked) {
                    this.colorPickerHelper.setVisibility(true);
                    setTextColor();
                    return;
                }
                Toast.makeText(this.context, "Text is transparent , Try to change element color", Toast.LENGTH_LONG).show();
                return;
            default:
                break;
        }
    }

    public void setVisibility(Boolean bool) {
        Log.i(TAG, "setVisibility: " + bool);
        this.visibilityToggle.setChecked(bool);
        _saveTextVisibility(this.context, this.placeholder, bool);
        if (this.et.getText() == null) {
            return;
        }
        if (bool) {
            this.currentText = this.et.getText().toString();
            this.et.setText("");
        } else if (this.currentText.equals("")) {
            this.et.setText(this.placeholder);
        } else {
            this.et.setText(this.currentText);
        }
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (compoundButton.getId() == R.id.text_toggle_visibility) {
            setVisibility(z);
        }
    }
}
