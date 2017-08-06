package com.sinosafe.xb.manager.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.sinosafe.xb.manager.APP;

import luo.library.base.base.BaseFragmentActivity;
import luo.library.base.utils.AMapLocUtils;
import luo.library.base.utils.MyLog;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 类名称：   com.sinosafe.xb.manager.utils
 * 内容摘要： //定位工具。
 * 修改备注：
 * 创建时间： 2017/6/29 0029
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class MyAMapLocUtils {

    protected static final int LOCATION_PERMISSION = 0x0100;//定位权限
    private Context mContext;
    protected AMapLocUtils aMapLocUtils;

    public MyAMapLocUtils(Context mContext) {
        this.mContext = mContext;
        initMapLocUtils();
    }

    private void initMapLocUtils(){
        //定位
        aMapLocUtils = new AMapLocUtils(mContext, new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if(mContext instanceof BaseFragmentActivity)
                    ((BaseFragmentActivity)(mContext)).closeSVProgressHUD();
                if (null != aMapLocation&&aMapLocation.getLatitude()!=0) {
                    APP.lat = aMapLocation.getLatitude();
                    APP.lng = aMapLocation.getLongitude();
                    APP.cityName = aMapLocation.getCity();
                    APP.areaName = aMapLocation.getDistrict();
                    MyLog.e("定位详情：APP.lat="+APP.lat+"     APP.lng="+APP.lng+"        APP.cityName="+APP.cityName+"     APP.areaName="+APP.areaName);
                } else
                    T.showShortBottom("定位失败，请打开位置权限");
            }
        });
    }

    /**
     * 开始定位
     */
    public void startLocation(){
        requestLocationPermission();
    }

    /**
     * 停止定位
     */
    public void stopLocation(){
        aMapLocUtils.stopLocation();
    }

    /**
     * 定位权限
     */
    @AfterPermissionGranted(LOCATION_PERMISSION)
    private void requestLocationPermission() {

        if (EasyPermissions.hasPermissions(mContext, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_PHONE_STATE)) {
            aMapLocUtils.location();
        } else {
            EasyPermissions.requestPermissions((Activity)mContext, "需要定位权限", LOCATION_PERMISSION,
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.READ_PHONE_STATE);
        }
    }

}
