package com.sinosafe.xb.manager.adapter.base;

import android.view.View;

/**
 * Created by Administrator on 2016/1/5 0005.
 */
public interface OnItemLongClickListener<T> {
    void onLongClick(View view, int position, T item);
}
