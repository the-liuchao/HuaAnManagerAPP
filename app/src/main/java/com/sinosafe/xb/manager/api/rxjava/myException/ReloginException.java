package com.sinosafe.xb.manager.api.rxjava.myException;

/**
 * 类名称：   com.sinosafe.xb.manager.api.rxjava.myException
 * 内容摘要： //token登录失效异常。
 * 修改备注：
 * 创建时间： 2017/6/21 0021
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class ReloginException extends RuntimeException{

    public ReloginException(String message) {
        super(message);
    }

    public ReloginException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReloginException(Throwable cause) {
        super(cause);
    }
}
