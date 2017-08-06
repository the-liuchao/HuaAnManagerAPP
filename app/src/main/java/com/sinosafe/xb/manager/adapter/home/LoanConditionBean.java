package com.sinosafe.xb.manager.adapter.home;


/**
 * 消费贷 筛选条件
 * Created by john lee on 2017/5/31.
 */

public class LoanConditionBean {

    private int criteria_type;
    private String criteria_value;
    private String criteria_id;

    public int getCriteria_type() {
        return criteria_type;
    }

    public void setCriteria_type(int criteria_type) {
        this.criteria_type = criteria_type;
    }

    public String getCriteria_value() {
        return criteria_value;
    }

    public void setCriteria_value(String criteria_value) {
        this.criteria_value = criteria_value;
    }

    public String getCriteria_id() {
        return criteria_id;
    }

    public void setCriteria_id(String criteria_id) {
        this.criteria_id = criteria_id;
    }
}

