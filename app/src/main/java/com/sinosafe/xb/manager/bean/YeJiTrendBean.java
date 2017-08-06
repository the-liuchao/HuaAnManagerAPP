package com.sinosafe.xb.manager.bean;

/**
 * 类名称：   com.sinosafe.xb.manager.bean
 * 内容摘要： //业绩趋势bean。
 * 修改备注：
 * 创建时间： 2017/6/7 0007
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class YeJiTrendBean {


    //时间
    private String date;
    //结果
    private String result;

    //标记 0：时间节点
    private int flag = 1;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
