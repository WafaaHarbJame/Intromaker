package com.kinetic.sh.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.kinetic.sh.Models.MainApplication;
import com.kinetic.sh.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class AdapterVideoPickerExtension extends ArrayAdapter<JSONObject> {
    private static final String TAG = "AdapterVideoPickerExten";
    private final Context context;
    private final ArrayList<JSONObject> videos;

    public AdapterVideoPickerExtension(Context context2, ArrayList<JSONObject> arrayList) {
        super(context2, R.layout.item_video_resolution, arrayList);
        this.videos = arrayList;
        this.context = context2;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater from = LayoutInflater.from(this.context);
        JSONObject jSONObject = this.videos.get(i);
        if (view == null) {
            view = from.inflate(R.layout.item_video_resolution, viewGroup, false);
            try {
                String string = jSONObject.getString("size");
                String string2 = jSONObject.getString("height");
                TextView textView = view.findViewById(R.id.item_video_resolution_text);
                TextView textView2 = view.findViewById(R.id.item_video_size);
                ImageView imageView = view.findViewById(R.id.pro_res_lock);
                imageView.setVisibility(View.GONE);
                textView.setText(jSONObject.getString("height") + "p");
                DecimalFormat decimalFormat = new DecimalFormat("#");
                textView2.setText(decimalFormat.format(Float.parseFloat(string) / 1000000.0f) + " MB");
                if (!((MainApplication) ((AppCompatActivity) this.context).getApplication()).getPremium() && Integer.parseInt(string2) <= 1080 && Integer.parseInt(string2) >= 720) {
                    view.setBackgroundColor(ContextCompat.getColor(this.context, R.color.colorPrimary));
                    textView2.setTextColor(ContextCompat.getColor(this.context, R.color.colorAccent));
                    textView.setTextColor(ContextCompat.getColor(this.context, R.color.colorAccent));
                    imageView.setVisibility(View.VISIBLE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return view;
    }

    public int randomColor() {
        Random random = new Random();
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }
}
