package com.kinetic.sh.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.kinetic.sh.Models.CompMeta;
import com.kinetic.sh.R;

import java.util.List;

public class AdapterDynamicBg extends RecyclerView.Adapter<AdapterDynamicBg.ViewHolder> {
    private final List<CompMeta> compMetas;
    private Context context;

    public InterAdapterDynamicBg interAdapterDynamicBg;

    public int selectedPosition = -1;

    public interface InterAdapterDynamicBg {
        void onClick(CompMeta compMeta, ViewHolder viewHolder);
    }

    public AdapterDynamicBg(List<CompMeta> list) {
        this.compMetas = list;
    }

    public void setInterAdapterDynamicBg(InterAdapterDynamicBg interAdapterDynamicBg2) {
        this.interAdapterDynamicBg = interAdapterDynamicBg2;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_dynamic_background, viewGroup, false);
        this.context = viewGroup.getContext();
        return new ViewHolder(inflate);
    }

    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final CompMeta compMeta = this.compMetas.get(i);
        if (this.selectedPosition == i) {
            viewHolder.selectedOverlay.setVisibility(View.VISIBLE);
        } else {
            viewHolder.selectedOverlay.setVisibility(View.GONE);
        }
        Resources resources = this.context.getResources();
        Glide.with(this.context).load(resources.getIdentifier("dbg_" + i, "drawable", this.context.getPackageName())).into(viewHolder.imageView);
        if (this.interAdapterDynamicBg != null) {
            viewHolder.frameLayout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    AdapterDynamicBg.this.selectedPosition = i;
                    viewHolder.selectedOverlay.setVisibility(View.VISIBLE);
                    AdapterDynamicBg.this.interAdapterDynamicBg.onClick(compMeta, viewHolder);
                    AdapterDynamicBg.this.notifyDataSetChanged();
                }
            });
        }
    }

    public void setSelectedPosition(int i) {
        this.selectedPosition = i;
    }

    public int getItemCount() {
        return this.compMetas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final LottieAnimationView animationView;

        public FrameLayout frameLayout;

        public ImageView imageView;

        public ImageView selectedOverlay;

        public ViewHolder(View view) {
            super(view);
            this.imageView = view.findViewById(R.id.dyn_image);
            this.frameLayout = view.findViewById(R.id.card_dyn_frame_layout);
            LottieAnimationView lottieAnimationView = view.findViewById(R.id.card_dyn_picker_animation_view);
            this.animationView = lottieAnimationView;
            lottieAnimationView.enableMergePathsForKitKatAndAbove(true);
            ImageView imageView2 = view.findViewById(R.id.dyn_selected);
            this.selectedOverlay = imageView2;
            imageView2.setVisibility(View.GONE);
        }
    }
}
