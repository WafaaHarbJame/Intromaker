package com.kinetic.sh.Template;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.kinetic.sh.Models.MetaEditText;
import com.kinetic.sh.Template.TemplateUtils.DynamicBgMeta;
import com.kinetic.sh.Template.TemplateUtils.EffectElementColor;
import com.kinetic.sh.Template.TemplateUtils.EffectPosition;
import com.kinetic.sh.Template.TemplateUtils.GradientBgMeta;
import com.kinetic.sh.Template.TemplateUtils.ImageBgMeta;
import com.kinetic.sh.Template.TemplateUtils.SolidBgMeta;
import com.kinetic.sh.Template.TemplateUtils.VideoBgMeta;

import java.util.ArrayList;
import java.util.List;

public class Template {
    private static final String TAG = "Template";
    private int DPI;
    private int bgType;
    private String category;
    private DynamicBgMeta dynamicBgMeta = new DynamicBgMeta();
    private List<EffectElementColor> effectElementColors = new ArrayList<>();
    private String effectName;
    private EffectPosition effectPosition;
    private GradientBgMeta gradientBgMeta = new GradientBgMeta();
    private String id;
    private ImageBgMeta imageBgMeta = new ImageBgMeta();
    private boolean isFavourite = false;
    private boolean isProTemplate = false;
    private List<MetaEditText> metaEditTexts = new ArrayList<>();
    private SolidBgMeta solidBgMeta = new SolidBgMeta();
    private String thumbnail;
    private final int version = 1;
    private VideoBgMeta videoBgMeta = new VideoBgMeta();
    private String videoPreview;

    public boolean isProTemplate() {
        return this.isProTemplate;
    }

    public void setProTemplate(boolean z) {
        this.isProTemplate = z;
    }

    public boolean isFavourite() {
        return this.isFavourite;
    }

    public void setFavourite(boolean z) {
        this.isFavourite = z;
    }

    public int getDPI() {
        return this.DPI;
    }

    public void setDPI(int i) {
        this.DPI = i;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public GradientBgMeta getGradientBgMeta() {
        return this.gradientBgMeta;
    }

    public String getVideoPreview() {
        return this.videoPreview;
    }

    public void setVideoPreview(String str) {
        this.videoPreview = str;
    }

    public String getThumbnail() {
        return this.thumbnail;
    }

    public void setThumbnail(String str) {
        this.thumbnail = str;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String str) {
        this.category = str;
    }

    public void setGradientBgMeta(GradientBgMeta gradientBgMeta2) {
        this.gradientBgMeta = gradientBgMeta2;
    }

    public SolidBgMeta getSolidBgMeta() {
        return this.solidBgMeta;
    }

    public void setSolidBgMeta(SolidBgMeta solidBgMeta2) {
        this.solidBgMeta = solidBgMeta2;
    }

    public VideoBgMeta getVideoBgMeta() {
        return this.videoBgMeta;
    }

    public void setVideoBgMeta(VideoBgMeta videoBgMeta2) {
        this.videoBgMeta = videoBgMeta2;
    }

    public DynamicBgMeta getDynamicBgMeta() {
        return this.dynamicBgMeta;
    }

    public void setDynamicBgMeta(DynamicBgMeta dynamicBgMeta2) {
        this.dynamicBgMeta = dynamicBgMeta2;
    }

    public void setEffectName(String str) {
        this.effectName = str;
    }

    public String getEffectName() {
        return this.effectName;
    }

    public List<MetaEditText> getMetaEditTexts() {
        return this.metaEditTexts;
    }

    public void setMetaEditTexts(List<MetaEditText> list) {
        this.metaEditTexts = list;
    }

    public List<EffectElementColor> getEffectElementColors() {
        return this.effectElementColors;
    }

    public void setEffectElementColors(List<EffectElementColor> list) {
        this.effectElementColors = list;
    }

    public EffectPosition getEffectPosition() {
        return this.effectPosition;
    }

    public void setEffectPosition(EffectPosition effectPosition2) {
        this.effectPosition = effectPosition2;
    }

    public int getBgType() {
        return this.bgType;
    }

    public void setBgType(int i) {
        this.bgType = i;
    }

    public ImageBgMeta getImageBgMeta() {
        return this.imageBgMeta;
    }

    public void setImageBgMeta(ImageBgMeta imageBgMeta2) {
        this.imageBgMeta = imageBgMeta2;
    }

    public String makeJson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        gsonBuilder.setPrettyPrinting();
        String json = gsonBuilder.create().toJson(this);
        Log.i(TAG, "makeJson: " + json);
        return json;
    }
}
