package luo.library.base.utils;

import android.annotation.TargetApi;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.Space;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import luo.library.R;

/**
 * 类名称：   com.cnmobi.jichengguang.utils
 * 内容摘要： //说明主要功能。
 * 修改备注：
 * 创建时间： 2017/5/11
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class SnackbarUtil {

    //设置Snackbar背景颜色
    private static final int color_info = 0XFF2094F3;
    private static final int color_confirm = 0XFF4CB04E;
    private static final int color_warning = 0XFFFEC005;
    private static final int color_danger = 0XFFF44336;
    //工具类当前持有的Snackbar实例
    private static WeakReference<Snackbar> snackbarWeakReference;

    private SnackbarUtil() {
        throw new RuntimeException("禁止无参创建实例");
    }


    public SnackbarUtil(@Nullable WeakReference<Snackbar> snackbarWeakReference) {
        this.snackbarWeakReference = snackbarWeakReference;
    }

    /**
     * 获取 mSnackbar
     *
     * @return
     */
    public Snackbar getSnackbar() {
        if (this.snackbarWeakReference != null && this.snackbarWeakReference.get() != null) {
            return this.snackbarWeakReference.get();
        } else {
            return null;
        }
    }

    /**
     * 初始化Snackbar实例
     * 展示时间:Snackbar.LENGTH_SHORT
     *
     * @param view
     * @param message
     * @return
     */
    public static SnackbarUtil Short(View view, String message) {
        return new SnackbarUtil(new WeakReference<Snackbar>(Snackbar.make(view, message, Snackbar.LENGTH_SHORT))).backColor(0XFF323232);
    }

    /**
     * 初始化Snackbar实例
     * 展示时间:Snackbar.LENGTH_LONG
     *
     * @param view
     * @param message
     * @return
     */
    public static SnackbarUtil Long(View view, String message) {
        return new SnackbarUtil(new WeakReference<Snackbar>(Snackbar.make(view, message, Snackbar.LENGTH_LONG))).backColor(0XFF323232);
    }

    /**
     * 初始化Snackbar实例
     * 展示时间:Snackbar.LENGTH_INDEFINITE
     *
     * @param view
     * @param message
     * @return
     */
    public static SnackbarUtil Indefinite(View view, String message) {
        return new SnackbarUtil(new WeakReference<Snackbar>(Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE))).backColor(0XFF323232);
    }

    /**
     * 初始化Snackbar实例
     * 展示时间:duration 毫秒
     *
     * @param view
     * @param message
     * @param duration 展示时长(毫秒)
     * @return
     */
    public static SnackbarUtil Custom(View view, String message, int duration) {
        return new SnackbarUtil(new WeakReference<Snackbar>(Snackbar.make(view, message, Snackbar.LENGTH_SHORT).setDuration(duration))).backColor(0XFF323232);
    }

    /**
     * 设置mSnackbar背景色为  color_info
     */
    public SnackbarUtil info() {
        Snackbar snackbar = getSnackbar();
        if (snackbar != null) {
            snackbar.getView().setBackgroundColor(color_info);
        }
        return new SnackbarUtil(snackbarWeakReference);
    }

    /**
     * 设置mSnackbar背景色为  color_confirm
     */
    public SnackbarUtil confirm() {
        Snackbar snackbar = getSnackbar();
        if (snackbar != null) {
            snackbar.getView().setBackgroundColor(color_confirm);
        }
        return new SnackbarUtil(snackbarWeakReference);
    }

    /**
     * 设置Snackbar背景色为   color_warning
     */
    public SnackbarUtil warning() {
        Snackbar snackbar = getSnackbar();
        if (snackbar != null) {
            snackbar.getView().setBackgroundColor(color_warning);
        }
        return new SnackbarUtil(snackbarWeakReference);
    }

    /**
     * 设置Snackbar背景色为   color_warning
     */
    public SnackbarUtil danger() {
        Snackbar snackbar = getSnackbar();
        if (snackbar != null) {
            snackbar.getView().setBackgroundColor(color_danger);
        }
        return new SnackbarUtil(snackbarWeakReference);
    }

    /**
     * 设置Snackbar背景色
     *
     * @param backgroundColor
     */
    public SnackbarUtil backColor(@ColorInt int backgroundColor) {
        Snackbar snackbar = getSnackbar();
        if (snackbar != null) {
            snackbar.getView().setBackgroundColor(backgroundColor);
        }
        return new SnackbarUtil(snackbarWeakReference);
    }

    /**
     * 设置TextView(@+id/snackbar_text)的文字颜色
     *
     * @param messageColor
     */
    public SnackbarUtil messageColor(@ColorInt int messageColor) {
        Snackbar snackbar = getSnackbar();
        if (snackbar != null) {
            ((TextView) snackbar.getView().findViewById(R.id.snackbar_text)).setTextColor(messageColor);
        }
        return new SnackbarUtil(snackbarWeakReference);
    }

    /**
     * 设置Button(@+id/snackbar_action)的文字颜色
     *
     * @param actionTextColor
     */
    public SnackbarUtil actionColor(@ColorInt int actionTextColor) {
        Snackbar snackbar = getSnackbar();
        if (snackbar != null) {
            ((Button) snackbar.getView().findViewById(R.id.snackbar_action)).setTextColor(actionTextColor);
        }
        return new SnackbarUtil(snackbarWeakReference);
    }

    /**
     * 设置   Snackbar背景色 + TextView(@+id/snackbar_text)的文字颜色 + Button(@+id/snackbar_action)的文字颜色
     *
     * @param backgroundColor
     * @param messageColor
     * @param actionTextColor
     */
    public SnackbarUtil colors(@ColorInt int backgroundColor, @ColorInt int messageColor, @ColorInt int actionTextColor) {
        Snackbar snackbar = getSnackbar();
        if (snackbar != null) {
            snackbar.getView().setBackgroundColor(backgroundColor);
            ((TextView) snackbar.getView().findViewById(R.id.snackbar_text)).setTextColor(messageColor);
            ((Button) snackbar.getView().findViewById(R.id.snackbar_action)).setTextColor(actionTextColor);
        }
        return new SnackbarUtil(snackbarWeakReference);
    }

    /**
     * 设置Snackbar 背景透明度
     *
     * @param alpha
     * @return
     */
    public SnackbarUtil alpha(float alpha) {
        Snackbar snackbar = getSnackbar();
        if (snackbar != null) {
            alpha = alpha >= 1.0f ? 1.0f : (alpha <= 0.0f ? 0.0f : alpha);
            snackbar.getView().setAlpha(alpha);
        }
        return new SnackbarUtil(snackbarWeakReference);
    }

    /**
     * 设置Snackbar显示的位置
     *
     * @param gravity
     */
    public SnackbarUtil gravityFrameLayout(int gravity) {
        Snackbar snackbar = getSnackbar();
        if (snackbar != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(snackbarWeakReference.get().getView().getLayoutParams().width, snackbarWeakReference.get().getView().getLayoutParams().height);
            params.gravity = gravity;
            snackbar.getView().setLayoutParams(params);
        }
        return new SnackbarUtil(snackbarWeakReference);
    }

    /**
     * 设置Snackbar显示的位置,当Snackbar和CoordinatorLayout组合使用的时候
     *
     * @param gravity
     */
    public SnackbarUtil gravityCoordinatorLayout(int gravity) {
        Snackbar snackbar = getSnackbar();
        if (snackbar != null) {
            CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(snackbarWeakReference.get().getView().getLayoutParams().width, snackbarWeakReference.get().getView().getLayoutParams().height);
            params.gravity = gravity;
            snackbar.getView().setLayoutParams(params);
        }
        return new SnackbarUtil(snackbarWeakReference);
    }

    /**
     * 设置按钮文字内容 及 点击监听
     * {@link Snackbar#setAction(CharSequence, View.OnClickListener)}
     *
     * @param resId
     * @param listener
     * @return
     */
    public SnackbarUtil setAction(@StringRes int resId, View.OnClickListener listener) {
        Snackbar snackbar = getSnackbar();
        if (snackbar != null) {
            return setAction(snackbar.getView().getResources().getText(resId), listener);
        } else {
            return new SnackbarUtil(snackbarWeakReference);
        }
    }

    /**
     * 设置按钮文字内容 及 点击监听
     * {@link Snackbar#setAction(CharSequence, View.OnClickListener)}
     *
     * @param text
     * @param listener
     * @return
     */
    public SnackbarUtil setAction(CharSequence text, View.OnClickListener listener) {
        Snackbar snackbar = getSnackbar();
        if (snackbar != null) {
            snackbar.setAction(text, listener);
        }
        return new SnackbarUtil(snackbarWeakReference);
    }

    /**
     * 设置 mSnackbar 展示完成 及 隐藏完成 的监听
     *
     * @param setCallback
     * @return
     */
    public SnackbarUtil setCallback(Snackbar.Callback setCallback) {
        Snackbar snackbar = getSnackbar();
        if (snackbar != null) {
            snackbar.setCallback(setCallback);
        }
        return new SnackbarUtil(snackbarWeakReference);
    }

    /**
     * 设置TextView(@+id/snackbar_text)左右两侧的图片
     *
     * @param leftDrawable
     * @param rightDrawable
     * @return
     */
    public SnackbarUtil leftAndRightDrawable(@Nullable @DrawableRes Integer leftDrawable, @Nullable @DrawableRes Integer rightDrawable) {
        Snackbar snackbar = getSnackbar();
        if (snackbar != null) {
            Drawable drawableLeft = null;
            Drawable drawableRight = null;
            if (leftDrawable != null) {
                try {
                    drawableLeft = snackbar.getView().getResources().getDrawable(leftDrawable.intValue());
                } catch (Exception e) {
                }
            }
            if (rightDrawable != null) {
                try {
                    drawableRight = snackbar.getView().getResources().getDrawable(rightDrawable.intValue());
                } catch (Exception e) {
                }
            }
            return leftAndRightDrawable(drawableLeft, drawableRight);
        } else {
            return new SnackbarUtil(snackbarWeakReference);
        }
    }

    /**
     * 设置TextView(@+id/snackbar_text)左右两侧的图片
     *
     * @param leftDrawable
     * @param rightDrawable
     * @return
     */
    public SnackbarUtil leftAndRightDrawable(@Nullable Drawable leftDrawable, @Nullable Drawable rightDrawable) {
        Snackbar snackbar = getSnackbar();
        if (snackbar != null) {
            TextView message = (TextView) snackbar.getView().findViewById(R.id.snackbar_text);
            LinearLayout.LayoutParams paramsMessage = (LinearLayout.LayoutParams) message.getLayoutParams();
            paramsMessage = new LinearLayout.LayoutParams(paramsMessage.width, paramsMessage.height, 0.0f);
            message.setLayoutParams(paramsMessage);
            message.setCompoundDrawablePadding(message.getPaddingLeft());
            int textSize = (int) message.getTextSize();
            Log.e("Jet", "textSize:" + textSize);
            if (leftDrawable != null) {
                leftDrawable.setBounds(0, 0, textSize, textSize);
            }
            if (rightDrawable != null) {
                rightDrawable.setBounds(0, 0, textSize, textSize);
            }
            message.setCompoundDrawables(leftDrawable, null, rightDrawable, null);
            LinearLayout.LayoutParams paramsSpace = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
            ((Snackbar.SnackbarLayout) snackbar.getView()).addView(new Space(snackbar.getView().getContext()), 1, paramsSpace);
        }
        return new SnackbarUtil(snackbarWeakReference);
    }

    /**
     * 设置TextView(@+id/snackbar_text)中文字的对齐方式 居中
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public SnackbarUtil messageCenter() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Snackbar snackbar = getSnackbar();
            if (snackbar != null) {
                TextView message = (TextView) snackbar.getView().findViewById(R.id.snackbar_text);
                //View.setTextAlignment需要SDK>=17
                message.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
                message.setGravity(Gravity.CENTER);
            }
        }
        return new SnackbarUtil(snackbarWeakReference);
    }

    /**
     * 设置TextView(@+id/snackbar_text)中文字的对齐方式 居右
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public SnackbarUtil messageRight() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Snackbar snackbar = getSnackbar();
            if (snackbar != null) {
                TextView message = (TextView) snackbar.getView().findViewById(R.id.snackbar_text);
                //View.setTextAlignment需要SDK>=17
                message.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
                message.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
            }
        }
        return new SnackbarUtil(snackbarWeakReference);
    }

    /**
     * 向Snackbar布局中添加View(Google不建议,复杂的布局应该使用DialogFragment进行展示)
     *
     * @param layoutId 要添加的View的布局文件ID
     * @param index
     * @return
     */
    public SnackbarUtil addView(int layoutId, int index) {
        Snackbar snackbar = getSnackbar();
        if (snackbar != null) {
            //加载布局文件新建View
            View addView = LayoutInflater.from(snackbar.getView().getContext()).inflate(layoutId, null);
            return addView(addView, index);
        } else {
            return new SnackbarUtil(snackbarWeakReference);
        }
    }

    /**
     * 向Snackbar布局中添加View(Google不建议,复杂的布局应该使用DialogFragment进行展示)
     *
     * @param addView
     * @param index
     * @return
     */
    public SnackbarUtil addView(View addView, int index) {
        Snackbar snackbar = getSnackbar();
        if (snackbar != null) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);//设置新建布局参数
            //设置新建View在Snackbar内垂直居中显示
            params.gravity = Gravity.CENTER_VERTICAL;
            addView.setLayoutParams(params);
            ((Snackbar.SnackbarLayout) snackbar.getView()).addView(addView, index);
        }
        return new SnackbarUtil(snackbarWeakReference);
    }

    /**
     * 设置Snackbar布局的外边距
     * 注:经试验发现,调用margins后再调用 gravityFrameLayout,则margins无效.
     * 为保证margins有效,应该先调用 gravityFrameLayout,在 show() 之前调用 margins
     *
     * @param margin
     * @return
     */
    public SnackbarUtil margins(int margin) {
        Snackbar snackbar = getSnackbar();
        if (snackbar != null) {
            return margins(margin, margin, margin, margin);
        } else {
            return new SnackbarUtil(snackbarWeakReference);
        }
    }

    /**
     * 设置Snackbar布局的外边距
     * 注:经试验发现,调用margins后再调用 gravityFrameLayout,则margins无效.
     * 为保证margins有效,应该先调用 gravityFrameLayout,在 show() 之前调用 margins
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @return
     */
    public SnackbarUtil margins(int left, int top, int right, int bottom) {
        Snackbar snackbar = getSnackbar();
        if (snackbar != null) {
            ViewGroup.LayoutParams params = snackbar.getView().getLayoutParams();
            ((ViewGroup.MarginLayoutParams) params).setMargins(left, top, right, bottom);
            snackbar.getView().setLayoutParams(params);
        }
        return new SnackbarUtil(snackbarWeakReference);
    }

    /**
     * 经试验发现:
     *      执行过{@link SnackbarUtil#backColor(int)}后:background instanceof ColorDrawable
     *      未执行过{@link SnackbarUtil#backColor(int)}:background instanceof GradientDrawable
     * @return
     */
    /*
    public SnackbarUtil radius(){
        Drawable background = snackbarWeakReference.get().getView().getBackground();
        if(background instanceof GradientDrawable){
            Log.e("Jet","radius():GradientDrawable");
        }
        if(background instanceof ColorDrawable){
            Log.e("Jet","radius():ColorDrawable");
        }
        if(background instanceof StateListDrawable){
            Log.e("Jet","radius():StateListDrawable");
        }
        Log.e("Jet","radius()background:"+background.getClass().getSimpleName());
        return new SnackbarUtil(mSnackbar);
    }
    */

    /**
     * 通过SnackBar现在的背景,获取其设置圆角值时候所需的GradientDrawable实例
     *
     * @param backgroundOri
     * @return
     */
    private GradientDrawable getRadiusDrawable(Drawable backgroundOri) {
        GradientDrawable background = null;
        if (backgroundOri instanceof GradientDrawable) {
            background = (GradientDrawable) backgroundOri;
        } else if (backgroundOri instanceof ColorDrawable) {
            int backgroundColor = ((ColorDrawable) backgroundOri).getColor();
            background = new GradientDrawable();
            background.setColor(backgroundColor);
        } else {
        }
        return background;
    }

    /**
     * 设置Snackbar布局的圆角半径值
     *
     * @param radius 圆角半径
     * @return
     */
    public SnackbarUtil radius(float radius) {
        Snackbar snackbar = getSnackbar();
        if (snackbar != null) {
            //将要设置给mSnackbar的背景
            GradientDrawable background = getRadiusDrawable(snackbar.getView().getBackground());
            if (background != null) {
                radius = radius <= 0 ? 12 : radius;
                background.setCornerRadius(radius);
                snackbar.getView().setBackgroundDrawable(background);
            }
        }
        return new SnackbarUtil(snackbarWeakReference);
    }

    /**
     * 设置Snackbar布局的圆角半径值及边框颜色及边框宽度
     *
     * @param radius
     * @param strokeWidth
     * @param strokeColor
     * @return
     */
    public SnackbarUtil radius(int radius, int strokeWidth, @ColorInt int strokeColor) {
        Snackbar snackbar = getSnackbar();
        if (snackbar != null) {
            //将要设置给mSnackbar的背景
            GradientDrawable background = getRadiusDrawable(snackbar.getView().getBackground());
            if (background != null) {
                radius = radius <= 0 ? 12 : radius;
                strokeWidth = strokeWidth <= 0 ? 1 : (strokeWidth >= snackbar.getView().findViewById(R.id.snackbar_text).getPaddingTop() ? 2 : strokeWidth);
                background.setCornerRadius(radius);
                background.setStroke(strokeWidth, strokeColor);
                snackbar.getView().setBackgroundDrawable(background);
            }
        }
        return new SnackbarUtil(snackbarWeakReference);
    }

    /**
     * 计算单行的Snackbar的高度值(单位 pix)
     *
     * @return
     */
    private int calculateSnackBarHeight() {
        int SnackbarHeight = ScreenUtil.dp2px(snackbarWeakReference.get().getView().getContext(), 28) + ScreenUtil.sp2px(snackbarWeakReference.get().getView().getContext(), 14);
        Log.e("Jet", "直接获取MessageView高度:" + snackbarWeakReference.get().getView().findViewById(R.id.snackbar_text).getHeight());
        return SnackbarHeight;
    }

    /**
     * 设置Snackbar显示在指定View的上方
     * 注:暂时仅支持单行的Snackbar,因为{@link SnackbarUtil#calculateSnackBarHeight()}暂时仅支持单行Snackbar的高度计算
     *
     * @param targetView     指定View
     * @param contentViewTop Activity中的View布局区域 距离屏幕顶端的距离
     * @param marginLeft     左边距
     * @param marginRight    右边距
     * @return
     */
    public SnackbarUtil above(View targetView, int contentViewTop, int marginLeft, int marginRight) {
        Snackbar snackbar = getSnackbar();
        if (snackbar != null) {
            marginLeft = marginLeft <= 0 ? 0 : marginLeft;
            marginRight = marginRight <= 0 ? 0 : marginRight;
            int[] locations = new int[2];
            targetView.getLocationOnScreen(locations);
            Log.e("Jet", "距离屏幕左侧:" + locations[0] + "==距离屏幕顶部:" + locations[1]);
            int snackbarHeight = calculateSnackBarHeight();
            Log.e("Jet", "Snackbar高度:" + snackbarHeight);
            //必须保证指定View的顶部可见 且 单行Snackbar可以完整的展示
            if (locations[1] >= contentViewTop + snackbarHeight) {
                gravityFrameLayout(Gravity.BOTTOM);
                ViewGroup.LayoutParams params = snackbar.getView().getLayoutParams();
                ((ViewGroup.MarginLayoutParams) params).setMargins(marginLeft, 0, marginRight, snackbar.getView().getResources().getDisplayMetrics().heightPixels - locations[1]);
                snackbar.getView().setLayoutParams(params);
            }
        }
        return new SnackbarUtil(snackbarWeakReference);
    }

    /**
     * 设置Snackbar显示在指定View的下方
     * 注:暂时仅支持单行的Snackbar,因为{@link SnackbarUtil#calculateSnackBarHeight()}暂时仅支持单行Snackbar的高度计算
     *
     * @param targetView     指定View
     * @param contentViewTop Activity中的View布局区域 距离屏幕顶端的距离
     * @param marginLeft     左边距
     * @param marginRight    右边距
     * @return
     */
    public SnackbarUtil bellow(View targetView, int contentViewTop, int marginLeft, int marginRight) {
        Snackbar snackbar = getSnackbar();
        if (snackbar != null) {
            marginLeft = marginLeft <= 0 ? 0 : marginLeft;
            marginRight = marginRight <= 0 ? 0 : marginRight;
            int[] locations = new int[2];
            targetView.getLocationOnScreen(locations);
            int snackbarHeight = calculateSnackBarHeight();
            int screenHeight = ScreenUtil.getScreenHeight(snackbar.getView().getContext());
            //必须保证指定View的底部可见 且 单行Snackbar可以完整的展示
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //为什么要'+2'? 因为在Android L(Build.VERSION_CODES.LOLLIPOP)以上,例如Button会有一定的'阴影(shadow)',阴影的大小由'高度(elevation)'决定.
                //为了在Android L以上的系统中展示的Snackbar不要覆盖targetView的阴影部分太大比例,所以人为减小2px的layout_marginBottom属性.
                if (locations[1] + targetView.getHeight() >= contentViewTop && locations[1] + targetView.getHeight() + snackbarHeight + 2 <= screenHeight) {
                    gravityFrameLayout(Gravity.BOTTOM);
                    ViewGroup.LayoutParams params = snackbar.getView().getLayoutParams();
                    ((ViewGroup.MarginLayoutParams) params).setMargins(marginLeft, 0, marginRight, screenHeight - (locations[1] + targetView.getHeight() + snackbarHeight + 2));
                    snackbar.getView().setLayoutParams(params);
                }
            } else {
                if (locations[1] + targetView.getHeight() >= contentViewTop && locations[1] + targetView.getHeight() + snackbarHeight <= screenHeight) {
                    gravityFrameLayout(Gravity.BOTTOM);
                    ViewGroup.LayoutParams params = snackbar.getView().getLayoutParams();
                    ((ViewGroup.MarginLayoutParams) params).setMargins(marginLeft, 0, marginRight, screenHeight - (locations[1] + targetView.getHeight() + snackbarHeight));
                    snackbar.getView().setLayoutParams(params);
                }
            }
        }
        return new SnackbarUtil(snackbarWeakReference);
    }


    /**
     * 显示 mSnackbar
     */
    public void show() {
        if (getSnackbar() != null) {
            snackbarWeakReference.get().show();
        } else {
        }
    }
}
