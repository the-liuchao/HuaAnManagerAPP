package luo.library.base.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 类名称：   luo.library.base.widget
 * 内容摘要： //不可以滑动，但是可以setCurrentItem的ViewPager。
 * 修改备注：
 * 创建时间： 2017/4/11
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class NoScrollViewPager extends ViewPager {
    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return false;
    }
}
