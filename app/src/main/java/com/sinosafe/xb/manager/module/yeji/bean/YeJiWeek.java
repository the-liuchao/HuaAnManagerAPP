package com.sinosafe.xb.manager.module.yeji.bean;

import java.util.List;

/**
 * 类名称：   com.sinosafe.xb.manager.module.yeji.bean
 * 内容摘要： //业绩---周。
 * 修改备注：
 * 创建时间： 2017/7/14 0014
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class YeJiWeek {

    private List<WeekItem> data;
    private float avg;
    private float max;

    public List<WeekItem> getData() {
        return data;
    }

    public void setData(List<WeekItem> data) {
        this.data = data;
    }

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

    public static class WeekItem {

        //周
        private String WEEK;
        //
        private String RESULT;
        //结束时间
        private String END_DAY;
        //开始时间
        private String START_DAY;

        public String getWEEK() {
            return WEEK;
        }

        public void setWEEK(String WEEK) {
            this.WEEK = WEEK;
        }

        public String getRESULT() {
            return RESULT;
        }

        public void setRESULT(String RESULT) {
            this.RESULT = RESULT;
        }

        public String getEND_DAY() {
            return END_DAY;
        }

        public void setEND_DAY(String END_DAY) {
            this.END_DAY = END_DAY;
        }

        public String getSTART_DAY() {
            return START_DAY;
        }

        public void setSTART_DAY(String START_DAY) {
            this.START_DAY = START_DAY;
        }
    }
}
