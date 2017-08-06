package luo.library.base.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

/**
 * @ClassName: DensityUtil
 * @Description: TODO(像素转换,获取屏幕宽高等信息、全屏切换、保持屏幕常亮、截屏等)
 * @author Mr 张
 * @date 2016-3-25 上午10:35:37
 * @company 深圳市华移科技股份有限公司
 */
public class DensityUtil {

	private static boolean isFullScreen = false;

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dp2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
	

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	
	
	
	/**
	 * 获取屏幕宽度和高度，单位为px
	 * @param context
	 * @return
	 */
	public static Point getScreenMetrics(Context context){
		DisplayMetrics dm =context.getResources().getDisplayMetrics();
		int w_screen = dm.widthPixels;
		int h_screen = dm.heightPixels;
		Log.e("hello", "Screen---Width = " + w_screen + " Height = " + h_screen + " densityDpi = " + dm.densityDpi);
		return new Point(w_screen, h_screen);
		
	}
	
	
	/**
	 * 获取屏幕长宽比
	 * @param context
	 * @return
	 */
	public static float getScreenRate(Context context){
		Point P = getScreenMetrics(context);
		float H = P.y;
		float W = P.x;
		return (H/W);
	}

	/**
	 * 获取屏幕的宽高 <br>
	 * DisplayMetrics.heightPixels<br>
	 * DisplayMetrics.widthPixels <br>
	 * 需要在manifest 中加上 适配<br>
	 * <supports-screens android:smallScreens="true"
	 * android:normalScreens="true" android:largeScreens="true"
	 * android:resizeable="true" android:anyDensity="true" /> <br>
	 * 
	 * @param context
	 **/
	public static DisplayMetrics getDisplayMetrics(Activity context) {
		DisplayMetrics dm = new DisplayMetrics();

		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm;
	}

	/** 获取状态栏的高度 */
	public static int getStatusBarHeight(Activity mActivity) {
		Resources resources = mActivity.getResources();
		int resourceId = resources.getIdentifier("status_bar_height", "dimen",
				"android");
		int height = resources.getDimensionPixelSize(resourceId);
		return height;
	}

	/**
	 * Display metrics display metrics.
	 * 
	 * @param context
	 *            the context
	 * @return the display metrics
	 */
	public static DisplayMetrics displayMetrics(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(dm);
		return dm;
	}

	/**
	 * Width pixels int.
	 * 
	 * @param context
	 *            the context
	 * @return the int
	 */
	public static int widthPixels(Context context) {
		return displayMetrics(context).widthPixels;
	}

	/**
	 * Height pixels int.
	 * 
	 * @param context
	 *            the context
	 * @return the int
	 */
	public static int heightPixels(Context context) {
		return displayMetrics(context).heightPixels;
	}

	/**
	 * Density float.
	 * 
	 * @param context
	 *            the context
	 * @return the float
	 */
	public static float density(Context context) {
		return displayMetrics(context).density;
	}

	/**
	 * Density dpi int.
	 * 
	 * @param context
	 *            the context
	 * @return the int
	 */
	public static int densityDpi(Context context) {
		return displayMetrics(context).densityDpi;
	}

	/**
	 * Is full screen boolean.
	 * 
	 * @return the boolean
	 */
	public static boolean isFullScreen() {
		return isFullScreen;
	}

	/**
	 * Toggle full displayMetrics.
	 * 
	 * @param activity
	 *            the activity
	 */
	public static void toggleFullScreen(Activity activity) {
		Window window = activity.getWindow();
		int flagFullscreen = WindowManager.LayoutParams.FLAG_FULLSCREEN;
		if (isFullScreen) {
			window.clearFlags(flagFullscreen);
			isFullScreen = false;
		} else {
			window.setFlags(flagFullscreen, flagFullscreen);
			isFullScreen = true;
		}
	}

	/**
	 * 保持屏幕常亮
	 * 
	 * @param activity
	 *            the activity
	 */
	public static void keepBright(Activity activity) {
		// 需在setContentView前调用
		int keepScreenOn = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
		activity.getWindow().setFlags(keepScreenOn, keepScreenOn);
	}
}
