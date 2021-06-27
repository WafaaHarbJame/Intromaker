package com.kinetic.sh.Effects;

import android.content.Context;
import android.util.Log;

import com.kinetic.sh.Models.MetaEditText;
import com.kinetic.sh.Template.Template;
import com.kinetic.sh.Template.TemplateUtils.EffectElementColor;
import com.kinetic.sh.Template.TemplateUtils.EffectPosition;
import com.kinetic.sh.Ui.MainActivity;

import java.text.Bidi;
import java.util.Arrays;
import java.util.List;

public class TileUtils {
    private static final String TAG = "TileUtils";

    public boolean isRTL(String str) {
        return !new Bidi(str, -2).isLeftToRight();
    }

    public void _saveEditText(Context context, String str, String str2) {
        Template template = ((MainActivity) context).global.getTemplate();
        MetaEditText metaEditText = new MetaEditText();
        metaEditText.setDelegateText(str);
        metaEditText.setValue(str2);
        List<MetaEditText> metaEditTexts = template.getMetaEditTexts();
        metaEditTexts.add(metaEditText);
        Log.i(TAG, "_saveIntense:  " + str + " size: " + metaEditTexts.size());
    }

    public void _saveTextValue(Context context, String str, String str2) {
        MetaEditText containsDelegateText = containsDelegateText(((MainActivity) context).global.getTemplate().getMetaEditTexts(), str);
        if (containsDelegateText != null) {
            containsDelegateText.setValue(str2);
            Log.i(TAG, "_saveTextColor: " + containsDelegateText.getValue());
        }
    }

    public void _saveEffectElementColor(Context context, String... strArr) {
        Template template = ((MainActivity) context).global.getTemplate();
        EffectElementColor effectElementColor = new EffectElementColor();
        effectElementColor.setPathNames(strArr);
        template.getEffectElementColors().add(effectElementColor);
    }

    public void _saveEffectElementColorValue(Context context, int i, String... strArr) {
        EffectElementColor containsElementColor = containsElementColor(((MainActivity) context).global.getTemplate().getEffectElementColors(), strArr);
        if (containsElementColor != null) {
            containsElementColor.setValue(i);
        }
    }

    public void _saveTextColor(Context context, String str, int i) {
        MetaEditText containsDelegateText = containsDelegateText(((MainActivity) context).global.getTemplate().getMetaEditTexts(), str);
        if (containsDelegateText != null) {
            containsDelegateText.setFontColor(i);
            Log.i(TAG, "_saveTextColor: " + containsDelegateText.getFontColor());
        }
    }

    public void _saveTextVisibility(Context context, String str, Boolean bool) {
        MetaEditText containsDelegateText = containsDelegateText(((MainActivity) context).global.getTemplate().getMetaEditTexts(), str);
        if (containsDelegateText != null) {
            containsDelegateText.setVisibility(bool);
            Log.i(TAG, "_saveTextVisibility: " + containsDelegateText.getVisibility());
        }
    }

    public void _savePosition(Context context, float f, float f2, float f3) {
        Template template = ((MainActivity) context).global.getTemplate();
        template.setEffectPosition(new EffectPosition(f, f2, f3));
        Log.i(TAG, "_savePosition:  x:" + template.getEffectPosition().getX() + " y: " + template.getEffectPosition().getY() + " scale: " + template.getEffectPosition().getScale());
    }

    public static MetaEditText containsDelegateText(List<MetaEditText> list, String str) {
        for (MetaEditText next : list) {
            if (next != null && next.getDelegateText().equals(str)) {
                return next;
            }
        }
        return null;
    }

    public static EffectElementColor containsElementColor(List<EffectElementColor> list, String... strArr) {
        for (EffectElementColor next : list) {
            if (next != null && Arrays.equals(next.getPathNames(), strArr)) {
                return next;
            }
        }
        return null;
    }
}
