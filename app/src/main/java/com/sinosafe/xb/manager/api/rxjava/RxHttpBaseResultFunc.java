package com.sinosafe.xb.manager.api.rxjava;

import android.os.Handler;
import android.os.Message;

import com.sinosafe.xb.manager.api.rxjava.myException.OtherException;
import com.sinosafe.xb.manager.api.rxjava.myException.ReloginException;
import com.sinosafe.xb.manager.bean.BaseEntity;

import luo.library.base.utils.MyLog;
import rx.functions.Func1;

/**
 * 类名称：   com.cnmobi.jichengguang.rxjava
 * 内容摘要： //Rx处理服务器返回
 * 修改备注：
 * 创建时间： 2017/5/11
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */

public class RxHttpBaseResultFunc implements Func1<BaseEntity, BaseEntity> {

    @Override public BaseEntity call(BaseEntity httpResult) {


        if(httpResult.getResult()==null)
            httpResult.setResult("");
        Message mess = handler.obtainMessage(1);
        mess.obj = httpResult.getMsg();
        mess.what = httpResult.getCode();
        handler.sendMessage(mess);
        if(httpResult.getCode()==1)
            return httpResult;
        else if(httpResult.getCode()==422){
            throw new ReloginException(httpResult.getMsg());
        }else
            throw new OtherException(httpResult.getMsg());
    }


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.obj!=null) {
                MyLog.e("http请求返回结果：" + msg.obj.toString() + "       code=" + msg.what);
            }
        }
    };
}
