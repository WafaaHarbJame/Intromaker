package com.kinetic.sh.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kinetic.sh.Models.CompMeta;
import com.kinetic.sh.R;

import java.util.List;

public class AdapterTile extends RecyclerView.Adapter<AdapterTile.ViewHolder> {
    AdapterTileInterface adapterTileInterface;
    private final List<CompMeta> compMetas;

    public interface AdapterTileInterface {
        void onClickCallback(CompMeta compMeta);
    }

    public AdapterTile(List<CompMeta> list) {
        this.compMetas = list;
    }

    public void setAdapterTileInterface(AdapterTileInterface adapterTileInterface2) {
        this.adapterTileInterface = adapterTileInterface2;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_effect_tile, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final CompMeta compMeta = this.compMetas.get(i);
        viewHolder.title.setText(String.valueOf(i + 1));
        viewHolder.title.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (AdapterTile.this.adapterTileInterface != null) {
                    AdapterTile.this.adapterTileInterface.onClickCallback(compMeta);
                }
            }
        });
    }

    public int getItemCount() {
        return this.compMetas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public ViewHolder(View view) {
            super(view);
            this.title = view.findViewById(R.id.card_tile_title);
        }
    }
}
