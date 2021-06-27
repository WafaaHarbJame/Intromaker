package com.kinetic.sh.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.florent37.expansionpanel.ExpansionLayout;
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection;
import com.kinetic.sh.Helpers.UIUtils;
import com.kinetic.sh.Models.VideoMeta;
import com.kinetic.sh.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AdapterVideoPicker extends RecyclerView.Adapter<AdapterVideoPicker.ViewHolder> {
    private static final int AD = 1;
    private static final int CONTENT = 0;
    private static final int LIST_AD_DELTA = 3;
    private static final String TAG = "AdapterVideoPicker";
    private Context context;
    private final ExpansionLayoutCollection expansionsCollection = new ExpansionLayoutCollection();
    private final List<VideoMeta> videoMetas;

    public videoPickerListener videoPickerListener;

    public interface videoPickerListener {
        void onItemClick(JSONObject jSONObject);
    }

    public AdapterVideoPicker(List<VideoMeta> list) {
        this.videoMetas = list;
    }

    public void setVideoPickerListener(videoPickerListener videopickerlistener) {
        this.videoPickerListener = videopickerlistener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_video_thumb, viewGroup, false);
        this.context = viewGroup.getContext();
        return new ViewHolder(inflate);
    }

    public int getItemViewType(int i) {
        return (i <= 0 || i % 3 != 0) ? 0 : 1;
    }

    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        VideoMeta videoMeta = this.videoMetas.get(i);
        this.expansionsCollection.add(viewHolder.getExpansionLayout());
        JSONObject videos = videoMeta.getVideos();
        final ArrayList<JSONObject> arrayList = new ArrayList<>();
        Iterator<String> keys = videos.keys();
        while (keys.hasNext()) {
            try {
                JSONObject jSONObject = videos.getJSONObject(keys.next());
                String string = jSONObject.getString("height");
                if (!string.equals("0") && Integer.parseInt(string) <= 1080) {
                    arrayList.add(jSONObject);
                }
            } catch (JSONException unused) {
                Toast.makeText(this.context, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }
        viewHolder.listView.setAdapter(new AdapterVideoPickerExtension(this.context, arrayList));
        UIUtils.setListViewHeightBasedOnItems(viewHolder.listView);
        Glide.with(this.context).load(videoMeta.getThumbnail()).into(viewHolder.thumbImageView);
        viewHolder.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                JSONObject jSONObject = arrayList.get(i);
                if (AdapterVideoPicker.this.videoPickerListener != null) {
                    AdapterVideoPicker.this.videoPickerListener.onItemClick(jSONObject);
                }
            }
        });
        viewHolder.frameLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (viewHolder.expansionLayout.isExpanded()) {
                    viewHolder.expansionLayout.collapse(true);
                } else {
                    viewHolder.expansionLayout.expand(true);
                }
            }
        });
    }

    public int getItemCount() {
        return this.videoMetas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView creditTextView;

        public ExpansionLayout expansionLayout;

        public FrameLayout frameLayout;

        public ListView listView;

        public ImageView thumbImageView;

        public ViewHolder(View view) {
            super(view);
            this.creditTextView = view.findViewById(R.id.bs_vp_credit_text);
            this.thumbImageView = view.findViewById(R.id.bs_vp_image);
            this.expansionLayout = view.findViewById(R.id.bs_vp_expanction_pannel);
            this.frameLayout = view.findViewById(R.id.bs_vp_frame_layout);
            this.listView = view.findViewById(R.id.bs_vp_list_view);
        }

        public ExpansionLayout getExpansionLayout() {
            return this.expansionLayout;
        }
    }
}
