package luo.library.base.utils;

import android.content.Context;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;

public class CrashHandler implements UncaughtExceptionHandler {

    // 单例
    private static CrashHandler instance;

    private Context context = null;

    private CrashHandler() {
    }

    // 同步方法，以免单例多线程环境下出现异常
    public synchronized static CrashHandler getInstance() {
        if (instance == null) {
            instance = new CrashHandler();
        }
        return instance;
    }

    // 初始化，把当前对象设置成UncaughtExceptionHandler处理器
    public void init(Context ctx) {
        context = ctx;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 处理未捕获异常
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        StringWriter stackTrace = new StringWriter();
        ex.printStackTrace(new PrintWriter(stackTrace));

        MyLog.e("运行时异常："+stackTrace.toString());
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast toast = Toast.makeText(context, "抱歉，APP挂了！", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                Looper.loop();
            }
        }.start();
       android.os.Process.killProcess(android.os.Process.myPid());
    }
}
