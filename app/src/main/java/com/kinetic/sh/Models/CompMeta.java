package com.kinetic.sh.Models;

import android.widget.EditText;

import java.io.Serializable;
import java.util.List;

public class CompMeta implements Serializable {
    private String bgType;
    private String dynamicBg;
    private List<EditText> editTexts;
    private String effectName;
    private boolean isProTemplate = false;

    public boolean isProTemplate() {
        return this.isProTemplate;
    }

    public void setProTemplate(boolean z) {
        this.isProTemplate = z;
    }

    public String getEffectName() {
        return this.effectName;
    }

    public void setEffectName(String str) {
        this.effectName = str;
    }

    public void setEditTexts(List<EditText> list) {
        this.editTexts = list;
    }

    public List<EditText> getEditTexts() {
        return this.editTexts;
    }

    public void setDynamicBg(String str) {
        this.dynamicBg = str;
    }

    public String getDynamicBg() {
        return this.dynamicBg;
    }

    public String getBgType() {
        return this.bgType;
    }

    public void setBgType(String str) {
        this.bgType = str;
    }
}
