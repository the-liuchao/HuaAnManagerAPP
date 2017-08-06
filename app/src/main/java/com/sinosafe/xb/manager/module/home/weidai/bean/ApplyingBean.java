package com.sinosafe.xb.manager.module.home.weidai.bean;

import java.io.Serializable;

/**
 * 类名称：   com.sinosafe.xb.manager.module.home.weidai.bean
 * 内容摘要： //申请中数据。
 * 修改备注：
 * 创建时间： 2017/6/27 0027
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class ApplyingBean implements Serializable{

    private String user_id;

    private String serno;

    private float amount;

    private int period;

    private String approve_status;

    private String applyDate;

    private String prdName;

    private String prdId;

    private String loanType;

    //客户详情的纪录编号
    private String detail_id;

    private String user_name;

    private String mobile;

    private String head_photo;

    private String prd_type = "2";

    private String register_latitude;

    private String cert_code;

    private String create_date;

    //婚姻状况(0未婚，1已婚)
    private String indiv_mar_st;
    //1男,2女
    private String  indiv_sex;
    //配偶身份证号
    private String indiv_sps_cardno;

    //车产，1有车
    private String carl_st;
    private String carl_certificate;
    //房产，1有房
    private String house_st;
    private String house_certificate;


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSerno() {
        return serno;
    }

    public void setSerno(String serno) {
        this.serno = serno;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getApprove_status() {
        return approve_status;
    }

    public void setApprove_status(String approve_status) {
        this.approve_status = approve_status;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getPrdName() {
        return prdName;
    }

    public void setPrdName(String prdName) {
        this.prdName = prdName;
    }

    public String getPrdId() {
        return prdId;
    }

    public void setPrdId(String prdId) {
        this.prdId = prdId;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public String getDetail_id() {
        return detail_id;
    }

    public void setDetail_id(String detail_id) {
        this.detail_id = detail_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHead_photo() {
        return head_photo;
    }

    public void setHead_photo(String head_photo) {
        this.head_photo = head_photo;
    }

    public String getPrd_type() {
        return prd_type;
    }

    public void setPrd_type(String prd_type) {
        this.prd_type = prd_type;
    }

    public String getRegister_latitude() {
        return register_latitude;
    }

    public void setRegister_latitude(String register_latitude) {
        this.register_latitude = register_latitude;
    }

    public String getCert_code() {
        return cert_code;
    }

    public void setCert_code(String cert_code) {
        this.cert_code = cert_code;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getIndiv_mar_st() {
        return indiv_mar_st;
    }

    public void setIndiv_mar_st(String indiv_mar_st) {
        this.indiv_mar_st = indiv_mar_st;
    }

    public String getIndiv_sex() {
        return indiv_sex;
    }

    public void setIndiv_sex(String indiv_sex) {
        this.indiv_sex = indiv_sex;
    }

    public String getIndiv_sps_cardno() {
        return indiv_sps_cardno;
    }

    public void setIndiv_sps_cardno(String indiv_sps_cardno) {
        this.indiv_sps_cardno = indiv_sps_cardno;
    }

    public String getCarl_st() {
        return carl_st;
    }

    public void setCarl_st(String carl_st) {
        this.carl_st = carl_st;
    }

    public String getCarl_certificate() {
        return carl_certificate;
    }

    public void setCarl_certificate(String carl_certificate) {
        this.carl_certificate = carl_certificate;
    }

    public String getHouse_st() {
        return house_st;
    }

    public void setHouse_st(String house_st) {
        this.house_st = house_st;
    }

    public String getHouse_certificate() {
        return house_certificate;
    }

    public void setHouse_certificate(String house_certificate) {
        this.house_certificate = house_certificate;
    }
}
