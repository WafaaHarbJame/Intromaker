package com.kinetic.sh.Adapters;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.kinetic.sh.R;

import java.util.List;

public class AdapterTypefaces extends RecyclerView.Adapter<AdapterTypefaces.ViewHolder> {
    private static final String TAG = "AdapterTypefaces";

    public AdapterTypefacesInter adapterTypefacesInter;
    private Context context;
    private List<String> typfaces;

    public interface AdapterTypefacesInter {
        void onClicked(String str);
    }

    public void setAdapterTypefacesInter(AdapterTypefacesInter adapterTypefacesInter2) {
        this.adapterTypefacesInter = adapterTypefacesInter2;
    }

    public AdapterTypefaces(List<String> list) {
        this.typfaces = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_typeface, viewGroup, false);
        this.context = viewGroup.getContext();
        return new ViewHolder(inflate);
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final String str = this.typfaces.get(i);
        AssetManager assets = this.context.getAssets();
        viewHolder.textView.setTypeface(Typeface.createFromAsset(assets, "fonts/" + str));
        if (this.adapterTypefacesInter != null) {
            viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Log.i(TAG, "onClick: " + str);
                    AdapterTypefaces.this.adapterTypefacesInter.onClicked(str);
                }
            });
        }
    }

    public int getItemCount() {
        return this.typfaces.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;

        public TextView textView;

        public ViewHolder(View view) {
            super(view);
            this.textView = (TextView) view.findViewById(R.id.card_typeface_text_view);
            this.cardView = (CardView) view.findViewById(R.id.card_typeface_card_view);
        }
    }
}
