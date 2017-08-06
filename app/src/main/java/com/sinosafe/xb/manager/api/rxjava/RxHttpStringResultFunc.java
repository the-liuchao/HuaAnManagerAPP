package com.sinosafe.xb.manager.api.rxjava;

import rx.functions.Func1;

/**
 * 类名称：   com.cnmobi.jichengguang.rxjava
 * 内容摘要： //Rx处理服务器返回
 * 修改备注：
 * 创建时间： 2017/5/11
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */

public class RxHttpStringResultFunc implements Func1<String, String> {

    @Override public String call(String httpResult) {

        if(httpResult==null)
            throw new RuntimeException("返回结果是空的");

        return httpResult;
    }

}
