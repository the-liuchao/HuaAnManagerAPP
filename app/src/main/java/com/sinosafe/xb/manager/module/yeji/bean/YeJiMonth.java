package com.sinosafe.xb.manager.module.yeji.bean;

import java.util.List;

/**
 * 类名称：   com.sinosafe.xb.manager.module.yeji.bean
 * 内容摘要： //业绩---月。
 * 修改备注：
 * 创建时间： 2017/7/14 0014
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class YeJiMonth {

    //平均值
    private float avg;
    //最大值
    private float max;
    //数据集合
    private List<MonthArray> array;

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

    public List<MonthArray> getArray() {
        return array;
    }

    public void setArray(List<MonthArray> array) {
        this.array = array;
    }

    public static class MonthArray {

        //年
        private String year;
        //年的数据集合
        private List<MonthItem> data;

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public List<MonthItem> getData() {
            return data;
        }

        public void setData(List<MonthItem> data) {
            this.data = data;
        }
    }

    public static class MonthItem {

        //时间
        private String TIME;
        //结构
        private String RESULT;

        public String getTIME() {
            return TIME;
        }

        public void setTIME(String TIME) {
            this.TIME = TIME;
        }

        public String getRESULT() {
            return RESULT;
        }

        public void setRESULT(String RESULT) {
            this.RESULT = RESULT;
        }
    }
}
