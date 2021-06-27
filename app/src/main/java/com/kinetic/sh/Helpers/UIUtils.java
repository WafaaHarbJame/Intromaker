package com.kinetic.sh.Helpers;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class UIUtils {
    public static boolean setListViewHeightBasedOnItems(ListView listView) {
        ListAdapter adapter = listView.getAdapter();
        if (adapter == null) {
            return false;
        }
        int count = adapter.getCount();
        int i = 0;
        for (int i2 = 0; i2 < count; i2++) {
            View view = adapter.getView(i2, (View) null, listView);
            view.measure(0, 0);
            i += view.getMeasuredHeight();
        }
        int dividerHeight = listView.getDividerHeight() * (count - 1);
        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        layoutParams.height = i + dividerHeight;
        listView.setLayoutParams(layoutParams);
        listView.requestLayout();
        return true;
    }
}
