package com.sinosafe.xb.manager.bean;

import java.io.Serializable;

/**
 * 类名称：   com.cnmobi.jichengguang.model
 * 内容摘要： //返回数据封装。
 * 修改备注：
 * 创建时间： 2017/5/10
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class BaseEntity<E> implements Serializable {
    private int code;
    private String msg;
    private E result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public E getResult() {
        return result;
    }

    public void setResult(E result) {
        this.result = result;
    }
}
