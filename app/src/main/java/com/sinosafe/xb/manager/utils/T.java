package com.sinosafe.xb.manager.utils;

import android.view.Gravity;
import android.widget.Toast;

import com.sinosafe.xb.manager.APP;

/**
 * 类名称：T.java <br>
 * 内容摘要： //Toast统一管理类。<br>
 * 属性描述：<br>
 * 方法描述：<br>
 * 修改备注： <br>
 * 创建时间： 2016年4月12日下午3:51:06<br>
 * 公司：深圳市华移科技股份有限公司<br>
 * 
 * @author Mr 张<br>
 */
public class T {
	// Toast
	private static Toast toast;

	/**
	 * 短时间显示Toast
	 * 在底部显示
	 * @param message
	 */
	public static void showShortBottom(CharSequence message) {
		try {
			if (null == toast) {
				toast = Toast.makeText(APP.getApplication(), message,
						Toast.LENGTH_SHORT);
			} else {
				toast.setText(message);
			}
			toast.show();
		} catch (Exception e) {

		}
	}

	/**
	 * 短时间显示Toast
	 * 
	 * @param message
	 */
	public static void showShort(int message) {
		if (null == toast) {
			toast = Toast.makeText(APP.getApplication(), message,
					Toast.LENGTH_SHORT);
		} else {
			toast.setText(message);
		}
		toast.show();
	}

	/**
	 * 短时间在屏幕中心显示Toast
	 * 
	 * @param message
	 */
	public static void showShortCenter(String message) {
		if (null == toast) {
			toast = Toast.makeText(APP.getApplication(), message,
					Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 50);
		} else {
			toast.setText(message);
		}
		toast.show();
	}

	/**
	 * 长时间显示Toast
	 * 
	 * @param message
	 */
	public static void showLong(CharSequence message) {
		if (null == toast) {
			toast = Toast.makeText(APP.getApplication(), message,
					Toast.LENGTH_LONG);
		} else {
			toast.setText(message);
		}
		toast.show();
	}

	/**
	 * 长时间显示Toast
	 * 
	 * @param message
	 */
	public static void showLong(int message) {
		if (null == toast) {
			toast = Toast.makeText(APP.getApplication(), message,
					Toast.LENGTH_LONG);
		} else {
			toast.setText(message);
		}
		toast.show();
	}

	/**
	 * 自定义显示Toast时间
	 * 
	 * @param message
	 * @param duration
	 */
	public static void show(CharSequence message, int duration) {
		if (null == toast) {
			toast = Toast.makeText(APP.getApplication(), message, duration);
		} else {
			toast.setText(message);
		}
		toast.show();
	}

	/**
	 * 自定义显示Toast时间
	 * 
	 * @param message
	 * @param duration
	 */
	public static void show(int message, int duration) {
		if (null == toast) {
			toast = Toast.makeText(APP.getApplication(), message, duration);
		} else {
			toast.setText(message);
		}
		toast.show();
	}

	/** Hide the toast, if any. */
	public static void hideToast() {
		if (null != toast) {
			toast.cancel();
		}
	}

}
