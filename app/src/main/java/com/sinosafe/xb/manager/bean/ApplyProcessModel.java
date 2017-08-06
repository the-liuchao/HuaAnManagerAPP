package com.sinosafe.xb.manager.bean;

/**
 * 类名称：   com.sinosafe.xb.manager.bean
 * 内容摘要： //申请流程。
 * 修改备注：
 * 创建时间： 2017/6/9 0009
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class ApplyProcessModel {

    private String name;
    private int order;

    public ApplyProcessModel(String name,int order) {
        this.name = name;
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
