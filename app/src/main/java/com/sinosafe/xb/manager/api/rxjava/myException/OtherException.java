package com.sinosafe.xb.manager.api.rxjava.myException;

/**
 * 类名称：   com.sinosafe.xb.manager.api.rxjava.myException
 * 内容摘要： //请求其他异常。
 * 修改备注：
 * 创建时间： 2017/6/21 0021
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class OtherException extends RuntimeException{

    public OtherException(String message) {
        super(message);
    }

    public OtherException(String message, Throwable cause) {
        super(message, cause);
    }

    public OtherException(Throwable cause) {
        super(cause);
    }
}
