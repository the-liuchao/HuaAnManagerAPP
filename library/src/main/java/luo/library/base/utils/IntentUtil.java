package luo.library.base.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import luo.library.R;

/**
 * 类名称：   IntentUtil
 * 内容摘要： Intent辅助类，activity的启动跳转
 * 修改备注：
 * 创建时间： 2017/5/10
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class IntentUtil {

    /**
     * 方法名：  gotoActivity	<br>
     * 方法描述：TODO(跳转至指定activity)<br>
     * 修改备注：<br>
     * 创建时间： 2016-1-5下午6:57:32<br>
     * @param context
     * @param gotoClass
     */
    public static void gotoActivity(Context context, Class<?> gotoClass) {
        Intent intent = new Intent();
        intent.setClass(context, gotoClass);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
    }

    /**
     * 方法名：  gotoActivityToTopAndFinish	<br>
     * 方法描述：TODO(单例模式跳转至指定activity)<br>
     * 修改备注：<br>
     * 创建时间： 2016-1-5下午6:57:49<br>
     * @param context
     * @param gotoClass
     */
    public static void gotoActivityToTopAndFinish(Context context, Class<?> gotoClass) {
        Intent intent = new Intent();
        intent.setClass(context, gotoClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        ((Activity) context).finish();
        ((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
    }


    /**
     * 方法名：  gotoActivityToTopAndFinish	<br>
     * 方法描述：TODO(单例模式跳转并携带数据)<br>
     * 修改备注：<br>
     * 创建时间： 2016-1-5下午6:58:05<br>
     * @param context
     * @param gotoClass
     * @param bundle
     */
    public static void gotoActivityToTopAndFinish(Context context, Class<?> gotoClass, Bundle bundle) {
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setClass(context, gotoClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        ((Activity) context).finish();
        ((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
    }


    /**
     * 方法名：  gotoActivity	<br>
     * 方法描述：TODO(携带传递数据跳转至指定activity.)<br>
     * 修改备注：<br>
     * 创建时间： 2016-1-5下午6:58:20<br>
     * @param context
     * @param gotoClass
     * @param bundle
     */
    public static void gotoActivity(Context context, Class<?> gotoClass, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(context, gotoClass);
        intent.putExtras(bundle);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
    }

    /**
     * 方法名：  gotoActivityForResult	<br>
     * 方法描述：TODO(跳转至指定activity)<br>
     * 修改备注：<br>
     * 创建时间： 2016-1-5下午6:58:37<br>
     * @param context
     * @param gotoClass
     * @param requestCode
     */
    public static void gotoActivityForResult(Context context, Class<?> gotoClass, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(context, gotoClass);
        ((Activity) context).startActivityForResult(intent, requestCode);
        ((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
    }

    /**
     * 方法名：  gotoActivityForResult	<br>
     * 方法描述：TODO(携带传递数据跳转至指定activity.)<br>
     * 修改备注：<br>
     * 创建时间： 2016-1-5下午6:58:56<br>
     * @param context
     * @param gotoClass
     * @param bundle
     * @param requestCode
     */
    public static void gotoActivityForResult(Context context, Class<?> gotoClass, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(context, gotoClass);
        intent.putExtras(bundle);
        ((Activity) context).startActivityForResult(intent, requestCode);
        ((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
    }


    /**
     * 方法名：  gotoActivityAndFinish	<br>
     * 方法描述：TODO(跳转至指定activity,并关闭当前activity.)<br>
     * 修改备注：<br>
     * 创建时间： 2016-1-5下午6:59:11<br>
     * @param context
     * @param gotoClass
     */
    public static void gotoActivityAndFinish(Context context, Class<?> gotoClass) {
        Intent intent = new Intent();
        intent.setClass(context, gotoClass);
        context.startActivity(intent);
        ((Activity) context).finish();
        ((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
    }


    /**
     * 方法名：  gotoActivityAndFinish	<br>
     * 方法描述：TODO(携带传递数据跳转至指定activity,并关闭当前activity.)<br>
     * 修改备注：<br>
     * 创建时间： 2016-1-5下午6:59:25<br>
     * @param context
     * @param gotoClass
     * @param bundle
     */
    public static void gotoActivityAndFinish(Context context, Class<?> gotoClass, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(context, gotoClass);
        intent.putExtras(bundle);
        context.startActivity(intent);
        ((Activity) context).finish();
        ((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
    }


    /**
     * 方法名：  gotoActivityToTop	<br>
     * 方法描述：TODO(携带传递数据跳转至指定activity,并关闭当前activity.)<br>
     * 修改备注：<br>
     * 创建时间： 2016-1-5下午6:59:43<br>
     * @param context
     * @param gotoClass
     */
    public static void gotoActivityToTop(Context context, Class<?> gotoClass) {
        Intent intent = new Intent();
        intent.setClass(context, gotoClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
    }


    /**
     * 方法名：  gotoActivityToTop	<br>
     * 方法描述：TODO(携带传递数据跳转至指定activity,并关闭当前activity.)<br>
     * 修改备注：<br>
     * 创建时间： 2016-1-5下午7:00:00<br>
     * @param context
     * @param gotoClass
     * @param bundle
     */
    public static void gotoActivityToTop(Context context, Class<?> gotoClass, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(context, gotoClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtras(bundle);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
    }




    /**
     * 方法名：  finish	<br>
     * 方法描述：TODO(关闭某个activity)<br>
     * 修改备注：<br>
     * 创建时间： 2016-1-5下午7:00:36<br>
     * @param activity
     */
    public static void finish(Activity activity) {
        activity.finish();
        //activity.overridePendingTransition(R.anim.exit_enter, R.anim.exit_exit);
    }
}

