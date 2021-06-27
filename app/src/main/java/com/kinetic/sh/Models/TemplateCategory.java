package com.kinetic.sh.Models;

import com.kinetic.sh.Template.Template;

import java.util.ArrayList;

public class TemplateCategory {
    private String category;
    private ArrayList<Template> templates;

    public TemplateCategory(String str, ArrayList arrayList) {
        this.category = str;
        this.templates = arrayList;
    }

    public String getCategory() {
        return this.category;
    }

    public ArrayList<Template> getTemplates() {
        return this.templates;
    }
}
