package com.sinosafe.xb.manager.module.home.loanmanager;

import java.util.List;

/**
 * 类名称：   com.sinosafe.xb.manager.module.home.loanmanager
 * 内容摘要： //还款详情。
 * 修改备注：
 * 创建时间： 2017/7/1 0001
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class RepaymentDetails {

    private List<RepaymentNote> AccMtdPsNote;

    private List<RepaymentPlan> AccMtdPlan;

    public List<RepaymentNote> getAccMtdPsNote() {
        return AccMtdPsNote;
    }

    public void setAccMtdPsNote(List<RepaymentNote> accMtdPsNote) {
        AccMtdPsNote = accMtdPsNote;
    }

    public List<RepaymentPlan> getAccMtdPlan() {
        return AccMtdPlan;
    }

    public void setAccMtdPlan(List<RepaymentPlan> accMtdPlan) {
        AccMtdPlan = accMtdPlan;
    }



    //还款明细
    public class RepaymentNote{

        //主键
        private String PK_ID;
        //应还复利
        private float PS_COMM_OD_INT;
        //
        private String REMARK;
        //
        private String PS_REAL_SOURCE;
        //实际还款利息
        private float PS_REAL_INT_AMT;
        //实还罚息
        private float SETL_OD_INC_TAKEN;
        //实际还款本金
        private float PS_REAL_PRCP_AMT;
        //应还罚息
        private float PS_OD_INT_AMT;
        //已还复利
        private float SETL_COMM_OD_INT;
        //摘要
        private String ABSTRACT;
        //银行/冲账流水号
        private String BANK_SERNO;
        //实际还款日期
        private String PS_REAL_DT;
        //原借据编号
        private String BILL_NO;
        //期号
        private int PS_PERD_NO;
        //页码
        private String PAGE_COUNT;

        private String REPAYMENT_MODE;


        public String getPK_ID() {
            return PK_ID;
        }

        public void setPK_ID(String PK_ID) {
            this.PK_ID = PK_ID;
        }

        public float getPS_COMM_OD_INT() {
            return PS_COMM_OD_INT;
        }

        public void setPS_COMM_OD_INT(float PS_COMM_OD_INT) {
            this.PS_COMM_OD_INT = PS_COMM_OD_INT;
        }

        public String getREMARK() {
            return REMARK;
        }

        public void setREMARK(String REMARK) {
            this.REMARK = REMARK;
        }

        public String getPS_REAL_SOURCE() {
            return PS_REAL_SOURCE;
        }

        public void setPS_REAL_SOURCE(String PS_REAL_SOURCE) {
            this.PS_REAL_SOURCE = PS_REAL_SOURCE;
        }

        public float getPS_REAL_INT_AMT() {
            return PS_REAL_INT_AMT;
        }

        public void setPS_REAL_INT_AMT(float PS_REAL_INT_AMT) {
            this.PS_REAL_INT_AMT = PS_REAL_INT_AMT;
        }

        public float getSETL_OD_INC_TAKEN() {
            return SETL_OD_INC_TAKEN;
        }

        public void setSETL_OD_INC_TAKEN(float SETL_OD_INC_TAKEN) {
            this.SETL_OD_INC_TAKEN = SETL_OD_INC_TAKEN;
        }

        public float getPS_REAL_PRCP_AMT() {
            return PS_REAL_PRCP_AMT;
        }

        public void setPS_REAL_PRCP_AMT(float PS_REAL_PRCP_AMT) {
            this.PS_REAL_PRCP_AMT = PS_REAL_PRCP_AMT;
        }

        public float getPS_OD_INT_AMT() {
            return PS_OD_INT_AMT;
        }

        public void setPS_OD_INT_AMT(float PS_OD_INT_AMT) {
            this.PS_OD_INT_AMT = PS_OD_INT_AMT;
        }

        public float getSETL_COMM_OD_INT() {
            return SETL_COMM_OD_INT;
        }

        public void setSETL_COMM_OD_INT(float SETL_COMM_OD_INT) {
            this.SETL_COMM_OD_INT = SETL_COMM_OD_INT;
        }

        public String getABSTRACT() {
            return ABSTRACT;
        }

        public void setABSTRACT(String ABSTRACT) {
            this.ABSTRACT = ABSTRACT;
        }

        public String getBANK_SERNO() {
            return BANK_SERNO;
        }

        public void setBANK_SERNO(String BANK_SERNO) {
            this.BANK_SERNO = BANK_SERNO;
        }

        public String getPS_REAL_DT() {
            return PS_REAL_DT;
        }

        public void setPS_REAL_DT(String PS_REAL_DT) {
            this.PS_REAL_DT = PS_REAL_DT;
        }

        public String getBILL_NO() {
            return BILL_NO;
        }

        public void setBILL_NO(String BILL_NO) {
            this.BILL_NO = BILL_NO;
        }

        public int getPS_PERD_NO() {
            return PS_PERD_NO;
        }

        public void setPS_PERD_NO(int PS_PERD_NO) {
            this.PS_PERD_NO = PS_PERD_NO;
        }

        public String getPAGE_COUNT() {
            return PAGE_COUNT;
        }

        public void setPAGE_COUNT(String PAGE_COUNT) {
            this.PAGE_COUNT = PAGE_COUNT;
        }

        public String getREPAYMENT_MODE() {
            return REPAYMENT_MODE;
        }

        public void setREPAYMENT_MODE(String REPAYMENT_MODE) {
            this.REPAYMENT_MODE = REPAYMENT_MODE;
        }
    }

    //还款计划
    public class RepaymentPlan{

        //应还罚息
        private float PS_OD_INT_AMT;
        //应还复利
        private float PS_COMM_OD_INT;
        //期号
        private int PS_PERD_NO;
        //期供
        private float PS_INSTM_AMT;
        //借据号
        private String BILL_NO;
        //状态(STD_ZB_REPAYPLAN_ST)21	超期00	未执行10	正常20	逾期30	代偿

        private String REPAY_FLAG;
        //本月应还,本金
        private float PS_PRCP_AMT;
        //利息
        private float PS_NORM_INT_AMT;
        //利率
        private float PS_INT_RATE;
        //还款日期
        private String PS_DUE_DT;
        //页码
        private String PAGE_COUNT;

        //是否上报征信0- 未上报  1- 上报
        private String IS_CREDIT_REPORT;

        private boolean openFlag;

        private String REPAYMENT_MODE;

        private String ABSTRACT;

        private float OVER_AMOUNT;//- 逾期金额
        private String OVER_DAYS;//-逾期天数 ;

        public float getOVER_AMOUNT() {
            return OVER_AMOUNT;
        }

        public void setOVER_AMOUNT(float OVER_AMOUNT) {
            this.OVER_AMOUNT = OVER_AMOUNT;
        }

        public String getOVER_DAYS() {
            return OVER_DAYS;
        }

        public void setOVER_DAYS(String OVER_DAYS) {
            this.OVER_DAYS = OVER_DAYS;
        }

        public float getPS_OD_INT_AMT() {
            return PS_OD_INT_AMT;
        }

        public void setPS_OD_INT_AMT(float PS_OD_INT_AMT) {
            this.PS_OD_INT_AMT = PS_OD_INT_AMT;
        }

        public float getPS_COMM_OD_INT() {
            return PS_COMM_OD_INT;
        }

        public void setPS_COMM_OD_INT(float PS_COMM_OD_INT) {
            this.PS_COMM_OD_INT = PS_COMM_OD_INT;
        }

        public int getPS_PERD_NO() {
            return PS_PERD_NO;
        }

        public void setPS_PERD_NO(int PS_PERD_NO) {
            this.PS_PERD_NO = PS_PERD_NO;
        }

        public float getPS_INSTM_AMT() {
            return PS_INSTM_AMT;
        }

        public void setPS_INSTM_AMT(float PS_INSTM_AMT) {
            this.PS_INSTM_AMT = PS_INSTM_AMT;
        }

        public String getBILL_NO() {
            return BILL_NO;
        }

        public void setBILL_NO(String BILL_NO) {
            this.BILL_NO = BILL_NO;
        }

        public String getREPAY_FLAG() {
            return REPAY_FLAG;
        }

        public void setREPAY_FLAG(String REPAY_FLAG) {
            this.REPAY_FLAG = REPAY_FLAG;
        }

        public float getPS_PRCP_AMT() {
            return PS_PRCP_AMT;
        }

        public void setPS_PRCP_AMT(float PS_PRCP_AMT) {
            this.PS_PRCP_AMT = PS_PRCP_AMT;
        }

        public float getPS_NORM_INT_AMT() {
            return PS_NORM_INT_AMT;
        }

        public void setPS_NORM_INT_AMT(float PS_NORM_INT_AMT) {
            this.PS_NORM_INT_AMT = PS_NORM_INT_AMT;
        }

        public float getPS_INT_RATE() {
            return PS_INT_RATE;
        }

        public void setPS_INT_RATE(float PS_INT_RATE) {
            this.PS_INT_RATE = PS_INT_RATE;
        }

        public String getPS_DUE_DT() {
            return PS_DUE_DT;
        }

        public void setPS_DUE_DT(String PS_DUE_DT) {
            this.PS_DUE_DT = PS_DUE_DT;
        }

        public String getPAGE_COUNT() {
            return PAGE_COUNT;
        }

        public void setPAGE_COUNT(String PAGE_COUNT) {
            this.PAGE_COUNT = PAGE_COUNT;
        }

        public boolean isOpenFlag() {
            return openFlag;
        }

        public void setOpenFlag(boolean openFlag) {
            this.openFlag = openFlag;
        }
        public String getIS_CREDIT_REPORT() {
            return IS_CREDIT_REPORT;
        }

        public void setIS_CREDIT_REPORT(String IS_CREDIT_REPORT) {
            this.IS_CREDIT_REPORT = IS_CREDIT_REPORT;
        }

        public String getREPAYMENT_MODE() {
            return REPAYMENT_MODE;
        }

        public void setREPAYMENT_MODE(String REPAYMENT_MODE) {
            this.REPAYMENT_MODE = REPAYMENT_MODE;
        }

        public String getABSTRACT() {
            return ABSTRACT;
        }

        public void setABSTRACT(String ABSTRACT) {
            this.ABSTRACT = ABSTRACT;
        }
    }
}
