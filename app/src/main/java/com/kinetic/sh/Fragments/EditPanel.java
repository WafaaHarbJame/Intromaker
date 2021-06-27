package com.kinetic.sh.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.RenderMode;
import com.github.florent37.expansionpanel.ExpansionLayout;
import com.kinetic.sh.Helpers.TextEffect;
import com.kinetic.sh.Models.CompMeta;
import com.kinetic.sh.R;

import java.io.IOException;
import java.util.Objects;

public class EditPanel extends Fragment {
    private final String TAG = "EditPanel";
    private LottieAnimationView animationView;
    private LottieAnimationView bgAnimationView;
    private CompMeta cCompMeta;
    private Context context;
    private ExpansionLayout expansionLayout;
    private LinearLayout linearLayout;
    private RecyclerView rvAccentColors;
    private ScrollView scrollView;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.activity_main_edit_panel, viewGroup, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        FragmentActivity activity = getActivity();
        this.context = getContext();
        this.animationView = activity.findViewById(R.id.animation_view);
        this.bgAnimationView = activity.findViewById(R.id.animation_view_bg);
        this.linearLayout = view.findViewById(R.id.dynamicLayout);
        this.scrollView = view.findViewById(R.id.scrollEditPenal);
        this.expansionLayout = view.findViewById(R.id.expansionLayout);
        this.rvAccentColors = view.findViewById(R.id.rvAccentColors);
        this.animationView.setRenderMode(RenderMode.SOFTWARE);
        this.animationView.enableMergePathsForKitKatAndAbove(true);
        setEffect((CompMeta) Objects.requireNonNull(getActivity()).getIntent().getSerializableExtra("CompMeta"));
        getFonts();
    }

    private void setEffect(CompMeta compMeta) {
        TextEffect textEffect = new TextEffect(this.context, compMeta.getEffectName(), this.animationView, this.linearLayout);
        this.linearLayout.removeAllViews();
        playSingleAnimation(textEffect);
        this.animationView.playAnimation();
        try {
            if (textEffect.getEditFields() != null) {
                CompMeta compMeta2 = new CompMeta();
                compMeta2.setEditTexts(textEffect.getEditFields());
                compMeta2.setEffectName(textEffect.getEffectName());
                return;
            }          Toast.makeText(this.context, "error", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void playSingleAnimation(TextEffect textEffect) {
        try {
            textEffect.init();
            textEffect.startAnimation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getFonts() {
        try {
            for (String str : this.context.getAssets().list("fonts")) {
                Log.i(this.TAG, "getFonts: " + str);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(this.TAG, "getFonts: " + e.getMessage());
        }
    }
}
