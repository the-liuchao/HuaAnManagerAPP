package luo.library.base.utils;

import android.util.Log;

/**
 * 类名称：   com.cnmobi.device.streamer.utils
 * 内容摘要： //打印log。
 * 修改备注：
 * 创建时间： 2017/5/15 0015
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class MyLog {

    private static final String TAG = "TEST";
    private static boolean enableLog = true;

    public MyLog() {
    }

    public static void enableLog(boolean enable) {
        enableLog = enable;
    }

    public static void v(String msg) {
        if(enableLog) {
            Log.v(TAG," Log : " + msg);
           // LogUtil.writeLog("Streamer-->" + cls.getSimpleName() + " , Log : " + msg);
        }

    }


    public static void d(String msg) {
        if(enableLog) {
            Log.d(TAG , " Log : " + msg);
            //LogUtil.writeLog("Streamer-->" + cls.getSimpleName() + " , Log : " + msg);
        }

    }


    public static void i( String msg) {
        if(enableLog) {
            Log.i(TAG," Log : " + msg);
            //LogUtil.writeLog("Streamer-->" + cls.getSimpleName() + " , Log : " + msg);
        }

    }



    public static void w(String msg) {
        if(enableLog) {
            Log.w(TAG, " Log : " + msg);
           // LogUtil.writeLog("Streamer-->" + cls.getSimpleName() + " , Log : " + msg);
        }

    }

    public static void e( String msg) {
        if(enableLog) {
            Log.e(TAG , " Log : " + msg);
           // LogUtil.writeLog("Streamer-->" + cls.getSimpleName() + " , Log : " + msg);
        }

    }
}
