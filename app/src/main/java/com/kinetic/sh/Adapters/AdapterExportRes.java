package com.kinetic.sh.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.kinetic.sh.Models.ExportResolutions;
import com.kinetic.sh.R;

import java.util.List;

public class AdapterExportRes extends ArrayAdapter<ExportResolutions> {
    private final Context context;
    private final List<ExportResolutions> resolutions;

    public AdapterExportRes(Context context2, List<ExportResolutions> list) {
        super(context2, R.layout.item_video_resolution, list);
        this.resolutions = list;
        this.context = context2;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater from = LayoutInflater.from(this.context);
        ExportResolutions exportResolutions = this.resolutions.get(i);
        if (view != null) {
            return view;
        }
        View inflate = from.inflate(R.layout.item_video_resolution, viewGroup, false);
        TextView textView = inflate.findViewById(R.id.item_video_resolution_text);
        inflate.findViewById(R.id.pro_res_lock).setVisibility(View.GONE);
        textView.setText(exportResolutions.getHeight() + "p");
        ((TextView) inflate.findViewById(R.id.item_video_size)).setText(exportResolutions.getResType());
        textView.setTextColor(ContextCompat.getColor(this.context, R.color.colorAccent));
        return inflate;
    }
}
