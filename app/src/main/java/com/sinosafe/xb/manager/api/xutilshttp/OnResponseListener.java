package com.sinosafe.xb.manager.api.xutilshttp;

/**
 * 类名称：   com.cnmobi.tujiclient.trace.http
 * 内容摘要： //请求成功回调。
 * 修改备注：
 * 创建时间： 2017/4/14
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public abstract class OnResponseListener {
    public OnResponseListener() {
    }

    /**
     * 查询失败
     */
    public  void onRequestFailedCallback(String var1){};

    /**
     * 身份证识别成功回调
     */
    public void  onIDCardSuccessListCallback(String result){};

    /**
     * 人脸识别成功回调
     */
    public void  onFaceSuccessListCallback(String result){};

    /**
     * 影像上传 xml文件
     * @param result
     */
    public void onFileUpLoadXMLCallback(String result){};


    /**
     * 共同成功返回
     * @param result
     */
    public void onCommonRequestCallback(String result){};

    /**
     * 卡bin判断成功返回
     * @param result
     */
    public void onBankcardbinCallback(String result){};


}
