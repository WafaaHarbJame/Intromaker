package com.kinetic.sh.Adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.kinetic.sh.Models.AccentColor;
import com.kinetic.sh.R;

import java.util.List;

public class AdapterColors extends RecyclerView.Adapter<AdapterColors.ViewHolder> {
    private final String TAG = "AdapterColors";
    ColorAccentInterface colorAccentInterface;
    private final List<AccentColor> colors;
    private Context context;

    public interface ColorAccentInterface {
        void colorPicked(AccentColor accentColor, MaterialCardView materialCardView);
    }

    public void setColorAccentInterface(ColorAccentInterface colorAccentInterface2) {
        this.colorAccentInterface = colorAccentInterface2;
    }

    public AdapterColors(List<AccentColor> list) {
        this.colors = list;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_main_bottom_sheet_color, viewGroup, false);
        this.context = viewGroup.getContext();
        return new ViewHolder(inflate);
    }

    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final AccentColor accentColor = this.colors.get(i);
        ViewCompat.setBackgroundTintList(viewHolder.cardView, ColorStateList.valueOf(accentColor.getColor()));
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (AdapterColors.this.colorAccentInterface != null) {
                    AdapterColors.this.colorAccentInterface.colorPicked(accentColor, viewHolder.cardView);
                }
            }
        });
    }

    public int getItemCount() {
        return this.colors.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public MaterialCardView cardView;

        public ViewHolder(View view) {
            super(view);
            this.cardView = view.findViewById(R.id.colorCard);
        }
    }
}
