package com.sinosafe.xb.manager.module.yeji.bean;

/**
 * 类名称：   com.sinosafe.xb.manager.module.yeji.bean
 * 内容摘要： //排行榜。
 * 修改备注：
 * 创建时间： 2017/6/24 0024
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class RankingList {

    //保费收入
    private int salary;
    //业绩提成
    private int bonus;
    //放款笔数
    private int loanAmount;
    //放款金额
    private int loanFund;
    //回款率排名
    private int backPaymentRate;

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public int getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(int loanAmount) {
        this.loanAmount = loanAmount;
    }

    public int getLoanFund() {
        return loanFund;
    }

    public void setLoanFund(int loanFund) {
        this.loanFund = loanFund;
    }

    public int getBackPaymentRate() {
        return backPaymentRate;
    }

    public void setBackPaymentRate(int backPaymentRate) {
        this.backPaymentRate = backPaymentRate;
    }
}


