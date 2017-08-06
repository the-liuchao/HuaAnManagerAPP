package luo.library.base.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
/**
 * 类名称：   luo.library.base.widget
 * 内容摘要： //SwipeRefreshLayout和ViewPager左右滑动冲突的原因以及正确的解决方法。
 * 修改备注：
 * 创建时间： 2017/6/4 0004
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class SwipeRefreshLayoutCompat extends SwipeRefreshLayout {

    //重写SwipeRefreshLayout的onIntercept方法,判断用户是横向滑动还是纵向滑动，
    // 如果是横向滑动自己不处理事件，交给其他控件，如果是纵向滑动就拦截事件，自己处理

    // 是否存在左右滑动事件
    private boolean mDragger;
    // 记录手指按下的位置
    private float mStartY, mStartX;
    // 出发事件的最短距离
    private int mTouchSlop;

    public SwipeRefreshLayoutCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 记录手指按下的位置
                mStartY = ev.getY();
                mStartX = ev.getX();
                //初始化左右滑动事件为false
                mDragger = false;
                break;
            case MotionEvent.ACTION_MOVE:
                //如果左右滑动事件为true  直接返回false 不拦截事件
                if (mDragger) {
                    return false;
                }

                // 获取当前手指位置
                float endY = ev.getY();
                float endX = ev.getX();
                //获取X,Y滑动距离的绝对值
                float distanceX = Math.abs(endX - mStartX);
                float distanceY = Math.abs(endY - mStartY);

                // 如果X轴位移大于Y轴距离，那么将事件交给其他控件
                if (distanceX > mTouchSlop && distanceX > distanceY) {
                    mDragger = true;
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                //初始化左右滑动事件为false
                mDragger = false;
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
