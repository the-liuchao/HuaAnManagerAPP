package com.sinosafe.xb.manager.api.xutilshttp;

import android.content.Context;
import android.content.Intent;

import com.sinosafe.xb.manager.APP;
import com.sinosafe.xb.manager.bean.BaseEntity;
import com.sinosafe.xb.manager.utils.Constant;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import luo.library.base.utils.GsonUtil;
import luo.library.base.utils.MyLog;

/**
 * xutils3 网络请求封装
 * 实名认证用
 */

public class XutilsBaseHttp {

    //请求类型 0：身份证识别；1：人脸识别；卡bin类型判断10：
    public static int requestType = -1;

    private XutilsBaseHttp() {

    }

    public static void get(RequestParams params, OnResponseListener baseHttpCallback) {
        params.setConnectTimeout(15*1000);
        sendRequest(HttpMethod.GET, params, baseHttpCallback);
    }

    public static void get(Context context, RequestParams params, OnResponseListener baseHttpCallback) {
        params.setConnectTimeout(15*1000);
        sendRequest( HttpMethod.GET, params, baseHttpCallback);
    }

    public static void post(RequestParams params, OnResponseListener baseHttpCallback) {
        params.setConnectTimeout(15*1000);
        sendRequest(HttpMethod.POST, params, baseHttpCallback);
    }

    public static void post(Context context, RequestParams params, OnResponseListener baseHttpCallback) {
        params.setConnectTimeout(15*1000);
        sendRequest( HttpMethod.POST, params, baseHttpCallback);
    }

    public static void sendRequest(HttpMethod httpMethod, RequestParams params, final OnResponseListener baseHttpCallback) {
        MyLog.e("请求url===================="+params.getUri());

        //请求签名
        //Sign.getSignByOne(params,Constant.secret);
        /** 判断https证书是否成功验证 */
        if(requestType!=0&&requestType!=1&&requestType!=10){
            SSLContext sslContext = getSSLContext(APP.getApplication());
            if(null == sslContext){
                MyLog.e("Https配置错误：Error:Can't Get SSLContext!");
            }else{
                //绑定SSL证书(https请求)
                //MyLog.e("启用Https证书验证");
                params.setSslSocketFactory(sslContext.getSocketFactory());
            }
        }
        x.http().request(httpMethod, params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                BaseEntity entity = GsonUtil.GsonToBean(result, BaseEntity.class);
                if(entity!=null&&entity.getCode()==422){
                    baseHttpCallback.onRequestFailedCallback(entity.getMsg());
                    APP.getApplication().sendBroadcast(new Intent(Constant.TOKEN_INVALID_422));
                    return;
                }

                if(requestType==0)
                    baseHttpCallback.onIDCardSuccessListCallback(result);
                else if(requestType == 1){
                    baseHttpCallback.onFaceSuccessListCallback(result);
                }
                else if(requestType == 2){
                    baseHttpCallback.onFileUpLoadXMLCallback(result);
                }
                else if(requestType == 10){
                    baseHttpCallback.onBankcardbinCallback(result);
                }
                else{
                    baseHttpCallback.onCommonRequestCallback(result);
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                MyLog.e("终端信息获取异常："+ex.getMessage());
                baseHttpCallback.onRequestFailedCallback(ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 获取Https的证书
     * @param context Activity（fragment）的上下文
     * @return SSL的上下文对象
     */
    private static SSLContext getSSLContext(Context context) {
        CertificateFactory certificateFactory = null;
        InputStream inputStream = null;
        Certificate cer = null;
        KeyStore keystore = null;
        TrustManagerFactory trustManagerFactory = null;
        try {
            certificateFactory = CertificateFactory.getInstance("X.509");
            inputStream = context.getAssets().open("all_sinosafe_com_cn.crt");//这里导入SSL证书文件
            try {
                //读取证书
                cer = certificateFactory.generateCertificate(inputStream);
            } finally {
                inputStream.close();
            }
            //创建一个证书库，并将证书导入证书库
            keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            keystore.load(null,null); //双向验证时使用
            keystore.setCertificateEntry("trust", cer);

            // 实例化信任库
            trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            // 初始化信任库
            trustManagerFactory.init(keystore);
            SSLContext s_sSLContext = SSLContext.getInstance("TLS");
            s_sSLContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            return s_sSLContext;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
