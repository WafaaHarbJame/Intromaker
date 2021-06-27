package com.kinetic.sh.Helpers;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.kinetic.sh.Models.MainApplication;
import com.kinetic.sh.R;

import org.json.JSONObject;

public class PixabayImg {
    private static final int PER_PAGE = 20;
    private static final String TAG = "PixabayImg";
    private String baseUrl;
    private final Context context;
    private int page = 1;

    public PixabayImgListener pixabayImgListener;
    private String query = "yellow+flowers";

    public interface PixabayImgListener {
        void onDataLoaded(JSONObject jSONObject);

        void onError(VolleyError volleyError);
    }

    public void setQuery(String str) {
        this.page = 1;
        this.query = str;
        this.baseUrl = getBaseUrl();
    }

    public String getBaseUrl() {
        return "https://pixabay.com/api/?key=" + MainApplication.getContext().getString(R.string.pixabay_key) + "&q=" + this.query + "&image_type=photo&per_page=" + PER_PAGE + "&page=" + this.page;
    }

    public PixabayImg(Context context2) {
        this.context = context2;
    }

    public void setPixabayImgListener(PixabayImgListener pixabayImgListener2) {
        this.pixabayImgListener = pixabayImgListener2;
    }

    public void JsonObjectRequest(String str) {
        Log.i(TAG, "JsonObjectRequest:  " + str);
        Volley.newRequestQueue(this.context).add(new JsonObjectRequest(str, null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject jSONObject) {
                if (PixabayImg.this.pixabayImgListener != null) {
                    PixabayImg.this.pixabayImgListener.onDataLoaded(jSONObject);
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                if (PixabayImg.this.pixabayImgListener != null) {
                    PixabayImg.this.pixabayImgListener.onError(volleyError);
                }
            }
        }));
    }

    public void init() {
        this.baseUrl = getBaseUrl();
        Log.i(TAG, "init: " + this.baseUrl);
        JsonObjectRequest(this.baseUrl);
    }

    public void getNextPage() {
        this.page++;
        String baseUrl2 = getBaseUrl();
        this.baseUrl = baseUrl2;
        JsonObjectRequest(baseUrl2);
    }
}
