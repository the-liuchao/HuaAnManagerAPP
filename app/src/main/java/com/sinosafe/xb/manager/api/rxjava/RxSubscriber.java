package com.sinosafe.xb.manager.api.rxjava;

import android.content.Intent;

import com.sinosafe.xb.manager.APP;
import com.sinosafe.xb.manager.api.rxjava.myException.ReloginException;
import com.sinosafe.xb.manager.utils.Constant;

import rx.Subscriber;

/**
 * 类名称：   com.cnmobi.jichengguang.rxjava
 * 内容摘要： //封装Subscriber。
 * 修改备注：
 * 创建时间： 2017/5/11
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public abstract class  RxSubscriber<T> extends Subscriber<T> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        _onError(e.getMessage());

        if (e instanceof ReloginException) {
            // 服务器异常
            APP.getApplication().sendBroadcast(new Intent(Constant.TOKEN_INVALID_422));
        }
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    public abstract void _onNext(T t);

    public abstract void _onError(String msg);
}
