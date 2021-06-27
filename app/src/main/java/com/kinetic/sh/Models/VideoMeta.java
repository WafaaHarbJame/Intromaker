package com.kinetic.sh.Models;

import org.json.JSONObject;

public class VideoMeta {
    private JSONObject jsonObject;
    private String[] resolutions;
    private String[] sizes;
    private String thumbnail;
    private JSONObject videos;

    public void setVideos(JSONObject jSONObject) {
        this.videos = jSONObject;
    }

    public JSONObject getVideos() {
        return this.videos;
    }

    public void setResolutions(String[] strArr) {
        this.resolutions = strArr;
    }

    public void setSizes(String[] strArr) {
        this.sizes = strArr;
    }

    public String[] getResolutions() {
        return this.resolutions;
    }

    public String[] getSizes() {
        return this.sizes;
    }

    public String getThumbnail() {
        return this.thumbnail;
    }

    public void setThumbnail(String str) {
        this.thumbnail = str;
    }

    public void setJsonObject(JSONObject jSONObject) {
        this.jsonObject = jSONObject;
    }

    public JSONObject getJsonObject() {
        return this.jsonObject;
    }
}
