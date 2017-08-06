package com.sinosafe.xb.manager.utils;

import android.os.Handler;
import android.os.Message;

import com.megvii.idcardquality.IDCardQualityLicenseManager;
import com.megvii.licensemanager.Manager;
import com.megvii.livenessdetection.LivenessLicenseManager;
import com.megvii.livenesslib.util.ConUtil;
import com.sinosafe.xb.manager.APP;

import luo.library.base.utils.MyLog;

/**
 * 类名称：   com.sinosafe.xb.manager.utils
 * 内容摘要： //人脸识别 联网授权工具类。
 * 修改备注：
 * 创建时间： 2017/8/1 0001
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class MyFaceNetWorkWarranty {

    private NetWorkWarrantyCallback netWorkWarrantyCallback;

    /**
     * 判断人脸识别授权是否通过
     * @return
     */
    public static boolean isFaceNetWorkWarranty(){

        if(APP.getApplication().getMainActivity()==null){
            return false;
        }

        if(APP.getApplication().getMainActivity().getSp("faceNetWorkWarranty","")==null
                ||APP.getApplication().getMainActivity().getSp("faceNetWorkWarranty","").equals("")){

            return false;
        }
        return true;
    }


    /**
     * 判断身份证授权是否通过
     * @return
     */
    public static boolean isIdCardNetWorkWarranty(){

        if(APP.getApplication().getMainActivity()==null){
            return false;
        }
        if(APP.getApplication().getMainActivity().getSp("idCardNetWorkWarranty","")==null
                ||APP.getApplication().getMainActivity().getSp("idCardNetWorkWarranty","").equals("")){

            return false;
        }
        return true;
    }

    /**
     * 人脸识别 联网授权
     */
    public void faceNetWorkWarranty(NetWorkWarrantyCallback netWorkWarrantyCallback) {
        this.netWorkWarrantyCallback = netWorkWarrantyCallback;
        new Thread(new Runnable() {
            @Override
            public void run() {
                String uuid = ConUtil.getUUIDString(APP.getApplication().getMainActivity());
                Manager manager = new Manager(APP.getApplication().getMainActivity());
                LivenessLicenseManager licenseManager = new LivenessLicenseManager(APP.getApplication().getMainActivity());
                manager.registerLicenseManager(licenseManager);
                try {
                    manager.takeLicenseFromNetwork(uuid);
                }catch (Exception e){
                }
                if (licenseManager.checkCachedLicense() > 0)
                    mHandler.sendEmptyMessage(3);
                else
                    mHandler.sendEmptyMessage(4);
            }
        }).start();
    }


    /**
     * 身份证 联网授权
     */
    public void idCardNetWorkWarranty(NetWorkWarrantyCallback netWorkWarrantyCallback) {
        this.netWorkWarrantyCallback = netWorkWarrantyCallback;
        new Thread(new Runnable() {
            @Override
            public void run() {
                String uuid = ConUtil.getUUIDString(APP.getApplication().getMainActivity());
                Manager manager = new Manager(APP.getApplication().getMainActivity());
                IDCardQualityLicenseManager idCardLicenseManager = new IDCardQualityLicenseManager(APP.getApplication().getMainActivity());
                manager.registerLicenseManager(idCardLicenseManager);
                try {
                    manager.takeLicenseFromNetwork(uuid);
                }catch (Exception e){
                }
                //授权成功
                if (idCardLicenseManager.checkCachedLicense() > 0)
                    mHandler.sendEmptyMessage(1);
                    //授权失败
                else
                    mHandler.sendEmptyMessage(2);
            }
        }).start();
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    MyLog.e("身份证联网授权成功");
                    APP.getApplication().getMainActivity().putSp("idCardNetWorkWarranty","idCardNetWorkWarranty");
                    if(netWorkWarrantyCallback!=null)
                        netWorkWarrantyCallback.grantResult(true);
                    break;
                case 2:
                    MyLog.e("身份证联网授权失败");
                    APP.getApplication().getMainActivity().putSp("idCardNetWorkWarranty","");
                    if(netWorkWarrantyCallback!=null)
                        netWorkWarrantyCallback.grantResult(false);
                    break;
                case 3:
                    APP.getApplication().getMainActivity().putSp("faceNetWorkWarranty","faceNetWorkWarranty");
                    MyLog.e("人脸识别联网授权成功");
                    if(netWorkWarrantyCallback!=null)
                        netWorkWarrantyCallback.grantResult(true);
                    break;
                case 4:
                    APP.getApplication().getMainActivity().putSp("faceNetWorkWarranty","");
                    MyLog.e("人脸识别联网授权成失败");
                    if(netWorkWarrantyCallback!=null)
                        netWorkWarrantyCallback.grantResult(false);
                    break;
            }
        }
    };

    public void myDestory(){

        netWorkWarrantyCallback = null;
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;

    }

    public interface NetWorkWarrantyCallback{

        void grantResult(boolean result);
    }
}
