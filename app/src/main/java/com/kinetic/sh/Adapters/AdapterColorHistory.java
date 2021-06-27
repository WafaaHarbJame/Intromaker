package com.kinetic.sh.Adapters;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.kinetic.sh.Models.AccentColor;
import com.kinetic.sh.R;

import java.util.ArrayList;

public class AdapterColorHistory extends RecyclerView.Adapter<AdapterColorHistory.ViewHolder> {

    public ColorHistoryListener colorHistoryListener;
    private ArrayList<AccentColor> colors;

    public interface ColorHistoryListener {
        void OnClick(AccentColor accentColor);
    }

    public void setColors(ArrayList<AccentColor> arrayList) {
        this.colors = arrayList;
    }

    public void setColorHistoryListener(ColorHistoryListener colorHistoryListener2) {
        this.colorHistoryListener = colorHistoryListener2;
    }

    public AdapterColorHistory(ArrayList<AccentColor> arrayList) {
        this.colors = arrayList;
    }

    @NonNull
    @Override
    public AdapterColorHistory.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_color_card, parent, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final AccentColor accentColor = this.colors.get(i);
        viewHolder.cardView.setBackgroundTintList(ColorStateList.valueOf(accentColor.getColor()));
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (AdapterColorHistory.this.colorHistoryListener != null) {
                    AdapterColorHistory.this.colorHistoryListener.OnClick(accentColor);
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
            this.cardView = view.findViewById(R.id.color_card);
        }
    }
}
