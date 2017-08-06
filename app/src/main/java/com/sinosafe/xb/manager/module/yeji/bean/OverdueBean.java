package com.sinosafe.xb.manager.module.yeji.bean;

/**
 * 类名称：   com.sinosafe.xb.manager.module.yeji.bean
 * 内容摘要： //逾期笔数、金额。
 * 修改备注：
 * 创建时间： 2017/7/1 0001
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class OverdueBean {

    private int OVERDUE_DAY;

    private String CUS_ID;

    private int ROW_NUM;

    private String CUS_MGR;

    private double OVERDUE_AMT;

    private String IQP_LOAN_SERNO;

    private String BILL_NO;

    private String CUS_NAME;

    private int PAGE_COUNT;

    private String MOBILE;

    private String head_photo;


    public void setOVERDUE_DAY(int OVERDUE_DAY) {
        this.OVERDUE_DAY = OVERDUE_DAY;
    }

    public int getOVERDUE_DAY() {
        return this.OVERDUE_DAY;
    }

    public void setCUS_ID(String CUS_ID) {
        this.CUS_ID = CUS_ID;
    }

    public String getCUS_ID() {
        return this.CUS_ID;
    }

    public void setROW_NUM(int ROW_NUM) {
        this.ROW_NUM = ROW_NUM;
    }

    public int getROW_NUM() {
        return this.ROW_NUM;
    }

    public void setCUS_MGR(String CUS_MGR) {
        this.CUS_MGR = CUS_MGR;
    }

    public String getCUS_MGR() {
        return this.CUS_MGR;
    }

    public void setOVERDUE_AMT(double OVERDUE_AMT) {
        this.OVERDUE_AMT = OVERDUE_AMT;
    }

    public double getOVERDUE_AMT() {
        return this.OVERDUE_AMT;
    }

    public void setIQP_LOAN_SERNO(String IQP_LOAN_SERNO) {
        this.IQP_LOAN_SERNO = IQP_LOAN_SERNO;
    }

    public String getIQP_LOAN_SERNO() {
        return this.IQP_LOAN_SERNO;
    }

    public void setBILL_NO(String BILL_NO) {
        this.BILL_NO = BILL_NO;
    }

    public String getBILL_NO() {
        return this.BILL_NO;
    }

    public void setCUS_NAME(String CUS_NAME) {
        this.CUS_NAME = CUS_NAME;
    }

    public String getCUS_NAME() {
        return this.CUS_NAME;
    }

    public void setPAGE_COUNT(int PAGE_COUNT) {
        this.PAGE_COUNT = PAGE_COUNT;
    }

    public int getPAGE_COUNT() {
        return this.PAGE_COUNT;
    }

    public String getMOBILE() {
        return MOBILE;
    }

    public void setMOBILE(String mobile) {
        this.MOBILE = mobile;
    }

    public String getHead_photo() {
        return head_photo;
    }

    public void setHead_photo(String head_photo) {
        this.head_photo = head_photo;
    }
}


