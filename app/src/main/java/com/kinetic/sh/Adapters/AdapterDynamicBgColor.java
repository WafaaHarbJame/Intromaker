package com.kinetic.sh.Adapters;

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

public class AdapterDynamicBgColor extends RecyclerView.Adapter<AdapterDynamicBgColor.ViewHolder> {
    List<AccentColor> colors;

    public DynamicBgListener dynamicBgListener;

    public interface DynamicBgListener {
        void colorPicked(AccentColor accentColor, MaterialCardView materialCardView);
    }

    public AdapterDynamicBgColor(List<AccentColor> list) {
        this.colors = list;
    }

    public void setDynamicBgListener(DynamicBgListener dynamicBgListener2) {
        this.dynamicBgListener = dynamicBgListener2;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_main_bottom_sheet_color, viewGroup, false));
    }

    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final AccentColor accentColor = this.colors.get(i);
        ViewCompat.setBackgroundTintList(viewHolder.cardView, ColorStateList.valueOf(accentColor.getColor()));
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (AdapterDynamicBgColor.this.dynamicBgListener != null) {
                    AdapterDynamicBgColor.this.dynamicBgListener.colorPicked(accentColor, viewHolder.cardView);
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
