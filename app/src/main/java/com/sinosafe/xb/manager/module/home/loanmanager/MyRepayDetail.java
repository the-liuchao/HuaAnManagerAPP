package com.sinosafe.xb.manager.module.home.loanmanager;

/**
 * 类名称：   com.sinosafe.xb.manager.module.home.loanmanager
 * 内容摘要： //封装的还款记录。
 * 修改备注：
 * 创建时间： 2017/7/4 0004
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class MyRepayDetail {

    //期号
    private int issueNumber;

    //21超期 00未执行  10正常 20逾期  30代偿
    private String repayStatus;

    //还款时间
    private String repaymentTime;

    //已还款
    private String hasRepayment;

    //应还金额
    private String dueAmount;

    //明细
    private String detailed;

    //金额、利息等等描述
    private String survey;

    //是否上报征信 0- 未上报  1- 上报
    private String IS_CREDIT_REPORT;

    private boolean openFlag;

    private boolean canOpen;


    public int getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(int issueNumber) {
        this.issueNumber = issueNumber;
    }

    public String getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(String repayStatus) {
        this.repayStatus = repayStatus;
    }

    public String getRepaymentTime() {
        return repaymentTime;
    }

    public void setRepaymentTime(String repaymentTime) {
        this.repaymentTime = repaymentTime;
    }

    public String getHasRepayment() {
        return hasRepayment;
    }

    public void setHasRepayment(String hasRepayment) {
        this.hasRepayment = hasRepayment;
    }

    public String getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(String dueAmount) {
        this.dueAmount = dueAmount;
    }

    public String getDetailed() {
        return detailed;
    }

    public void setDetailed(String detailed) {
        this.detailed = detailed;
    }

    public String getSurvey() {
        return survey;
    }

    public void setSurvey(String survey) {
        this.survey = survey;
    }

    public String getIS_CREDIT_REPORT() {
        return IS_CREDIT_REPORT;
    }

    public void setIS_CREDIT_REPORT(String IS_CREDIT_REPORT) {
        this.IS_CREDIT_REPORT = IS_CREDIT_REPORT;
    }

    public boolean isOpenFlag() {
        return openFlag;
    }

    public void setOpenFlag(boolean openFlag) {
        this.openFlag = openFlag;
    }

    public boolean isCanOpen() {
        return canOpen;
    }

    public void setCanOpen(boolean canOpen) {
        this.canOpen = canOpen;
    }
}
