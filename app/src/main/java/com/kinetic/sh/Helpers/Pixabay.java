package com.kinetic.sh.Helpers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.kinetic.sh.API.API;
import com.kinetic.sh.Models.MainApplication;
import com.kinetic.sh.Models.VideoMeta;
import com.kinetic.sh.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Pixabay {
    private static int PER_PAGE = 20;
    private static final String TAG = "Pixabay";
    private int CURRANT_PAGE = 1;

    public PixabayListener pixabayListener;
    private String query;

    public int total;

    public interface PixabayListener {
        void onDataLoaded(List<VideoMeta> list);

        void onError(VolleyError volleyError);
    }

    private String getBaseURL() {
        return "https://pixabay.com/api/videos/?key=" + MainApplication.getContext().getString(R.string.pixabay_key) + "&safesearch=true&per_page=" + PER_PAGE;
    }

    public boolean getPixabayListener() {
        return this.pixabayListener == null;
    }

    public void setPixabayListener(PixabayListener pixabayListener2) {
        this.pixabayListener = pixabayListener2;
    }

    public String getQueryString(String str) {
        return getBaseURL() + "&q=" + str + "&page=" + this.CURRANT_PAGE;
    }

    public String getThubnalilImageUrl(String str) {
        return "https://i.vimeocdn.com/video/" + str + "_640x360.jpg";
    }

    public void nextPage(Context context) {
        int i = this.total / PER_PAGE;
        int i2 = this.CURRANT_PAGE;
        if (i >= i2) {
            this.CURRANT_PAGE = i2 + 1;
            getInitialData(context, this.query);
            Log.i(TAG, "nextPage: " + i + " " + this.CURRANT_PAGE);
            return;
        }
        Log.i(TAG, "nextPage: this is a last page");
        Toast.makeText(context, "No more video exist", Toast.LENGTH_LONG).show();
    }

    public void getInitialData(final Context context, String str) {
        this.query = str;
        final ArrayList<VideoMeta> arrayList = new ArrayList<>();
        API api = new API(context);
        String queryString = getQueryString(str);
        api.JsonObjectRequest(queryString);
        Log.i(TAG, "getInitialData: " + queryString);
        api.setResponseListener(new API.APIResponseListener() {
            public void onJsonObjectResponse(JSONObject jSONObject) {
                try {
                    JSONArray jSONArray = jSONObject.getJSONArray("hits");
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                        if (Integer.parseInt(jSONObject2.getString("duration")) > 10) {
                            String string = jSONObject2.getString("picture_id");
                            VideoMeta videoMeta = new VideoMeta();
                            String thumbnailImageUrl = Pixabay.this.getThubnalilImageUrl(string);
                            videoMeta.setVideos(jSONObject2.getJSONObject("videos"));
                            videoMeta.setThumbnail(thumbnailImageUrl);
                            arrayList.add(videoMeta);
                        }
                    }
                    Pixabay.this.total = Integer.parseInt(jSONObject.getString("total"));
                    if (Pixabay.this.pixabayListener != null) {
                        Pixabay.this.pixabayListener.onDataLoaded(arrayList);
                    }
                } catch (JSONException e) {
                    Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            public void onError(VolleyError volleyError) {
                if (Pixabay.this.pixabayListener != null) {
                    Pixabay.this.pixabayListener.onError(volleyError);
                }
            }
        });
        Log.i(TAG, "getVideoMeta: " + arrayList.size());
    }

    private void getNextPageData(final Context context) {
        final ArrayList<VideoMeta> arrayList = new ArrayList<>();
        API api = new API(context);
        api.JsonObjectRequest(getQueryString(this.query));
        api.setResponseListener(new API.APIResponseListener() {
            public void onJsonObjectResponse(JSONObject jSONObject) {
                try {
                    JSONArray jSONArray = jSONObject.getJSONArray("hits");
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                        String string = jSONObject2.getString("picture_id");
                        VideoMeta videoMeta = new VideoMeta();
                        String thubnalilImageUrl = Pixabay.this.getThubnalilImageUrl(string);
                        videoMeta.setVideos(jSONObject2.getJSONObject("videos"));
                        videoMeta.setThumbnail(thubnalilImageUrl);
                        arrayList.add(videoMeta);
                    }
                    if (Pixabay.this.pixabayListener != null) {
                        Pixabay.this.pixabayListener.onDataLoaded(arrayList);
                    }
                } catch (JSONException e) {
                    Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            public void onError(VolleyError volleyError) {
                if (Pixabay.this.pixabayListener != null) {
                    Pixabay.this.pixabayListener.onError(volleyError);
                }
            }
        });
        Log.i(TAG, "getVideoMeta: " + arrayList.size());
    }
}
