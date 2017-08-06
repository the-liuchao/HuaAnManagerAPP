package com.sinosafe.xb.manager.module.yeji.bean;

/**
 * 类名称：   com.sinosafe.xb.manager.module.yeji.bean
 * 内容摘要： //业绩情况。
 * 修改备注：
 * 创建时间： 2017/6/24 0024
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class YeJiReport {

    //放款金额
    private float loanFund;
    //放款笔数
    private int loanAmount;
    //保费收入
    private float salary;
    //逾期笔数
    private int delayAmount;
    //逾期金额
    private float delayFund;
    //预计提成
    private float AMOUNT;
    //本月预计提成
    private float monthBonus;
    //上月确定提成
    private float lastMonthBonus;
    //回款金额
    private float backFund;

    public float getLoanFund() {
        return loanFund;
    }

    public void setLoanFund(float loanFund) {
        this.loanFund = loanFund;
    }

    public int getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(int loanAmount) {
        this.loanAmount = loanAmount;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public int getDelayAmount() {
        return delayAmount;
    }

    public void setDelayAmount(int delayAmount) {
        this.delayAmount = delayAmount;
    }

    public float getDelayFund() {
        return delayFund;
    }

    public void setDelayFund(float delayFund) {
        this.delayFund = delayFund;
    }

    public float getAMOUNT() {
        return AMOUNT;
    }

    public void setAMOUNT(float AMOUNT) {
        this.AMOUNT = AMOUNT;
    }

    public float getMonthBonus() {
        return monthBonus;
    }

    public void setMonthBonus(float monthBonus) {
        this.monthBonus = monthBonus;
    }

    public float getLastMonthBonus() {
        return lastMonthBonus;
    }

    public void setLastMonthBonus(float lastMonthBonus) {
        this.lastMonthBonus = lastMonthBonus;
    }

    public float getBackFund() {
        return backFund;
    }

    public void setBackFund(float backFund) {
        this.backFund = backFund;
    }
}
