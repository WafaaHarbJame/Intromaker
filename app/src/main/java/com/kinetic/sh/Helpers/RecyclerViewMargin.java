package com.kinetic.sh.Helpers;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewMargin extends RecyclerView.ItemDecoration {
    private final int columns;
    private final int margin;

    public RecyclerViewMargin(int i, int i2) {
        this.margin = i;
        this.columns = i2;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int childLayoutPosition = parent.getChildLayoutPosition(view);
        outRect.right = this.margin;
        outRect.bottom = this.margin;
        if (childLayoutPosition < this.columns) {
            outRect.top = this.margin;
        }
        if (childLayoutPosition % this.columns == 0) {
            outRect.left = this.margin;
        }
    }
}
