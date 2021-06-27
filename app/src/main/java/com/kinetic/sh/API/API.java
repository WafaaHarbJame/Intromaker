package com.kinetic.sh.API;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class API {
    private static final String TAG = "API";
    private final Context context;

    public APIResponseListener responseListener;

    public interface APIResponseListener {
        void onError(VolleyError volleyError);

        void onJsonObjectResponse(JSONObject jSONObject);
    }

    public void setResponseListener(APIResponseListener aPIResponseListener) {
        this.responseListener = aPIResponseListener;
    }

    public API(Context context2) {
        this.context = context2;
    }

    public void JsonObjectRequest(String str) {
        Volley.newRequestQueue(this.context).add(new JsonObjectRequest(str, null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject jSONObject) {
                Log.i(API.TAG, "onResponse: " + jSONObject);
                if (API.this.responseListener != null) {
                    API.this.responseListener.onJsonObjectResponse(jSONObject);
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                Log.i(API.TAG, "onErrorResponse: " + volleyError);
                if (API.this.responseListener != null) {
                    API.this.responseListener.onError(volleyError);
                }
            }
        }));
    }
}
