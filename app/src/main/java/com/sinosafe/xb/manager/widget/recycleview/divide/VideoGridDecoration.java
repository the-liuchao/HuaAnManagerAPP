package com.sinosafe.xb.manager.widget.recycleview.divide;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 自定义分割线
 */
public class VideoGridDecoration extends RecyclerView.ItemDecoration {
    private int marginTop;
    private int marginBottom;
    private int marginLeftforL;
    private int marginLeftforR;
    private int marginRightforL;
    private int marginRightforR;

    public VideoGridDecoration(Context context) {
        //marginTop = context.getResources().getDimensionPixelSize(R.dimen.marginTop1);
        //marginBottom = context.getResources().getDimensionPixelSize(R.dimen.marginBottom);
        //marginLeftforL = context.getResources().getDimensionPixelSize(R.dimen.marginLeftforL);
       // marginLeftforR = context.getResources().getDimensionPixelSize(R.dimen.marginLeftforR);
       // marginRightforL = context.getResources().getDimensionPixelSize(R.dimen.marginRightforL);
        //marginRightforR = context.getResources().getDimensionPixelSize(R.dimen.marginRightforR);
    }

    @Override
    public void getItemOffsets(
            Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if(parent.getChildAdapterPosition(view) == 0){
            return;
        }
        if(parent.getChildAdapterPosition(view) % 2 == 1){
            outRect.set(marginLeftforL, marginTop, marginRightforL, marginBottom);
        } else {
            outRect.set(marginLeftforR, marginTop, marginRightforR, marginBottom);
        }

    }
}
