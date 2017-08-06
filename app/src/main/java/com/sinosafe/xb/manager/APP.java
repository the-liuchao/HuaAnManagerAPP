package com.sinosafe.xb.manager;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.widget.ImageView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.loader.ImageLoader;
import com.lzy.imagepicker.view.CropImageView;
import com.umeng.socialize.PlatformConfig;

import org.xutils.x;

import java.io.File;

import cn.jpush.android.api.JPushInterface;
import luo.library.base.base.BaseAndroid;
import luo.library.base.base.BaseConfig;
import luo.library.base.base.BaseDb;
import luo.library.base.base.BaseImage;
import luo.library.base.utils.CrashHandler;


/**
 * Created by Administrator on 2017/1/6.
 */

public class APP extends Application {

    private static APP instance;
    //主activity对象
    private MainActivity mainActivity;
    //定位经纬度
    public static double lat = 0;
    public static double lng = 0;
    public static String cityName;
    public static String areaName;
    //传值
    public static Bundle bundle = new Bundle();

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //xutils初始化
        x.Ext.init(this);
        x.Ext.setDebug(true);
        BaseDb.initDb();
        //极光初始化
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush

        BaseAndroid.init(new BaseConfig()
                .setAppLogo(R.mipmap.ic_launcher)
                .setAppColor(R.color.colorPrimary)//app主调颜色，用于标题栏等背景颜色
                .setFailPicture(R.mipmap.ic_launcher)//加载加载失败和加载中显示的图
                .setCode(0)//网络请求成功返回的code数字，默认为1
                .setHttpCode("code")//网络请求返回的code字段名称，默认为code
                .setHttpMessage("msg")//网络请求返回的message字段名称，默认为message
                .setHttpResult("result"));//网络请求返回的result字段名称，默认为result
        //SDKInitializer.initialize(this);
        //第三方登录
        PlatformConfig.setWeixin("wxd6b3d31d3de1d1dc", "66d477bb61f7ace3a36fc79005d7b9c7");
        PlatformConfig.setQQZone("1106234724", "Lv4JV8ICxvUwjz3X");

        // 处理未捕获异常
        CrashHandler handler = CrashHandler.getInstance();
        handler.init(this);

        initImagePicker();
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 初始化多图选择器
     */
    private void initImagePicker(){
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new PicassoImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(false);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }

    public static APP getApplication() {
        return instance;
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    //加载显示图片
    public class PicassoImageLoader implements ImageLoader {
        @Override
        public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {

            BaseImage.getInstance().displayImage(instance,new File(path),imageView);
        }

        @Override
        public void clearMemoryCache() {
            //这里是清除缓存的方法,根据需要自己实现
        }
    }
}
