package com.sinosafe.xb.manager.widget.recycleview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 *
 */
public class VideoScrollView extends ScrollView {
    private boolean mIsIntercept;

    public VideoScrollView(Context context) {
        super(context);
    }

    public VideoScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(mIsIntercept){
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    public void setEvent(boolean isIntercept){
        mIsIntercept = isIntercept;
    }
}
