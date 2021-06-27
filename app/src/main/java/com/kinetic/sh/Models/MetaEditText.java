package com.kinetic.sh.Models;

public class MetaEditText {
    private String delegateText;
    private int fontColor;
    private String value;
    private Boolean visibility = false;

    public String getDelegateText() {
        return this.delegateText;
    }

    public void setDelegateText(String str) {
        this.delegateText = str;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String str) {
        this.value = str;
    }

    public Boolean getVisibility() {
        return this.visibility;
    }

    public void setVisibility(Boolean bool) {
        this.visibility = bool;
    }

    public int getFontColor() {
        return this.fontColor;
    }

    public void setFontColor(int i) {
        this.fontColor = i;
    }
}
