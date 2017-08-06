package com.sinosafe.xb.manager.bean;

/**
 * 贷款汇率列表
 * Created by john lee on 2017/6/9.
 */

public class LoanRateListBean {

    private String rate_id;
    private String create_date;
    private String period;
    private String prd_id;
    private String prd_cost_rate;

    public String getRate_id() {
        return this.rate_id;
    }

    public void setRate_id(String rate_id) {
        this.rate_id = rate_id;
    }

    public String getCreate_date() {
        return this.create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPrd_id() {
        return this.prd_id;
    }

    public void setPrd_id(String prd_id) {
        this.prd_id = prd_id;
    }

    public String getPrd_cost_rate() {
        return this.prd_cost_rate;
    }

    public void setPrd_cost_rate(String prd_cost_rate) {
        this.prd_cost_rate = prd_cost_rate;
    }

    @Override
    public String toString() {
        return "LoanRateListBean{" +
                "rate_id='" + rate_id + '\'' +
                ", create_date='" + create_date + '\'' +
                ", period='" + period + '\'' +
                ", prd_id='" + prd_id + '\'' +
                ", prd_cost_rate='" + prd_cost_rate + '\'' +
                '}';
    }
}
