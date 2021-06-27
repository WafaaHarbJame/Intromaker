package com.kinetic.sh.Template.TemplateUtils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kinetic.sh.Effects.BgUtils;
import com.kinetic.sh.Models.MainApplication;
import com.kinetic.sh.Qutils.Utils;
import com.kinetic.sh.Template.Template;

import java.util.ArrayList;

public class TemplateUtils {
    private static final String TAG = "TemplateUtils";
    public static final int TEMPLATE_LOCKDOWN_THRESHOLD = 28;
    private Context context;

    public void setContext(Context context2) {
        this.context = MainApplication.getContext().getApplicationContext();
    }

    public void cacheAssets() {
        Context applicationContext = MainApplication.getContext().getApplicationContext();
        this.context = applicationContext;
        MainApplication mainApplication = (MainApplication) applicationContext.getApplicationContext();
        ArrayList<Template> cacheTemplates = mainApplication.getCacheTemplates();
        boolean z = false;
        if (cacheTemplates != null) {
            int i = 0;
            while (true) {
                if (i >= cacheTemplates.size()) {
                    break;
                } else if (cacheTemplates.get(i).getId().equals(mainApplication.getcTemplate().getId())) {
                    cacheTemplates.set(i, mainApplication.getTemplate());
                    z = true;
                    break;
                } else {
                    i++;
                }
            }
        }
        if (!z) {
            cacheTemplates.add(mainApplication.getTemplate());
        }
        saveTemplates(Utils.PREFS_CACHE_ASSETS, mainApplication.getCacheTemplates());
    }

    public void addTemplateInFavouriteList(Template template) {
        Context applicationContext = MainApplication.getContext().getApplicationContext();
        this.context = applicationContext;
        MainApplication mainApplication = (MainApplication) applicationContext.getApplicationContext();
        ArrayList<Template> favouriteTemplates = mainApplication.getFavouriteTemplates();
        boolean z = false;
        if (favouriteTemplates.size() > 0) {
            int i = 0;
            while (true) {
                if (i >= favouriteTemplates.size()) {
                    break;
                } else if (favouriteTemplates.get(i).getId().equals(template.getId())) {
                    favouriteTemplates.remove(i);
                    z = true;
                    break;
                } else {
                    i++;
                }
            }
        }
        if (!z) {
            favouriteTemplates.add(template);
        }
        saveTemplates(Utils.PREFS_FAVOURITE_LIST, mainApplication.getFavouriteTemplates());
    }

    private void saveTemplates(String str, ArrayList<Template> arrayList) {
        Context applicationContext = MainApplication.getContext().getApplicationContext();
        this.context = applicationContext;
        MainApplication mainApplication = (MainApplication) applicationContext.getApplicationContext();
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(this.context).edit();
        edit.putString(str, new Gson().toJson(arrayList));
        edit.apply();
    }

    public void getAssetsCache() {
        Context applicationContext = MainApplication.getContext().getApplicationContext();
        this.context = applicationContext;
        MainApplication mainApplication = (MainApplication) applicationContext.getApplicationContext();
        ArrayList<Template> arrayList = new Gson().fromJson(PreferenceManager.getDefaultSharedPreferences(this.context).getString(Utils.PREFS_CACHE_ASSETS, ""), new TypeToken<ArrayList<Template>>() {
        }.getType());
        if (arrayList != null) {
            mainApplication.setCacheTemplates(arrayList);
        }
    }

    public void getFavourite() {
        Context applicationContext = MainApplication.getContext().getApplicationContext();
        this.context = applicationContext;
        MainApplication mainApplication = (MainApplication) applicationContext.getApplicationContext();
        ArrayList<Template> arrayList = new Gson().fromJson(PreferenceManager.getDefaultSharedPreferences(this.context).getString(Utils.PREFS_FAVOURITE_LIST, ""), new TypeToken<ArrayList<Template>>() {
        }.getType());
        if (arrayList != null) {
            mainApplication.setFavouriteTemplates(arrayList);
        }
    }

    public boolean isFav(Template template) {
        Context applicationContext = MainApplication.getContext().getApplicationContext();
        this.context = applicationContext;
        MainApplication mainApplication = (MainApplication) applicationContext.getApplicationContext();
        boolean z = false;
        if (mainApplication.getFavouriteTemplates() != null) {
            for (Template value : mainApplication.getFavouriteTemplates()) {
                if (template.getId().equals(value.getId())) {
                    z = true;
                    break;
                }
            }
        }
        return z;
    }

    public boolean isProTemplate(Template template) {
        return Integer.parseInt(parseEffectName(template.getEffectName()).replaceAll("[^0-9]", "")) >= 28;
    }

    private String parseEffectName(String str) {
        String[] split = str.split("/");
        return BgUtils.toCamelCase(split[split.length - 1]);
    }
}
