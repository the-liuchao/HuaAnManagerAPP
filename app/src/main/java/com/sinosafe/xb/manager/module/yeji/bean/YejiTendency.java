package com.sinosafe.xb.manager.module.yeji.bean;

import java.util.List;

/**
 * 类名称：   com.sinosafe.xb.manager.module.yeji.bean
 * 内容摘要： //封装的业绩趋势图bean。
 * 修改备注：
 * 创建时间： 2017/7/14 0014
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class YejiTendency {

    //平均值
    private float avg;
    //最大值
    private float max;
    //数据集合
    private List<TendencyItem> tendencyItem;

    public float getAvg() {
        return avg;
    }

    public void setAvg(float avg) {
        this.avg = avg;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public List<TendencyItem> getTendencyItem() {
        return tendencyItem;
    }

    public void setTendencyItem(List<TendencyItem> tendencyItem) {
        this.tendencyItem = tendencyItem;
    }

    public static class TendencyItem{

        private String date;

        private String data;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
}
