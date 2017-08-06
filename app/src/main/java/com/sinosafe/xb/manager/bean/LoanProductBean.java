package com.sinosafe.xb.manager.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/16.
 * 贷款基本信息
 */

public class LoanProductBean implements Serializable{

    //产品名称
    private String prd_name;
    //标签
    private String prd_tag;
    //产品编号
    private String prd_code;
    //最小利率
    private float rate_min;
    //产品图标
    private String prd_ico;

    //产品详情
    private String prd_introduce;
    //系列产品最高申请金额
    private float sub_amt_max;
    //产品主键
    private String prd_pk;
    //是否需要授信执行
    private String is_credit_implementation;
    //产品类别
    private String prd_type;
    //放款速度
    private String loan_speed;
    //最高年数
    private String highest_years;
    //面向群体
    private String oriented_groups;

    public void setPrd_name(String prd_name){
        this.prd_name = prd_name;
    }
    public String getPrd_name(){
        return this.prd_name;
    }
    public void setPrd_tag(String prd_tag){
        this.prd_tag = prd_tag;
    }
    public String getPrd_tag(){
        return this.prd_tag;
    }
    public void setPrd_code(String prd_code){
        this.prd_code = prd_code;
    }
    public String getPrd_code(){
        return this.prd_code;
    }
    public void setRate_min(float rate_min){
        this.rate_min = rate_min;
    }
    public float getRate_min(){
        return this.rate_min;
    }
    public void setPrd_ico(String prd_ico){
        this.prd_ico = prd_ico;
    }
    public String getPrd_ico(){
        return this.prd_ico;
    }
    public void setPrd_introduce(String prd_introduce){
        this.prd_introduce = prd_introduce;
    }
    public String getPrd_introduce(){
        return this.prd_introduce;
    }
    public void setSub_amt_max(float sub_amt_max){
        this.sub_amt_max = sub_amt_max;
    }
    public float getSub_amt_max(){
        return this.sub_amt_max;
    }
    public void setPrd_pk(String prd_pk){
        this.prd_pk = prd_pk;
    }
    public String getPrd_pk(){
        return this.prd_pk;
    }
    public void setIs_credit_implementation(String is_credit_implementation){
        this.is_credit_implementation = is_credit_implementation;
    }
    public String getIs_credit_implementation(){
        return this.is_credit_implementation;
    }
    public void setPrd_type(String prd_type){
        this.prd_type = prd_type;
    }
    public String getPrd_type(){
        return this.prd_type;
    }

    public String getLoan_speed() {
        return loan_speed;
    }

    public void setLoan_speed(String loan_speed) {
        this.loan_speed = loan_speed;
    }

    public String getHighest_years() {
        return highest_years;
    }

    public void setHighest_years(String highest_years) {
        this.highest_years = highest_years;
    }

    public String getOriented_groups() {
        return oriented_groups;
    }

    public void setOriented_groups(String oriented_groups) {
        this.oriented_groups = oriented_groups;
    }
}
