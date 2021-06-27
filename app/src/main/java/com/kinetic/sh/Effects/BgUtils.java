package com.kinetic.sh.Effects;

import android.content.Context;

import com.kinetic.sh.Template.Template;
import com.kinetic.sh.Template.TemplateUtils.BgElementColor;
import com.kinetic.sh.Template.TemplateUtils.DynamicBgMeta;
import com.kinetic.sh.Ui.MainActivity;

import java.util.Arrays;
import java.util.List;

public class BgUtils {
    public void _setBgType(Context context, int i) {
        ((MainActivity) context).global.getTemplate().setBgType(i);
    }

    public void _setDynamic(Context context, String str) {
        Template template = ((MainActivity) context).global.getTemplate();
        DynamicBgMeta dynamicBgMeta = new DynamicBgMeta();
        dynamicBgMeta.setName(str);
        template.setDynamicBgMeta(dynamicBgMeta);
    }

    public void _addDynamicElementBackgroundColor(Context context, int i, String... strArr) {
        ((MainActivity) context).global.getTemplate().getDynamicBgMeta().getBgElementColors().add(new BgElementColor(i, strArr));
    }

    public void _addDynamicElementBackgroundColorValue(Context context, int i, String... strArr) {
        BgElementColor containsBgElementColor = containsBgElementColor(((MainActivity) context).global.getTemplate().getDynamicBgMeta().getBgElementColors(), strArr);
        if (containsBgElementColor != null) {
            containsBgElementColor.setColor(i);
        }
    }

    public void _addMultiPathColorAtDifferentIntensity(Context context, int i, float[] fArr, String[]... strArr) {
        List<BgElementColor> bgElementColors = ((MainActivity) context).global.getTemplate().getDynamicBgMeta().getBgElementColors();
        BgElementColor bgElementColor = new BgElementColor(i, strArr);
        bgElementColor.setIntensity(fArr);
        bgElementColors.add(bgElementColor);
    }

    public void _addMultiPathColorAtDifferentIntensityValue(Context context, int i, String[]... strArr) {
        BgElementColor containsBgElementColor = containsBgElementColor(((MainActivity) context).global.getTemplate().getDynamicBgMeta().getBgElementColors(), strArr);
        if (containsBgElementColor != null) {
            containsBgElementColor.setColor(i);
        }
    }

    public void _setTransperency(Context context, float f) {
        ((MainActivity) context).global.getTemplate().getDynamicBgMeta().setTransperency(f);
    }

    public static BgElementColor containsBgElementColor(List<BgElementColor> list, String[] strArr) {
        for (BgElementColor next : list) {
            if (next != null && Arrays.equals(next.getPath(), strArr)) {
                return next;
            }
        }
        return null;
    }

    public static BgElementColor containsBgElementColor(List<BgElementColor> list, String[][] strArr) {
        for (BgElementColor next : list) {
            if (next != null && Arrays.equals(next.getMultiPath(), strArr)) {
                return next;
            }
        }
        return null;
    }

    public static String toCamelCase(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(str.length());
        for (String str2 : str.split(" ")) {
            if (!str2.isEmpty()) {
                sb.append(Character.toUpperCase(str2.charAt(0)));
                sb.append(str2.substring(1).toLowerCase());
            }
            if (sb.length() != str.length()) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }
}
