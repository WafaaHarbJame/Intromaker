package com.kinetic.sh.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kinetic.sh.Helpers.GD;
import com.kinetic.sh.R;

import java.util.List;

public class AdapterGradients extends RecyclerView.Adapter<AdapterGradients.ViewHolder> {
    private final List<GD> gradientDrawables;

    public GradientsInterface gradientsInterface;

    public int selectedPosition = -1;

    public interface GradientsInterface {
        void onClick(GD gd);
    }

    public AdapterGradients(List<GD> list) {
        this.gradientDrawables = list;
    }

    public void setGradientsInterface(GradientsInterface gradientsInterface2) {
        this.gradientsInterface = gradientsInterface2;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_gradient, viewGroup, false));
    }

    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        final GD gd = this.gradientDrawables.get(i);
        viewHolder.linearLayout.setBackground(gd);
        if (this.selectedPosition == i) {
            viewHolder.selectedOverlay.setVisibility(View.VISIBLE);
            gd.setPosition(this.selectedPosition);
        } else {
            viewHolder.selectedOverlay.setVisibility(View.GONE);
        }
        if (this.gradientsInterface != null) {
            viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    AdapterGradients.this.selectedPosition = i;
                    viewHolder.selectedOverlay.setVisibility(View.VISIBLE);
                    AdapterGradients.this.gradientsInterface.onClick(gd);
                    AdapterGradients.this.notifyDataSetChanged();
                }
            });
        }
    }

    public int getItemCount() {
        return this.gradientDrawables.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout linearLayout;

        public ImageView selectedOverlay;

        public ViewHolder(View view) {
            super(view);
            this.linearLayout = view.findViewById(R.id.card_gradient_background);
            ImageView imageView = view.findViewById(R.id.card_gradient_selected);
            this.selectedOverlay = imageView;
            imageView.setVisibility(View.GONE);
        }
    }
}
