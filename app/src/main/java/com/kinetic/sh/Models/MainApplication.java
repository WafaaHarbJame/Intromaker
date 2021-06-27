package com.kinetic.sh.Models;

import android.app.Application;
import android.content.Context;

import androidx.preference.PreferenceManager;

import com.android.billingclient.api.Purchase;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kinetic.sh.Qutils.Utils;
import com.kinetic.sh.Template.Template;
import com.kinetic.sh.ads.AdmobAds;

import java.util.ArrayList;

public class MainApplication extends Application {
    private static Context context = null;
    private Template cTemplate;
    private ArrayList<Template> cacheTemplates = new ArrayList<>();
    private ArrayList<AccentColor> colorHistory = new ArrayList<>();
    private ArrayList<Object> favTemplates;
    private ArrayList<Template> favouriteTemplates = new ArrayList<>();
    private Boolean fromProActivity = false;
    private Boolean isFirst = false;
    private Boolean isPremium = false;
    private Purchase purchase;
    private boolean suppressFeedbackDialog = false;
    private Template template = new Template();

    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        loadAds();

    }

    private void loadAds() {
        MobileAds.initialize(this);
        AdmobAds.initFullAds(this);


    }

    public ArrayList<Object> getFavTemplates() {
        ArrayList<Object> arrayList = new Gson().fromJson(PreferenceManager.getDefaultSharedPreferences(context).getString(Utils.PREFS_FAVOURITE_LIST, ""), new TypeToken<ArrayList<Template>>() {
        }.getType());
        this.favTemplates = arrayList;
        return arrayList;
    }

    public ArrayList<AccentColor> getColorHistory() {
        return this.colorHistory;
    }

    public void setNewColorInHistory(AccentColor accentColor) {
        int i = 0;
        while (true) {
            if (i >= this.colorHistory.size()) {
                break;
            } else if (this.colorHistory.get(i).getColor() == accentColor.getColor()) {
                this.colorHistory.remove(i);
                break;
            } else {
                i++;
            }
        }
        this.colorHistory.add(accentColor);
    }

    public void setColorHistory(ArrayList<AccentColor> arrayList) {
        this.colorHistory = arrayList;
    }

    public static Context getContext() {
        return context;
    }

    public boolean isSuppressFeedbackDialog() {
        return this.suppressFeedbackDialog;
    }

    public void setSuppressFeedbackDialog(boolean z) {
        this.suppressFeedbackDialog = z;
    }

    public ArrayList<Template> getFavouriteTemplates() {
        return this.favouriteTemplates;
    }

    public void setFavouriteTemplates(ArrayList<Template> arrayList) {
        this.favouriteTemplates = arrayList;
    }

    public ArrayList<Template> getCacheTemplates() {
        return this.cacheTemplates;
    }

    public void setCacheTemplates(ArrayList<Template> arrayList) {
        this.cacheTemplates = arrayList;
    }

    public Boolean getFirst() {
        return this.isFirst;
    }

    public void setFirst(Boolean bool) {
        this.isFirst = bool;
    }

    public Boolean getPremium() {
        // TODO: Dummy TRUE
        //return true;
        return this.isPremium;
    }

    public void setPremium(Boolean bool) {
        this.isPremium = bool;
    }

    public Boolean getFromProActivity() {
        return this.fromProActivity;
    }

    public void setFromProActivity(Boolean bool) {
        this.fromProActivity = bool;
    }

    public Purchase getPurchase() {
        return this.purchase;
    }

    public void setPurchase(Purchase purchase2) {
        this.purchase = purchase2;
    }

    public void setTemplate(Template template2) {
        this.template = template2;
    }

    public Template getTemplate() {
        return this.template;
    }

    public Template getcTemplate() {
        return this.cTemplate;
    }

    public void setcTemplate(Template template2) {
        this.cTemplate = template2;
    }
}
