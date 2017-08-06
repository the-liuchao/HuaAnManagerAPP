package com.sinosafe.xb.manager.widget.recycleview.divide;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 自定义分割线
 */

public class OrderDecoration extends RecyclerView.ItemDecoration {
    private int marginTop;
    private int marginBottom;
    private int marginLeftforL;
    private int marginLeftforR;
    private int marginRightforL;
    private int marginRightforR;

    public OrderDecoration(Context context) {
        //marginTop = context.getResources().getDimensionPixelSize(R.dimen.marginTop);
       // marginBottom = context.getResources().getDimensionPixelSize(R.dimen.marginBottom);
       // marginLeftforL = context.getResources().getDimensionPixelSize(R.dimen.marginLeftforL);
        //marginLeftforR = context.getResources().getDimensionPixelSize(R.dimen.marginLeftforR);
        //marginRightforL = context.getResources().getDimensionPixelSize(R.dimen.marginRightforL);
        //marginRightforR = context.getResources().getDimensionPixelSize(R.dimen.marginRightforR);
    }

    @Override
    public void getItemOffsets(
            Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if(parent.getChildAdapterPosition(view) == parent.getChildCount()){
            return;
        }
        outRect.set(0, marginTop, 0, marginBottom);
    }
}
