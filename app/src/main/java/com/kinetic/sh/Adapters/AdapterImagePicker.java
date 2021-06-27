package com.kinetic.sh.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.florent37.expansionpanel.ExpansionLayout;
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection;
import com.kinetic.sh.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AdapterImagePicker extends RecyclerView.Adapter<AdapterImagePicker.ViewHolder> {
    private static final String TAG = "AdapterImagePicker";
    private Context context;
    private final ExpansionLayoutCollection expansionsCollection = new ExpansionLayoutCollection();

    public ImagePickerListener imagePickerListener;
    private final List<JSONObject> jsonObjects;

    public interface ImagePickerListener {
        void onSelected(JSONObject jSONObject, ViewHolder viewHolder);
    }

    public void setImagePickerListener(ImagePickerListener imagePickerListener2) {
        this.imagePickerListener = imagePickerListener2;
    }

    public AdapterImagePicker(List<JSONObject> list) {
        this.jsonObjects = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_image_bg, viewGroup, false);
        this.context = viewGroup.getContext();
        return new ViewHolder(inflate);
    }

    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final JSONObject jSONObject = this.jsonObjects.get(i);
        this.expansionsCollection.add(viewHolder.getExpansionLayout());
        try {
            Glide.with(this.context).load(jSONObject.getString("webformatURL")).into(viewHolder.imageView);
            viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Log.i(AdapterImagePicker.TAG, "onClick:  ");
                    if (viewHolder.expansionLayout.isExpanded()) {
                        viewHolder.expansionLayout.collapse(true);
                    } else {
                        viewHolder.expansionLayout.expand(true);
                    }
                }
            });
            viewHolder.setBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (AdapterImagePicker.this.imagePickerListener != null) {
                        AdapterImagePicker.this.imagePickerListener.onSelected(jSONObject, viewHolder);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getItemCount() {
        return this.jsonObjects.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;

        public ExpansionLayout expansionLayout;

        public ImageView imageView;
        public RelativeLayout loadingContainer;
        public ProgressBar progressBar;

        public Button setBtn;

        public ViewHolder(View view) {
            super(view);
            this.imageView = view.findViewById(R.id.bs_ip_image);
            this.expansionLayout = view.findViewById(R.id.expansionLayout_image_bg);
            this.cardView = view.findViewById(R.id.image_bg_container);
            this.setBtn = view.findViewById(R.id.image_bg_set_btn);
            this.loadingContainer = view.findViewById(R.id.image_bg_loading_container);
            this.progressBar = view.findViewById(R.id.image_picker_progress_bar);
        }

        public ExpansionLayout getExpansionLayout() {
            return this.expansionLayout;
        }
    }
}
