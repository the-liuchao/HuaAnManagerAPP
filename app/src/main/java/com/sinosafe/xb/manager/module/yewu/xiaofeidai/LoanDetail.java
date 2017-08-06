package com.sinosafe.xb.manager.module.yewu.xiaofeidai;

import com.sinosafe.xb.manager.bean.YeWuBean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/16.
 * 消费贷详情
 */

public class LoanDetail {

    List<LoadSchedual> LOAN_SCHEDUAL;

    YeWuBean LOAN_DETAIL;

    LoanResult LOAN_RESULT;

    public List<LoadSchedual> getLOAN_SCHEDUAL() {
        return LOAN_SCHEDUAL;
    }

    public void setLOAN_SCHEDUAL(List<LoadSchedual> LOAN_SCHEDUAL) {
        this.LOAN_SCHEDUAL = LOAN_SCHEDUAL;
    }

    public YeWuBean getLOAN_DETAIL() {
        return LOAN_DETAIL;
    }

    public void setLOAN_DETAIL(YeWuBean LOAN_DETAIL) {
        this.LOAN_DETAIL = LOAN_DETAIL;
    }

    public LoanResult getLOAN_RESULT() {
        return LOAN_RESULT;
    }

    public void setLOAN_RESULT(LoanResult LOAN_RESULT) {
        this.LOAN_RESULT = LOAN_RESULT;
    }

    //贷款进度
    public static class LoadSchedual{

        private String OPER_ID;//
        private String PK_ID;//主键
        private String IQP_SERNO;//业务申请号
        private String TASK_END_TIME;
        private String STATUS;
        private String SERNO;
        private String OPER_TIME;//操作时间
        private String OPER_ORGID;//
        private String NODENAME;
        private String TASK_BEGIN_TIME;
        private String PAGE_COUNT;


        public String getOPER_ID() {
            return OPER_ID;
        }

        public void setOPER_ID(String OPER_ID) {
            this.OPER_ID = OPER_ID;
        }

        public String getPK_ID() {
            return PK_ID;
        }

        public void setPK_ID(String PK_ID) {
            this.PK_ID = PK_ID;
        }

        public String getIQP_SERNO() {
            return IQP_SERNO;
        }

        public void setIQP_SERNO(String IQP_SERNO) {
            this.IQP_SERNO = IQP_SERNO;
        }

        public String getTASK_END_TIME() {
            return TASK_END_TIME;
        }

        public void setTASK_END_TIME(String TASK_END_TIME) {
            this.TASK_END_TIME = TASK_END_TIME;
        }

        public String getSTATUS() {
            return STATUS;
        }

        public void setSTATUS(String STATUS) {
            this.STATUS = STATUS;
        }

        public String getSERNO() {
            return SERNO;
        }

        public void setSERNO(String SERNO) {
            this.SERNO = SERNO;
        }

        public String getOPER_TIME() {
            return OPER_TIME;
        }

        public void setOPER_TIME(String OPER_TIME) {
            this.OPER_TIME = OPER_TIME;
        }

        public String getOPER_ORGID() {
            return OPER_ORGID;
        }

        public void setOPER_ORGID(String OPER_ORGID) {
            this.OPER_ORGID = OPER_ORGID;
        }

        public String getNODENAME() {
            return NODENAME;
        }

        public void setNODENAME(String NODENAME) {
            this.NODENAME = NODENAME;
        }

        public String getTASK_BEGIN_TIME() {
            return TASK_BEGIN_TIME;
        }

        public void setTASK_BEGIN_TIME(String TASK_BEGIN_TIME) {
            this.TASK_BEGIN_TIME = TASK_BEGIN_TIME;
        }

        public String getPAGE_COUNT() {
            return PAGE_COUNT;
        }

        public void setPAGE_COUNT(String PAGE_COUNT) {
            this.PAGE_COUNT = PAGE_COUNT;
        }
    }

    /**
     * 批复结果
     */
    public class LoanResult {

        private int IS_PAY_FEE;//是否代扣保费 0：代扣 1：在线缴费
        private double APPROVE_AMOUNT;//批准金额
        private String APPROVE_TERM;//期限(月)
        private String APPROVE_TERMDAY;//期限(天)
        private double COST_RATE;//保险费率
        private String APPROVE_REPAYMODE;//批准还款方式
        private double APPROVE_RATE;//贷款利率
        private String TERM_TIME_TYPE;//期限类型
        private double INSUR_AMOUNT;//保险金额

        public int getIS_PAY_FEE() {
            return IS_PAY_FEE;
        }

        public void setIS_PAY_FEE(int IS_PAY_FEE) {
            this.IS_PAY_FEE = IS_PAY_FEE;
        }

        public double getAPPROVE_AMOUNT() {
            return APPROVE_AMOUNT;
        }

        public void setAPPROVE_AMOUNT(double APPROVE_AMOUNT) {
            this.APPROVE_AMOUNT = APPROVE_AMOUNT;
        }

        public String getAPPROVE_TERM() {
            return APPROVE_TERM;
        }

        public void setAPPROVE_TERM(String APPROVE_TERM) {
            this.APPROVE_TERM = APPROVE_TERM;
        }

        public String getAPPROVE_TERMDAY() {
            return APPROVE_TERMDAY;
        }

        public void setAPPROVE_TERMDAY(String APPROVE_TERMDAY) {
            this.APPROVE_TERMDAY = APPROVE_TERMDAY;
        }

        public double getCOST_RATE() {
            return COST_RATE;
        }

        public void setCOST_RATE(double COST_RATE) {
            this.COST_RATE = COST_RATE;
        }

        public String getAPPROVE_REPAYMODE() {
            return APPROVE_REPAYMODE;
        }

        public void setAPPROVE_REPAYMODE(String APPROVE_REPAYMODE) {
            this.APPROVE_REPAYMODE = APPROVE_REPAYMODE;
        }

        public double getAPPROVE_RATE() {
            return APPROVE_RATE;
        }

        public void setAPPROVE_RATE(double APPROVE_RATE) {
            this.APPROVE_RATE = APPROVE_RATE;
        }

        public String getTERM_TIME_TYPE() {
            return TERM_TIME_TYPE;
        }

        public void setTERM_TIME_TYPE(String TERM_TIME_TYPE) {
            this.TERM_TIME_TYPE = TERM_TIME_TYPE;
        }

        public double getINSUR_AMOUNT() {
            return INSUR_AMOUNT;
        }

        public void setINSUR_AMOUNT(double INSUR_AMOUNT) {
            this.INSUR_AMOUNT = INSUR_AMOUNT;
        }
    }
}
