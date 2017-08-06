package com.sinosafe.xb.manager.widget.recycleview.divide;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * Created by cheny on 2016/12/5.
 */

public class ServiceListDecoration extends RecyclerView.ItemDecoration {
    private int marginTop;
    private int marginBottom;
    private int marginLeftforL;
    private int marginLeftforR;
    private int marginRightforL;
    private int marginRightforR;

    public ServiceListDecoration(Context context) {
       // marginTop = context.getResources().getDimensionPixelSize(R.dimen.marginTop2);
       // marginBottom = context.getResources().getDimensionPixelSize(R.dimen.marginBottom);
       // marginLeftforL = context.getResources().getDimensionPixelSize(R.dimen.marginLeftforL);
       // marginLeftforR = context.getResources().getDimensionPixelSize(R.dimen.marginLeftforR);
       // marginRightforL = context.getResources().getDimensionPixelSize(R.dimen.marginRightforL);
       // marginRightforR = context.getResources().getDimensionPixelSize(R.dimen.marginRightforR);
    }

    @Override
    public void getItemOffsets(
            Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        Log.e("gedkldk","parent.getChildCount()=="+parent.getChildCount());
//        Log.e("gedkldk","parent.getChildAdapterPosition(view)=="+parent.getChildAdapterPosition(view));
//        if(parent.getChildAdapterPosition(view) == parent.getChildCount()){ //为最后一个item留出底部间距
//            outRect.set(0, marginTop, 0, marginTop);
//            return;
//        }
        outRect.set(0, marginTop, 0, marginTop);

    }
}
