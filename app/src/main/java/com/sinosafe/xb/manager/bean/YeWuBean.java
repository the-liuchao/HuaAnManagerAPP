package com.sinosafe.xb.manager.bean;

import java.io.Serializable;

/**
 * 类名称：   com.cnmobi.huaan.manager.bean
 * 内容摘要： //业务。
 * 修改备注：
 * 创建时间： 2017/6/4 0004
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class YeWuBean implements Serializable{

    /**
     *证件号码
     */
    private String CERT_CODE;

    //身份证地址
    private String INDIV_BRT_PLACE;

    /**
     * 台帐状态
     0	出帐未确认
     1	正常
     2	核销
     9	结清
     3	撤销
     4	逾期
     5	超期
     */
    private String ACCOUNT_STATUS;

    /**
     *状态描述
     */
    private String APPROVE_DSC;
    /**
     * 借据协议编号
     */
    private String ACC_AGREE_NO;
    /**
     * 合作方类型
     */
    private String PARTNER_TYPE;
    /**
     *买方证件类型
     */
    private String BUYER_CERT_TYPE;
    /**
     * 推广时间
     */
    private String SPREAD_DATE;
    /**
     *罚息利率
     */
    private String CI_RATE;
    /**
     * 备注
     */
    private String REMARK;
    /**
     *暂时没使用
     */
    private String REFUSE_TIME;
    /**
     *商圈名称
     */
    private String CHANNEL_NAME;
    /**
     *贷款用途
     */
    private String LOAN_USE_TYPE;
    /**
     *是否合格(FACO评分)
     */
    private String IS_PASS;
    /**
     * 申请币种
     */
    private String APPLY_CUR_TYPE;
    /**
     * 担保人身份证号
     */
    private String GUARANTOR_CODE;
    /**
     * 联保协议名称
     */
    private String ASSURE_AGREE_NAME;
    /**
     * 业务模式
     */
    private String BIZ_MODE;
    /**
     *合作方名称
     */
    private String PARTNER_NAME;
    /**
     * 业务人员电话
     */
    private String BUSINESS_PERSION_PHONE;
    /**
     * 买方贷款金额
     */
    private String BUYER_AMOUNT;
    /**
     * 是否合作方
     */
    private String IS_FLAG;
    /**
     * 卖方客户编号
     */
    private String SELLER_CUS_ID;
    /**
     * MAIN_LEVEL 暂时没使用
     */
    private String MAIN_LEVEL;
    /**
     *所属商圈
     */
    private String GEO_LOC;
    /**
     * 申请金额（元）
     */
    private float AMOUNT;
    /**
     * 团队编号
     */
    private String TEAMNO;
    /**
     * 推广人
     */
    private String SPREAD_NAME;
    /**
     *
     */
    private String IS_MUST_RANSOM;
    /**
     * 是否涉农:STD_ZX_YES_NO 1-是 2-否
     */
    private String IS_AGRICULTURE;
    /**
     * 零钱袋放款状态
     */
    private String LOAN_STATUS;
    /**
     * 原房屋贷款余额
     */
    private String OLD_HOUSE_AMOUNT;

    private String PRD_TYPE;

    private String BUSINESS_PERSION_ID;

    private String USE_DEC;

    private String BUYER_CUS_ID;

    private String UPDATE_DATE;

    private String INPUT_LEVEL;

    private String TERM_TIME_TYPE;

    private String BANK_MGR_PHONE;

    private String PAYEE_BANK;

    private String UNASSURE_MEANS_ACTIONS;

    private String ADVISE_REPAYMENT_WAY;

    private String RANSOM_TYPE;

    private String OVERDUE_IR;

    private String CONT_PUBLIC;

    private String IMP_THI_SRC;

    private float COST_RATE;

    private String VALID_RESULT;

    private String SPREAD_TIME;

    private String APPLY_ADVISE;

    private String REALITY_IR_Y;

    private String BANK_CARD_NO;

    private String GUARANTEE_SERNO;

    private String PRJ_COST;

    private String ANALYSIS_DATE;

    private String SELLER_PHONE;

    private String PARTNER_CERT_CODE;

    private String APPLY_TERM;

    private String BUSINESS_BR_ID;

    private String PAY_TYPE;

    private String GUARANTOR_OCC;

    private String CUS_TYPE_NEW;

    private String SPREAD_PLACE;

    private String REPAY_ACCOUNT_NAME;

    private String INPUT_BR_NAME;

    private String FREQUENCY;

    private String BIZ_TYPE_SUB;

    private String REPAYMENT_SRC;

    private String COUNTY_CODE;

    private String CORP_DESC;

    private String GOODS_DETAIL_ID;

    private String BUYER_BANK_CODE;

    private String REPAYMENT_MODE;

    private String GUARANTY_NAME;

    private String IS_MORE_LOAN;

    private String SELLER_CERT_CODE;

    private String CUS_ID;

    private String IR_TYPE;

    private String ANOTHER_INFO_CHANNEL;

    private String AUTOAPPR;

    private String SERNO;

    private String COMPETENCE;

    private String CI_IR;

    private String LOAN_COORDINATE;

    private String FLOATING_TYPE;

    private String SELLER_CERT_TYPE;

    private String MODIFYREASON;

    private String MAY_GRT;

    private String PRD_ID;

    private String INPUT_BR_ID;

    private String INPUT_DATE;

    private String BUYER_BANK_NAME;

    private String OPER_PHONE;

    private String APP_STATUS;

    private String GRT_VALUE;

    private String ADVISE_REPAY_PERCENT;

    private String OPER_TEL_NO;

    private String INTEREST_ACC_MODE;

    private String RIGHTER_NAME;

    private String REPAY_PERCENT;

    private String NOT_THROUGH_REASON;

    private String HOUSE_CERT_NO;

    private String CREDIT_COORDINATE;

    private String BUSINESS_PERSION_NAME;

    private String REPAYMENT_MODE_DESC;

    private String CONT_NAME;

    private String RIGHTER_CERT_CODE;

    private String SPECIAL_FLAG;

    private String GUARANTOR_INCM;

    private String DUE_FIRST_DAY;

    private String APPLY_AMOUNT;

    private String DIRECTION_NAME;

    private String OPER_NAME;

    private String FLOATING_RATE;

    private String PAGE_COUNT;

    private String IS_CANCEL;

    private String ACCEPT_ID;

    private String PAY_DISTANCE;

    private String IS_ADD_CUS;

    private String HANDLE_TIME;

    private String VISA_INTERVIEW_COORDINATE;

    private String ASSURE_MEANS1;

    private String ACCEPT_LOAN_AMOUNT;

    private String MAIN_USER_ID;

    private String LOAN_REASON;

    private String APP_FORM;

    private String ASSURE_MEANS2;

    private String OVERDUE_RATE;

    private String CORP_GUAR_NAME;

    private String BELONG_BR_ID;

    private String o_LOAN_END_DATE;

    private String LOAN_TYPE;

    private String CAN_SINGLE;

    private String LOAN_FORM;

    private String LOAN_USE;

    private String IS_ELEC_INS;

    private String BUYER_CERT_CODE;

    private int TERM_DAY;

    private String REFUSE_REASON;

    private String EVAL_NET_VALUE;

    private String ADVISE_DATE;

    private String PARTNER_CUS_ID;

    private String SEND_IMAGE_STATUS;

    private String CUS_TYPE;

    private String ASSURE_AGREE_NO;

    private String IR_ADJUST_MODE;

    private String p2P_APPROVE_STATUS;

    private String PUSH_BR_ID;

    private String IS_BATC_BIZ;

    private String REFUSEREMARK;

    private String APPROVE_STATUS;

    private String INPUT_ID;

    private String RISK_ANALYSE;

    private String TERMINAL_SOURCE;

    private String SCHEME_TYPE;

    private String SPECIAL_REMARK;

    private String IS_WORTH;

    private String BUYER_CUS_TYPE;

    private String CUS_NAME;

    private String ASSI_NAME;

    private String PRD_NAME;

    private String IS_ORDER;

    private String LOAN_USE_DESC;

    private String BUYER_CUS_NAME;

    private String REPAY_BANK;

    private String PARTNER_NO;

    private String DEFAULT_RATE;

    private String CACCEL_LOAN_REASON;

    private String ASSURE_MEANS_MAIN;

    private String OPT_TYPE;

    private String APPLY_DATE;

    private int PERIOD;

    private String c_APPNT;

    private String PRJ_STEP;

    private String PHONE;

    private String SPECIAL_CASE;

    private String SUBORGANNO;

    private String ADVISE_GUAR_PERSON;

    private String TEAM_LEADER;

    private String INFO_CHANNEL;

    private String CHANNEL_NO;

    private String BUYER_PHONE;

    private String BIZ_TYPE;

    private String GUARANTY_TYPE;

    private String IS_SEND_LOAN;

    private String GUARANTOR_PHONE;

    private String GROUP_LEADER;

    private String FICO_RECORD_ID;

    private String BUSI_STATUS;

    private String DEFAULT_IR;

    private String INPUT_NAME;

    private String IS_UPDATE;

    private String FICO_SCORE;

    private String BUYER_FIRST_AMOUNT;

    private String CERT_TYPE;

    private String MAIN_USER_NAME;

    private String IS_DISTRIBUTION;

    private String BANK_CUS_MGR;

    private String SELLER_CUS_TYPE;

    private int TERM;

    private String IS_ASSURE;

    private String IS_OTHER_PLACE;

    private String JIRA_NO;

    private String IS_RECORD;

    private String CUS_MGR;

    private String RISK_RESULT;

    private String CACCEL_LOAN_REMARKS;

    private String LOAN_DIRECTION;

    private String RULE_LOAN_AMT;

    private String MAIN_BR_ID;

    private String IS_CREDIT_IMPLEMENTATION;

    private String TOTAL_NESS_RATE;

    private String MANAGER_ADVICE;

    private String PROP;

    private String ADVISE_AMT;

    private String APP_WORKFLOW_ID;

    private String LOAN_BALANCE;

    private String ACCEPT_NAME;

    private String INPUT_CHANNEL;

    private String OTHER_REMARK;

    private String NEW_APPROVE_STATUS;

    private String o_CONT_NO;

    private String PAYEE_ACCOUNT;

    private String DUE_DAY;

    private String IS_GRT_THIRD;

    private String RULING_IR;

    private String ADVISE_DEADLINE;

    private float USING_IR;

    private String SELLER_CUS_NAME;

    private String DAYS_OF_GRACE;

    private String IS_AUTO_APPROVE;

    private String BID_CUS_ID;

    private String o_USING_IR;

    private String REFUSE_CODE;

    private String ADVISE_REPAYMENT_WAY_DESC;

    private String REPAY_ACCOUNT;

    private String GROUPNO;

    private String ADVISE_TERM;

    private String IS_FACE_SIGN;

    private String RIGHTER_CERT_TYPE;

    private String SPREAD_PLACE_DESC;

    private String o_LOAN_START_DATE;

    private String CANCEL_FAIL_CAUSE;

    private String GUARANTOR_RELATE;

    private String READ_FLAG;

    private String o_BILL_NO;

    private String PAYEE_ACCOUNT_NAME;

    private String SPRD_RATE;

    private String PARTNER_CERT_TYPE;

    private String CORP_RELATE;

    private String IS_ALREADY;

    private String USE_DETAIL;

    private String GUARANTOR;

    private String USE_DATE;

    private String ASSI_ID;

    private String CUS_SEX;

    private User user;
    //居住地址区划名称
    private String INDIV_RSD_PLE;
    //居住住址
    private String INDIV_RSD_ADDR;

    //婚姻状况 0：未婚；1：已婚
    private String INDIV_MAR_ST;

    //配偶姓名
    private String INDIV_SPS_NAME;

    //配偶证件号码
    private String INDIV_SPS_ID_CODE;
    //private String INDIV_COM_NAME;

    //单位性质
    private String INDIV_COM_TYP;
    //企业生意名称
    private String BUSIN_NAME;
    //企业证件号码
    private String OPER_NO;

    //是否已上传贷后凭证1：待传；0：已上传
    private String IS_LOAN_CHECK;


    public String getINDIV_BRT_PLACE() {
        return INDIV_BRT_PLACE;
    }

    public void setINDIV_BRT_PLACE(String INDIV_BRT_PLACE) {
        this.INDIV_BRT_PLACE = INDIV_BRT_PLACE;
    }

    public String getIS_LOAN_CHECK() {
        return IS_LOAN_CHECK;
    }

    public void setIS_LOAN_CHECK(String IS_LOAN_CHECK) {
        this.IS_LOAN_CHECK = IS_LOAN_CHECK;
    }

    public String getCERT_CODE() {
        return CERT_CODE;
    }

    public void setCERT_CODE(String CERT_CODE) {
        this.CERT_CODE = CERT_CODE;
    }

    public String getAPPROVE_DSC() {
        return APPROVE_DSC;
    }

    public void setAPPROVE_DSC(String APPROVE_DSC) {
        this.APPROVE_DSC = APPROVE_DSC;
    }

    public String getACC_AGREE_NO() {
        return ACC_AGREE_NO;
    }

    public void setACC_AGREE_NO(String ACC_AGREE_NO) {
        this.ACC_AGREE_NO = ACC_AGREE_NO;
    }

    public String getPARTNER_TYPE() {
        return PARTNER_TYPE;
    }

    public void setPARTNER_TYPE(String PARTNER_TYPE) {
        this.PARTNER_TYPE = PARTNER_TYPE;
    }

    public String getBUYER_CERT_TYPE() {
        return BUYER_CERT_TYPE;
    }

    public void setBUYER_CERT_TYPE(String BUYER_CERT_TYPE) {
        this.BUYER_CERT_TYPE = BUYER_CERT_TYPE;
    }

    public String getSPREAD_DATE() {
        return SPREAD_DATE;
    }

    public void setSPREAD_DATE(String SPREAD_DATE) {
        this.SPREAD_DATE = SPREAD_DATE;
    }

    public String getCI_RATE() {
        return CI_RATE;
    }

    public void setCI_RATE(String CI_RATE) {
        this.CI_RATE = CI_RATE;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }

    public String getREFUSE_TIME() {
        return REFUSE_TIME;
    }

    public void setREFUSE_TIME(String REFUSE_TIME) {
        this.REFUSE_TIME = REFUSE_TIME;
    }

    public String getCHANNEL_NAME() {
        return CHANNEL_NAME;
    }

    public void setCHANNEL_NAME(String CHANNEL_NAME) {
        this.CHANNEL_NAME = CHANNEL_NAME;
    }

    public String getLOAN_USE_TYPE() {
        return LOAN_USE_TYPE;
    }

    public void setLOAN_USE_TYPE(String LOAN_USE_TYPE) {
        this.LOAN_USE_TYPE = LOAN_USE_TYPE;
    }

    public String getIS_PASS() {
        return IS_PASS;
    }

    public void setIS_PASS(String IS_PASS) {
        this.IS_PASS = IS_PASS;
    }

    public String getAPPLY_CUR_TYPE() {
        return APPLY_CUR_TYPE;
    }

    public void setAPPLY_CUR_TYPE(String APPLY_CUR_TYPE) {
        this.APPLY_CUR_TYPE = APPLY_CUR_TYPE;
    }

    public String getGUARANTOR_CODE() {
        return GUARANTOR_CODE;
    }

    public void setGUARANTOR_CODE(String GUARANTOR_CODE) {
        this.GUARANTOR_CODE = GUARANTOR_CODE;
    }

    public String getASSURE_AGREE_NAME() {
        return ASSURE_AGREE_NAME;
    }

    public void setASSURE_AGREE_NAME(String ASSURE_AGREE_NAME) {
        this.ASSURE_AGREE_NAME = ASSURE_AGREE_NAME;
    }

    public String getBIZ_MODE() {
        return BIZ_MODE;
    }

    public void setBIZ_MODE(String BIZ_MODE) {
        this.BIZ_MODE = BIZ_MODE;
    }

    public String getPARTNER_NAME() {
        return PARTNER_NAME;
    }

    public void setPARTNER_NAME(String PARTNER_NAME) {
        this.PARTNER_NAME = PARTNER_NAME;
    }

    public String getBUSINESS_PERSION_PHONE() {
        return BUSINESS_PERSION_PHONE;
    }

    public void setBUSINESS_PERSION_PHONE(String BUSINESS_PERSION_PHONE) {
        this.BUSINESS_PERSION_PHONE = BUSINESS_PERSION_PHONE;
    }

    public String getBUYER_AMOUNT() {
        return BUYER_AMOUNT;
    }

    public void setBUYER_AMOUNT(String BUYER_AMOUNT) {
        this.BUYER_AMOUNT = BUYER_AMOUNT;
    }

    public String getIS_FLAG() {
        return IS_FLAG;
    }

    public void setIS_FLAG(String IS_FLAG) {
        this.IS_FLAG = IS_FLAG;
    }

    public String getSELLER_CUS_ID() {
        return SELLER_CUS_ID;
    }

    public void setSELLER_CUS_ID(String SELLER_CUS_ID) {
        this.SELLER_CUS_ID = SELLER_CUS_ID;
    }

    public String getMAIN_LEVEL() {
        return MAIN_LEVEL;
    }

    public void setMAIN_LEVEL(String MAIN_LEVEL) {
        this.MAIN_LEVEL = MAIN_LEVEL;
    }

    public String getGEO_LOC() {
        return GEO_LOC;
    }

    public void setGEO_LOC(String GEO_LOC) {
        this.GEO_LOC = GEO_LOC;
    }

    public float getAMOUNT() {
        return AMOUNT;
    }

    public void setAMOUNT(float AMOUNT) {
        this.AMOUNT = AMOUNT;
    }

    public String getTEAMNO() {
        return TEAMNO;
    }

    public void setTEAMNO(String TEAMNO) {
        this.TEAMNO = TEAMNO;
    }

    public String getSPREAD_NAME() {
        return SPREAD_NAME;
    }

    public void setSPREAD_NAME(String SPREAD_NAME) {
        this.SPREAD_NAME = SPREAD_NAME;
    }

    public String getIS_MUST_RANSOM() {
        return IS_MUST_RANSOM;
    }

    public void setIS_MUST_RANSOM(String IS_MUST_RANSOM) {
        this.IS_MUST_RANSOM = IS_MUST_RANSOM;
    }

    public String getIS_AGRICULTURE() {
        return IS_AGRICULTURE;
    }

    public void setIS_AGRICULTURE(String IS_AGRICULTURE) {
        this.IS_AGRICULTURE = IS_AGRICULTURE;
    }

    public String getLOAN_STATUS() {
        return LOAN_STATUS;
    }

    public void setLOAN_STATUS(String LOAN_STATUS) {
        this.LOAN_STATUS = LOAN_STATUS;
    }

    public String getOLD_HOUSE_AMOUNT() {
        return OLD_HOUSE_AMOUNT;
    }

    public void setOLD_HOUSE_AMOUNT(String OLD_HOUSE_AMOUNT) {
        this.OLD_HOUSE_AMOUNT = OLD_HOUSE_AMOUNT;
    }

    public String getBUSINESS_PERSION_ID() {
        return BUSINESS_PERSION_ID;
    }

    public void setBUSINESS_PERSION_ID(String BUSINESS_PERSION_ID) {
        this.BUSINESS_PERSION_ID = BUSINESS_PERSION_ID;
    }

    public String getUSE_DEC() {
        return USE_DEC;
    }

    public void setUSE_DEC(String USE_DEC) {
        this.USE_DEC = USE_DEC;
    }

    public String getBUYER_CUS_ID() {
        return BUYER_CUS_ID;
    }

    public void setBUYER_CUS_ID(String BUYER_CUS_ID) {
        this.BUYER_CUS_ID = BUYER_CUS_ID;
    }

    public String getUPDATE_DATE() {
        return UPDATE_DATE;
    }

    public void setUPDATE_DATE(String UPDATE_DATE) {
        this.UPDATE_DATE = UPDATE_DATE;
    }

    public String getINPUT_LEVEL() {
        return INPUT_LEVEL;
    }

    public void setINPUT_LEVEL(String INPUT_LEVEL) {
        this.INPUT_LEVEL = INPUT_LEVEL;
    }

    public String getTERM_TIME_TYPE() {
        return TERM_TIME_TYPE;
    }

    public void setTERM_TIME_TYPE(String TERM_TIME_TYPE) {
        this.TERM_TIME_TYPE = TERM_TIME_TYPE;
    }

    public String getBANK_MGR_PHONE() {
        return BANK_MGR_PHONE;
    }

    public void setBANK_MGR_PHONE(String BANK_MGR_PHONE) {
        this.BANK_MGR_PHONE = BANK_MGR_PHONE;
    }

    public String getPAYEE_BANK() {
        return PAYEE_BANK;
    }

    public void setPAYEE_BANK(String PAYEE_BANK) {
        this.PAYEE_BANK = PAYEE_BANK;
    }

    public String getUNASSURE_MEANS_ACTIONS() {
        return UNASSURE_MEANS_ACTIONS;
    }

    public void setUNASSURE_MEANS_ACTIONS(String UNASSURE_MEANS_ACTIONS) {
        this.UNASSURE_MEANS_ACTIONS = UNASSURE_MEANS_ACTIONS;
    }

    public String getADVISE_REPAYMENT_WAY() {
        return ADVISE_REPAYMENT_WAY;
    }

    public void setADVISE_REPAYMENT_WAY(String ADVISE_REPAYMENT_WAY) {
        this.ADVISE_REPAYMENT_WAY = ADVISE_REPAYMENT_WAY;
    }

    public String getRANSOM_TYPE() {
        return RANSOM_TYPE;
    }

    public void setRANSOM_TYPE(String RANSOM_TYPE) {
        this.RANSOM_TYPE = RANSOM_TYPE;
    }

    public String getOVERDUE_IR() {
        return OVERDUE_IR;
    }

    public void setOVERDUE_IR(String OVERDUE_IR) {
        this.OVERDUE_IR = OVERDUE_IR;
    }

    public String getCONT_PUBLIC() {
        return CONT_PUBLIC;
    }

    public void setCONT_PUBLIC(String CONT_PUBLIC) {
        this.CONT_PUBLIC = CONT_PUBLIC;
    }

    public String getIMP_THI_SRC() {
        return IMP_THI_SRC;
    }

    public void setIMP_THI_SRC(String IMP_THI_SRC) {
        this.IMP_THI_SRC = IMP_THI_SRC;
    }

    public float getCOST_RATE() {
        return COST_RATE;
    }

    public void setCOST_RATE(float COST_RATE) {
        this.COST_RATE = COST_RATE;
    }

    public String getVALID_RESULT() {
        return VALID_RESULT;
    }

    public void setVALID_RESULT(String VALID_RESULT) {
        this.VALID_RESULT = VALID_RESULT;
    }

    public String getSPREAD_TIME() {
        return SPREAD_TIME;
    }

    public void setSPREAD_TIME(String SPREAD_TIME) {
        this.SPREAD_TIME = SPREAD_TIME;
    }

    public String getAPPLY_ADVISE() {
        return APPLY_ADVISE;
    }

    public void setAPPLY_ADVISE(String APPLY_ADVISE) {
        this.APPLY_ADVISE = APPLY_ADVISE;
    }

    public String getREALITY_IR_Y() {
        return REALITY_IR_Y;
    }

    public void setREALITY_IR_Y(String REALITY_IR_Y) {
        this.REALITY_IR_Y = REALITY_IR_Y;
    }

    public String getBANK_CARD_NO() {
        return BANK_CARD_NO;
    }

    public void setBANK_CARD_NO(String BANK_CARD_NO) {
        this.BANK_CARD_NO = BANK_CARD_NO;
    }

    public String getGUARANTEE_SERNO() {
        return GUARANTEE_SERNO;
    }

    public void setGUARANTEE_SERNO(String GUARANTEE_SERNO) {
        this.GUARANTEE_SERNO = GUARANTEE_SERNO;
    }

    public String getPRJ_COST() {
        return PRJ_COST;
    }

    public void setPRJ_COST(String PRJ_COST) {
        this.PRJ_COST = PRJ_COST;
    }

    public String getANALYSIS_DATE() {
        return ANALYSIS_DATE;
    }

    public void setANALYSIS_DATE(String ANALYSIS_DATE) {
        this.ANALYSIS_DATE = ANALYSIS_DATE;
    }

    public String getSELLER_PHONE() {
        return SELLER_PHONE;
    }

    public void setSELLER_PHONE(String SELLER_PHONE) {
        this.SELLER_PHONE = SELLER_PHONE;
    }

    public String getPARTNER_CERT_CODE() {
        return PARTNER_CERT_CODE;
    }

    public void setPARTNER_CERT_CODE(String PARTNER_CERT_CODE) {
        this.PARTNER_CERT_CODE = PARTNER_CERT_CODE;
    }

    public String getAPPLY_TERM() {
        return APPLY_TERM;
    }

    public void setAPPLY_TERM(String APPLY_TERM) {
        this.APPLY_TERM = APPLY_TERM;
    }

    public String getBUSINESS_BR_ID() {
        return BUSINESS_BR_ID;
    }

    public void setBUSINESS_BR_ID(String BUSINESS_BR_ID) {
        this.BUSINESS_BR_ID = BUSINESS_BR_ID;
    }

    public String getPAY_TYPE() {
        return PAY_TYPE;
    }

    public void setPAY_TYPE(String PAY_TYPE) {
        this.PAY_TYPE = PAY_TYPE;
    }

    public String getGUARANTOR_OCC() {
        return GUARANTOR_OCC;
    }

    public void setGUARANTOR_OCC(String GUARANTOR_OCC) {
        this.GUARANTOR_OCC = GUARANTOR_OCC;
    }

    public String getCUS_TYPE_NEW() {
        return CUS_TYPE_NEW;
    }

    public void setCUS_TYPE_NEW(String CUS_TYPE_NEW) {
        this.CUS_TYPE_NEW = CUS_TYPE_NEW;
    }

    public String getSPREAD_PLACE() {
        return SPREAD_PLACE;
    }

    public void setSPREAD_PLACE(String SPREAD_PLACE) {
        this.SPREAD_PLACE = SPREAD_PLACE;
    }

    public String getREPAY_ACCOUNT_NAME() {
        return REPAY_ACCOUNT_NAME;
    }

    public void setREPAY_ACCOUNT_NAME(String REPAY_ACCOUNT_NAME) {
        this.REPAY_ACCOUNT_NAME = REPAY_ACCOUNT_NAME;
    }

    public String getINPUT_BR_NAME() {
        return INPUT_BR_NAME;
    }

    public void setINPUT_BR_NAME(String INPUT_BR_NAME) {
        this.INPUT_BR_NAME = INPUT_BR_NAME;
    }

    public String getFREQUENCY() {
        return FREQUENCY;
    }

    public void setFREQUENCY(String FREQUENCY) {
        this.FREQUENCY = FREQUENCY;
    }

    public String getBIZ_TYPE_SUB() {
        return BIZ_TYPE_SUB;
    }

    public void setBIZ_TYPE_SUB(String BIZ_TYPE_SUB) {
        this.BIZ_TYPE_SUB = BIZ_TYPE_SUB;
    }

    public String getREPAYMENT_SRC() {
        return REPAYMENT_SRC;
    }

    public void setREPAYMENT_SRC(String REPAYMENT_SRC) {
        this.REPAYMENT_SRC = REPAYMENT_SRC;
    }

    public String getCOUNTY_CODE() {
        return COUNTY_CODE;
    }

    public void setCOUNTY_CODE(String COUNTY_CODE) {
        this.COUNTY_CODE = COUNTY_CODE;
    }

    public String getCORP_DESC() {
        return CORP_DESC;
    }

    public void setCORP_DESC(String CORP_DESC) {
        this.CORP_DESC = CORP_DESC;
    }

    public String getGOODS_DETAIL_ID() {
        return GOODS_DETAIL_ID;
    }

    public void setGOODS_DETAIL_ID(String GOODS_DETAIL_ID) {
        this.GOODS_DETAIL_ID = GOODS_DETAIL_ID;
    }

    public String getBUYER_BANK_CODE() {
        return BUYER_BANK_CODE;
    }

    public void setBUYER_BANK_CODE(String BUYER_BANK_CODE) {
        this.BUYER_BANK_CODE = BUYER_BANK_CODE;
    }

    public String getREPAYMENT_MODE() {
        return REPAYMENT_MODE;
    }

    public void setREPAYMENT_MODE(String REPAYMENT_MODE) {
        this.REPAYMENT_MODE = REPAYMENT_MODE;
    }

    public String getGUARANTY_NAME() {
        return GUARANTY_NAME;
    }

    public void setGUARANTY_NAME(String GUARANTY_NAME) {
        this.GUARANTY_NAME = GUARANTY_NAME;
    }

    public String getIS_MORE_LOAN() {
        return IS_MORE_LOAN;
    }

    public void setIS_MORE_LOAN(String IS_MORE_LOAN) {
        this.IS_MORE_LOAN = IS_MORE_LOAN;
    }

    public String getSELLER_CERT_CODE() {
        return SELLER_CERT_CODE;
    }

    public void setSELLER_CERT_CODE(String SELLER_CERT_CODE) {
        this.SELLER_CERT_CODE = SELLER_CERT_CODE;
    }

    public String getCUS_ID() {
        return CUS_ID;
    }

    public void setCUS_ID(String CUS_ID) {
        this.CUS_ID = CUS_ID;
    }

    public String getIR_TYPE() {
        return IR_TYPE;
    }

    public void setIR_TYPE(String IR_TYPE) {
        this.IR_TYPE = IR_TYPE;
    }

    public String getANOTHER_INFO_CHANNEL() {
        return ANOTHER_INFO_CHANNEL;
    }

    public void setANOTHER_INFO_CHANNEL(String ANOTHER_INFO_CHANNEL) {
        this.ANOTHER_INFO_CHANNEL = ANOTHER_INFO_CHANNEL;
    }

    public String getAUTOAPPR() {
        return AUTOAPPR;
    }

    public void setAUTOAPPR(String AUTOAPPR) {
        this.AUTOAPPR = AUTOAPPR;
    }

    public String getSERNO() {
        return SERNO;
    }

    public void setSERNO(String SERNO) {
        this.SERNO = SERNO;
    }

    public String getCOMPETENCE() {
        return COMPETENCE;
    }

    public void setCOMPETENCE(String COMPETENCE) {
        this.COMPETENCE = COMPETENCE;
    }

    public String getCI_IR() {
        return CI_IR;
    }

    public void setCI_IR(String CI_IR) {
        this.CI_IR = CI_IR;
    }

    public String getLOAN_COORDINATE() {
        return LOAN_COORDINATE;
    }

    public void setLOAN_COORDINATE(String LOAN_COORDINATE) {
        this.LOAN_COORDINATE = LOAN_COORDINATE;
    }

    public String getFLOATING_TYPE() {
        return FLOATING_TYPE;
    }

    public void setFLOATING_TYPE(String FLOATING_TYPE) {
        this.FLOATING_TYPE = FLOATING_TYPE;
    }

    public String getSELLER_CERT_TYPE() {
        return SELLER_CERT_TYPE;
    }

    public void setSELLER_CERT_TYPE(String SELLER_CERT_TYPE) {
        this.SELLER_CERT_TYPE = SELLER_CERT_TYPE;
    }

    public String getMODIFYREASON() {
        return MODIFYREASON;
    }

    public void setMODIFYREASON(String MODIFYREASON) {
        this.MODIFYREASON = MODIFYREASON;
    }

    public String getMAY_GRT() {
        return MAY_GRT;
    }

    public void setMAY_GRT(String MAY_GRT) {
        this.MAY_GRT = MAY_GRT;
    }

    public String getPRD_ID() {
        return PRD_ID;
    }

    public void setPRD_ID(String PRD_ID) {
        this.PRD_ID = PRD_ID;
    }

    public String getINPUT_BR_ID() {
        return INPUT_BR_ID;
    }

    public void setINPUT_BR_ID(String INPUT_BR_ID) {
        this.INPUT_BR_ID = INPUT_BR_ID;
    }

    public String getINPUT_DATE() {
        return INPUT_DATE;
    }

    public void setINPUT_DATE(String INPUT_DATE) {
        this.INPUT_DATE = INPUT_DATE;
    }

    public String getBUYER_BANK_NAME() {
        return BUYER_BANK_NAME;
    }

    public void setBUYER_BANK_NAME(String BUYER_BANK_NAME) {
        this.BUYER_BANK_NAME = BUYER_BANK_NAME;
    }

    public String getOPER_PHONE() {
        return OPER_PHONE;
    }

    public void setOPER_PHONE(String OPER_PHONE) {
        this.OPER_PHONE = OPER_PHONE;
    }

    public String getAPP_STATUS() {
        return APP_STATUS;
    }

    public void setAPP_STATUS(String APP_STATUS) {
        this.APP_STATUS = APP_STATUS;
    }

    public String getGRT_VALUE() {
        return GRT_VALUE;
    }

    public void setGRT_VALUE(String GRT_VALUE) {
        this.GRT_VALUE = GRT_VALUE;
    }

    public String getADVISE_REPAY_PERCENT() {
        return ADVISE_REPAY_PERCENT;
    }

    public void setADVISE_REPAY_PERCENT(String ADVISE_REPAY_PERCENT) {
        this.ADVISE_REPAY_PERCENT = ADVISE_REPAY_PERCENT;
    }

    public String getOPER_TEL_NO() {
        return OPER_TEL_NO;
    }

    public void setOPER_TEL_NO(String OPER_TEL_NO) {
        this.OPER_TEL_NO = OPER_TEL_NO;
    }

    public String getINTEREST_ACC_MODE() {
        return INTEREST_ACC_MODE;
    }

    public void setINTEREST_ACC_MODE(String INTEREST_ACC_MODE) {
        this.INTEREST_ACC_MODE = INTEREST_ACC_MODE;
    }

    public String getRIGHTER_NAME() {
        return RIGHTER_NAME;
    }

    public void setRIGHTER_NAME(String RIGHTER_NAME) {
        this.RIGHTER_NAME = RIGHTER_NAME;
    }

    public String getREPAY_PERCENT() {
        return REPAY_PERCENT;
    }

    public void setREPAY_PERCENT(String REPAY_PERCENT) {
        this.REPAY_PERCENT = REPAY_PERCENT;
    }

    public String getNOT_THROUGH_REASON() {
        return NOT_THROUGH_REASON;
    }

    public void setNOT_THROUGH_REASON(String NOT_THROUGH_REASON) {
        this.NOT_THROUGH_REASON = NOT_THROUGH_REASON;
    }

    public String getHOUSE_CERT_NO() {
        return HOUSE_CERT_NO;
    }

    public void setHOUSE_CERT_NO(String HOUSE_CERT_NO) {
        this.HOUSE_CERT_NO = HOUSE_CERT_NO;
    }

    public String getCREDIT_COORDINATE() {
        return CREDIT_COORDINATE;
    }

    public void setCREDIT_COORDINATE(String CREDIT_COORDINATE) {
        this.CREDIT_COORDINATE = CREDIT_COORDINATE;
    }

    public String getBUSINESS_PERSION_NAME() {
        return BUSINESS_PERSION_NAME;
    }

    public void setBUSINESS_PERSION_NAME(String BUSINESS_PERSION_NAME) {
        this.BUSINESS_PERSION_NAME = BUSINESS_PERSION_NAME;
    }

    public String getREPAYMENT_MODE_DESC() {
        return REPAYMENT_MODE_DESC;
    }

    public void setREPAYMENT_MODE_DESC(String REPAYMENT_MODE_DESC) {
        this.REPAYMENT_MODE_DESC = REPAYMENT_MODE_DESC;
    }

    public String getCONT_NAME() {
        return CONT_NAME;
    }

    public void setCONT_NAME(String CONT_NAME) {
        this.CONT_NAME = CONT_NAME;
    }

    public String getRIGHTER_CERT_CODE() {
        return RIGHTER_CERT_CODE;
    }

    public void setRIGHTER_CERT_CODE(String RIGHTER_CERT_CODE) {
        this.RIGHTER_CERT_CODE = RIGHTER_CERT_CODE;
    }

    public String getSPECIAL_FLAG() {
        return SPECIAL_FLAG;
    }

    public void setSPECIAL_FLAG(String SPECIAL_FLAG) {
        this.SPECIAL_FLAG = SPECIAL_FLAG;
    }

    public String getGUARANTOR_INCM() {
        return GUARANTOR_INCM;
    }

    public void setGUARANTOR_INCM(String GUARANTOR_INCM) {
        this.GUARANTOR_INCM = GUARANTOR_INCM;
    }

    public String getDUE_FIRST_DAY() {
        return DUE_FIRST_DAY;
    }

    public void setDUE_FIRST_DAY(String DUE_FIRST_DAY) {
        this.DUE_FIRST_DAY = DUE_FIRST_DAY;
    }

    public String getAPPLY_AMOUNT() {
        return APPLY_AMOUNT;
    }

    public void setAPPLY_AMOUNT(String APPLY_AMOUNT) {
        this.APPLY_AMOUNT = APPLY_AMOUNT;
    }

    public String getDIRECTION_NAME() {
        return DIRECTION_NAME;
    }

    public void setDIRECTION_NAME(String DIRECTION_NAME) {
        this.DIRECTION_NAME = DIRECTION_NAME;
    }

    public String getOPER_NAME() {
        return OPER_NAME;
    }

    public void setOPER_NAME(String OPER_NAME) {
        this.OPER_NAME = OPER_NAME;
    }

    public String getFLOATING_RATE() {
        return FLOATING_RATE;
    }

    public void setFLOATING_RATE(String FLOATING_RATE) {
        this.FLOATING_RATE = FLOATING_RATE;
    }

    public String getPAGE_COUNT() {
        return PAGE_COUNT;
    }

    public void setPAGE_COUNT(String PAGE_COUNT) {
        this.PAGE_COUNT = PAGE_COUNT;
    }

    public String getIS_CANCEL() {
        return IS_CANCEL;
    }

    public void setIS_CANCEL(String IS_CANCEL) {
        this.IS_CANCEL = IS_CANCEL;
    }

    public String getACCEPT_ID() {
        return ACCEPT_ID;
    }

    public void setACCEPT_ID(String ACCEPT_ID) {
        this.ACCEPT_ID = ACCEPT_ID;
    }

    public String getPAY_DISTANCE() {
        return PAY_DISTANCE;
    }

    public void setPAY_DISTANCE(String PAY_DISTANCE) {
        this.PAY_DISTANCE = PAY_DISTANCE;
    }

    public String getIS_ADD_CUS() {
        return IS_ADD_CUS;
    }

    public void setIS_ADD_CUS(String IS_ADD_CUS) {
        this.IS_ADD_CUS = IS_ADD_CUS;
    }

    public String getHANDLE_TIME() {
        return HANDLE_TIME;
    }

    public void setHANDLE_TIME(String HANDLE_TIME) {
        this.HANDLE_TIME = HANDLE_TIME;
    }

    public String getVISA_INTERVIEW_COORDINATE() {
        return VISA_INTERVIEW_COORDINATE;
    }

    public void setVISA_INTERVIEW_COORDINATE(String VISA_INTERVIEW_COORDINATE) {
        this.VISA_INTERVIEW_COORDINATE = VISA_INTERVIEW_COORDINATE;
    }

    public String getASSURE_MEANS1() {
        return ASSURE_MEANS1;
    }

    public void setASSURE_MEANS1(String ASSURE_MEANS1) {
        this.ASSURE_MEANS1 = ASSURE_MEANS1;
    }

    public String getACCEPT_LOAN_AMOUNT() {
        return ACCEPT_LOAN_AMOUNT;
    }

    public void setACCEPT_LOAN_AMOUNT(String ACCEPT_LOAN_AMOUNT) {
        this.ACCEPT_LOAN_AMOUNT = ACCEPT_LOAN_AMOUNT;
    }

    public String getMAIN_USER_ID() {
        return MAIN_USER_ID;
    }

    public void setMAIN_USER_ID(String MAIN_USER_ID) {
        this.MAIN_USER_ID = MAIN_USER_ID;
    }

    public String getLOAN_REASON() {
        return LOAN_REASON;
    }

    public void setLOAN_REASON(String LOAN_REASON) {
        this.LOAN_REASON = LOAN_REASON;
    }

    public String getAPP_FORM() {
        return APP_FORM;
    }

    public void setAPP_FORM(String APP_FORM) {
        this.APP_FORM = APP_FORM;
    }

    public String getASSURE_MEANS2() {
        return ASSURE_MEANS2;
    }

    public void setASSURE_MEANS2(String ASSURE_MEANS2) {
        this.ASSURE_MEANS2 = ASSURE_MEANS2;
    }

    public String getOVERDUE_RATE() {
        return OVERDUE_RATE;
    }

    public void setOVERDUE_RATE(String OVERDUE_RATE) {
        this.OVERDUE_RATE = OVERDUE_RATE;
    }

    public String getCORP_GUAR_NAME() {
        return CORP_GUAR_NAME;
    }

    public void setCORP_GUAR_NAME(String CORP_GUAR_NAME) {
        this.CORP_GUAR_NAME = CORP_GUAR_NAME;
    }

    public String getBELONG_BR_ID() {
        return BELONG_BR_ID;
    }

    public void setBELONG_BR_ID(String BELONG_BR_ID) {
        this.BELONG_BR_ID = BELONG_BR_ID;
    }

    public String getO_LOAN_END_DATE() {
        return o_LOAN_END_DATE;
    }

    public void setO_LOAN_END_DATE(String o_LOAN_END_DATE) {
        this.o_LOAN_END_DATE = o_LOAN_END_DATE;
    }

    public String getLOAN_TYPE() {
        return LOAN_TYPE;
    }

    public void setLOAN_TYPE(String LOAN_TYPE) {
        this.LOAN_TYPE = LOAN_TYPE;
    }

    public String getCAN_SINGLE() {
        return CAN_SINGLE;
    }

    public void setCAN_SINGLE(String CAN_SINGLE) {
        this.CAN_SINGLE = CAN_SINGLE;
    }

    public String getLOAN_FORM() {
        return LOAN_FORM;
    }

    public void setLOAN_FORM(String LOAN_FORM) {
        this.LOAN_FORM = LOAN_FORM;
    }

    public String getLOAN_USE() {
        return LOAN_USE;
    }

    public void setLOAN_USE(String LOAN_USE) {
        this.LOAN_USE = LOAN_USE;
    }

    public String getIS_ELEC_INS() {
        return IS_ELEC_INS;
    }

    public void setIS_ELEC_INS(String IS_ELEC_INS) {
        this.IS_ELEC_INS = IS_ELEC_INS;
    }

    public String getBUYER_CERT_CODE() {
        return BUYER_CERT_CODE;
    }

    public void setBUYER_CERT_CODE(String BUYER_CERT_CODE) {
        this.BUYER_CERT_CODE = BUYER_CERT_CODE;
    }

    public int getTERM_DAY() {
        return TERM_DAY;
    }

    public void setTERM_DAY(int TERM_DAY) {
        this.TERM_DAY = TERM_DAY;
    }

    public String getREFUSE_REASON() {
        return REFUSE_REASON;
    }

    public void setREFUSE_REASON(String REFUSE_REASON) {
        this.REFUSE_REASON = REFUSE_REASON;
    }

    public String getEVAL_NET_VALUE() {
        return EVAL_NET_VALUE;
    }

    public void setEVAL_NET_VALUE(String EVAL_NET_VALUE) {
        this.EVAL_NET_VALUE = EVAL_NET_VALUE;
    }

    public String getADVISE_DATE() {
        return ADVISE_DATE;
    }

    public void setADVISE_DATE(String ADVISE_DATE) {
        this.ADVISE_DATE = ADVISE_DATE;
    }

    public String getPARTNER_CUS_ID() {
        return PARTNER_CUS_ID;
    }

    public void setPARTNER_CUS_ID(String PARTNER_CUS_ID) {
        this.PARTNER_CUS_ID = PARTNER_CUS_ID;
    }

    public String getSEND_IMAGE_STATUS() {
        return SEND_IMAGE_STATUS;
    }

    public void setSEND_IMAGE_STATUS(String SEND_IMAGE_STATUS) {
        this.SEND_IMAGE_STATUS = SEND_IMAGE_STATUS;
    }

    public String getCUS_TYPE() {
        return CUS_TYPE;
    }

    public void setCUS_TYPE(String CUS_TYPE) {
        this.CUS_TYPE = CUS_TYPE;
    }

    public String getASSURE_AGREE_NO() {
        return ASSURE_AGREE_NO;
    }

    public void setASSURE_AGREE_NO(String ASSURE_AGREE_NO) {
        this.ASSURE_AGREE_NO = ASSURE_AGREE_NO;
    }

    public String getIR_ADJUST_MODE() {
        return IR_ADJUST_MODE;
    }

    public void setIR_ADJUST_MODE(String IR_ADJUST_MODE) {
        this.IR_ADJUST_MODE = IR_ADJUST_MODE;
    }

    public String getP2P_APPROVE_STATUS() {
        return p2P_APPROVE_STATUS;
    }

    public void setP2P_APPROVE_STATUS(String p2P_APPROVE_STATUS) {
        this.p2P_APPROVE_STATUS = p2P_APPROVE_STATUS;
    }

    public String getPUSH_BR_ID() {
        return PUSH_BR_ID;
    }

    public void setPUSH_BR_ID(String PUSH_BR_ID) {
        this.PUSH_BR_ID = PUSH_BR_ID;
    }

    public String getIS_BATC_BIZ() {
        return IS_BATC_BIZ;
    }

    public void setIS_BATC_BIZ(String IS_BATC_BIZ) {
        this.IS_BATC_BIZ = IS_BATC_BIZ;
    }

    public String getREFUSEREMARK() {
        return REFUSEREMARK;
    }

    public void setREFUSEREMARK(String REFUSEREMARK) {
        this.REFUSEREMARK = REFUSEREMARK;
    }

    public String getAPPROVE_STATUS() {
        return APPROVE_STATUS;
    }

    public void setAPPROVE_STATUS(String APPROVE_STATUS) {
        this.APPROVE_STATUS = APPROVE_STATUS;
    }

    public String getINPUT_ID() {
        return INPUT_ID;
    }

    public void setINPUT_ID(String INPUT_ID) {
        this.INPUT_ID = INPUT_ID;
    }

    public String getRISK_ANALYSE() {
        return RISK_ANALYSE;
    }

    public void setRISK_ANALYSE(String RISK_ANALYSE) {
        this.RISK_ANALYSE = RISK_ANALYSE;
    }

    public String getTERMINAL_SOURCE() {
        return TERMINAL_SOURCE;
    }

    public void setTERMINAL_SOURCE(String TERMINAL_SOURCE) {
        this.TERMINAL_SOURCE = TERMINAL_SOURCE;
    }

    public String getSCHEME_TYPE() {
        return SCHEME_TYPE;
    }

    public void setSCHEME_TYPE(String SCHEME_TYPE) {
        this.SCHEME_TYPE = SCHEME_TYPE;
    }

    public String getSPECIAL_REMARK() {
        return SPECIAL_REMARK;
    }

    public void setSPECIAL_REMARK(String SPECIAL_REMARK) {
        this.SPECIAL_REMARK = SPECIAL_REMARK;
    }

    public String getIS_WORTH() {
        return IS_WORTH;
    }

    public void setIS_WORTH(String IS_WORTH) {
        this.IS_WORTH = IS_WORTH;
    }

    public String getBUYER_CUS_TYPE() {
        return BUYER_CUS_TYPE;
    }

    public void setBUYER_CUS_TYPE(String BUYER_CUS_TYPE) {
        this.BUYER_CUS_TYPE = BUYER_CUS_TYPE;
    }

    public String getCUS_NAME() {
        return CUS_NAME;
    }

    public void setCUS_NAME(String CUS_NAME) {
        this.CUS_NAME = CUS_NAME;
    }

    public String getASSI_NAME() {
        return ASSI_NAME;
    }

    public void setASSI_NAME(String ASSI_NAME) {
        this.ASSI_NAME = ASSI_NAME;
    }

    public String getPRD_NAME() {
        return PRD_NAME;
    }

    public void setPRD_NAME(String PRD_NAME) {
        this.PRD_NAME = PRD_NAME;
    }

    public String getIS_ORDER() {
        return IS_ORDER;
    }

    public void setIS_ORDER(String IS_ORDER) {
        this.IS_ORDER = IS_ORDER;
    }

    public String getLOAN_USE_DESC() {
        return LOAN_USE_DESC;
    }

    public void setLOAN_USE_DESC(String LOAN_USE_DESC) {
        this.LOAN_USE_DESC = LOAN_USE_DESC;
    }

    public String getBUYER_CUS_NAME() {
        return BUYER_CUS_NAME;
    }

    public void setBUYER_CUS_NAME(String BUYER_CUS_NAME) {
        this.BUYER_CUS_NAME = BUYER_CUS_NAME;
    }

    public String getREPAY_BANK() {
        return REPAY_BANK;
    }

    public void setREPAY_BANK(String REPAY_BANK) {
        this.REPAY_BANK = REPAY_BANK;
    }

    public String getPARTNER_NO() {
        return PARTNER_NO;
    }

    public void setPARTNER_NO(String PARTNER_NO) {
        this.PARTNER_NO = PARTNER_NO;
    }

    public String getDEFAULT_RATE() {
        return DEFAULT_RATE;
    }

    public void setDEFAULT_RATE(String DEFAULT_RATE) {
        this.DEFAULT_RATE = DEFAULT_RATE;
    }

    public String getCACCEL_LOAN_REASON() {
        return CACCEL_LOAN_REASON;
    }

    public void setCACCEL_LOAN_REASON(String CACCEL_LOAN_REASON) {
        this.CACCEL_LOAN_REASON = CACCEL_LOAN_REASON;
    }

    public String getASSURE_MEANS_MAIN() {
        return ASSURE_MEANS_MAIN;
    }

    public void setASSURE_MEANS_MAIN(String ASSURE_MEANS_MAIN) {
        this.ASSURE_MEANS_MAIN = ASSURE_MEANS_MAIN;
    }

    public String getOPT_TYPE() {
        return OPT_TYPE;
    }

    public void setOPT_TYPE(String OPT_TYPE) {
        this.OPT_TYPE = OPT_TYPE;
    }

    public String getAPPLY_DATE() {
        return APPLY_DATE;
    }

    public void setAPPLY_DATE(String APPLY_DATE) {
        this.APPLY_DATE = APPLY_DATE;
    }

    public int getPERIOD() {
        return PERIOD;
    }

    public void setPERIOD(int PERIOD) {
        this.PERIOD = PERIOD;
    }

    public String getC_APPNT() {
        return c_APPNT;
    }

    public void setC_APPNT(String c_APPNT) {
        this.c_APPNT = c_APPNT;
    }

    public String getPRJ_STEP() {
        return PRJ_STEP;
    }

    public void setPRJ_STEP(String PRJ_STEP) {
        this.PRJ_STEP = PRJ_STEP;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public String getSPECIAL_CASE() {
        return SPECIAL_CASE;
    }

    public void setSPECIAL_CASE(String SPECIAL_CASE) {
        this.SPECIAL_CASE = SPECIAL_CASE;
    }

    public String getSUBORGANNO() {
        return SUBORGANNO;
    }

    public void setSUBORGANNO(String SUBORGANNO) {
        this.SUBORGANNO = SUBORGANNO;
    }

    public String getADVISE_GUAR_PERSON() {
        return ADVISE_GUAR_PERSON;
    }

    public void setADVISE_GUAR_PERSON(String ADVISE_GUAR_PERSON) {
        this.ADVISE_GUAR_PERSON = ADVISE_GUAR_PERSON;
    }

    public String getTEAM_LEADER() {
        return TEAM_LEADER;
    }

    public void setTEAM_LEADER(String TEAM_LEADER) {
        this.TEAM_LEADER = TEAM_LEADER;
    }

    public String getINFO_CHANNEL() {
        return INFO_CHANNEL;
    }

    public void setINFO_CHANNEL(String INFO_CHANNEL) {
        this.INFO_CHANNEL = INFO_CHANNEL;
    }

    public String getCHANNEL_NO() {
        return CHANNEL_NO;
    }

    public void setCHANNEL_NO(String CHANNEL_NO) {
        this.CHANNEL_NO = CHANNEL_NO;
    }

    public String getBUYER_PHONE() {
        return BUYER_PHONE;
    }

    public void setBUYER_PHONE(String BUYER_PHONE) {
        this.BUYER_PHONE = BUYER_PHONE;
    }

    public String getBIZ_TYPE() {
        return BIZ_TYPE;
    }

    public void setBIZ_TYPE(String BIZ_TYPE) {
        this.BIZ_TYPE = BIZ_TYPE;
    }

    public String getGUARANTY_TYPE() {
        return GUARANTY_TYPE;
    }

    public void setGUARANTY_TYPE(String GUARANTY_TYPE) {
        this.GUARANTY_TYPE = GUARANTY_TYPE;
    }

    public String getIS_SEND_LOAN() {
        return IS_SEND_LOAN;
    }

    public void setIS_SEND_LOAN(String IS_SEND_LOAN) {
        this.IS_SEND_LOAN = IS_SEND_LOAN;
    }

    public String getGUARANTOR_PHONE() {
        return GUARANTOR_PHONE;
    }

    public void setGUARANTOR_PHONE(String GUARANTOR_PHONE) {
        this.GUARANTOR_PHONE = GUARANTOR_PHONE;
    }

    public String getGROUP_LEADER() {
        return GROUP_LEADER;
    }

    public void setGROUP_LEADER(String GROUP_LEADER) {
        this.GROUP_LEADER = GROUP_LEADER;
    }

    public String getFICO_RECORD_ID() {
        return FICO_RECORD_ID;
    }

    public void setFICO_RECORD_ID(String FICO_RECORD_ID) {
        this.FICO_RECORD_ID = FICO_RECORD_ID;
    }

    public String getBUSI_STATUS() {
        return BUSI_STATUS;
    }

    public void setBUSI_STATUS(String BUSI_STATUS) {
        this.BUSI_STATUS = BUSI_STATUS;
    }

    public String getDEFAULT_IR() {
        return DEFAULT_IR;
    }

    public void setDEFAULT_IR(String DEFAULT_IR) {
        this.DEFAULT_IR = DEFAULT_IR;
    }

    public String getINPUT_NAME() {
        return INPUT_NAME;
    }

    public void setINPUT_NAME(String INPUT_NAME) {
        this.INPUT_NAME = INPUT_NAME;
    }

    public String getIS_UPDATE() {
        return IS_UPDATE;
    }

    public void setIS_UPDATE(String IS_UPDATE) {
        this.IS_UPDATE = IS_UPDATE;
    }

    public String getFICO_SCORE() {
        return FICO_SCORE;
    }

    public void setFICO_SCORE(String FICO_SCORE) {
        this.FICO_SCORE = FICO_SCORE;
    }

    public String getBUYER_FIRST_AMOUNT() {
        return BUYER_FIRST_AMOUNT;
    }

    public void setBUYER_FIRST_AMOUNT(String BUYER_FIRST_AMOUNT) {
        this.BUYER_FIRST_AMOUNT = BUYER_FIRST_AMOUNT;
    }

    public String getCERT_TYPE() {
        return CERT_TYPE;
    }

    public void setCERT_TYPE(String CERT_TYPE) {
        this.CERT_TYPE = CERT_TYPE;
    }

    public String getMAIN_USER_NAME() {
        return MAIN_USER_NAME;
    }

    public void setMAIN_USER_NAME(String MAIN_USER_NAME) {
        this.MAIN_USER_NAME = MAIN_USER_NAME;
    }

    public String getIS_DISTRIBUTION() {
        return IS_DISTRIBUTION;
    }

    public void setIS_DISTRIBUTION(String IS_DISTRIBUTION) {
        this.IS_DISTRIBUTION = IS_DISTRIBUTION;
    }

    public String getBANK_CUS_MGR() {
        return BANK_CUS_MGR;
    }

    public void setBANK_CUS_MGR(String BANK_CUS_MGR) {
        this.BANK_CUS_MGR = BANK_CUS_MGR;
    }

    public String getSELLER_CUS_TYPE() {
        return SELLER_CUS_TYPE;
    }

    public void setSELLER_CUS_TYPE(String SELLER_CUS_TYPE) {
        this.SELLER_CUS_TYPE = SELLER_CUS_TYPE;
    }

    public int getTERM() {
        return TERM;
    }

    public void setTERM(int TERM) {
        this.TERM = TERM;
    }

    public String getIS_ASSURE() {
        return IS_ASSURE;
    }

    public void setIS_ASSURE(String IS_ASSURE) {
        this.IS_ASSURE = IS_ASSURE;
    }

    public String getIS_OTHER_PLACE() {
        return IS_OTHER_PLACE;
    }

    public void setIS_OTHER_PLACE(String IS_OTHER_PLACE) {
        this.IS_OTHER_PLACE = IS_OTHER_PLACE;
    }

    public String getJIRA_NO() {
        return JIRA_NO;
    }

    public void setJIRA_NO(String JIRA_NO) {
        this.JIRA_NO = JIRA_NO;
    }

    public String getIS_RECORD() {
        return IS_RECORD;
    }

    public void setIS_RECORD(String IS_RECORD) {
        this.IS_RECORD = IS_RECORD;
    }

    public String getCUS_MGR() {
        return CUS_MGR;
    }

    public void setCUS_MGR(String CUS_MGR) {
        this.CUS_MGR = CUS_MGR;
    }

    public String getRISK_RESULT() {
        return RISK_RESULT;
    }

    public void setRISK_RESULT(String RISK_RESULT) {
        this.RISK_RESULT = RISK_RESULT;
    }

    public String getCACCEL_LOAN_REMARKS() {
        return CACCEL_LOAN_REMARKS;
    }

    public void setCACCEL_LOAN_REMARKS(String CACCEL_LOAN_REMARKS) {
        this.CACCEL_LOAN_REMARKS = CACCEL_LOAN_REMARKS;
    }

    public String getLOAN_DIRECTION() {
        return LOAN_DIRECTION;
    }

    public void setLOAN_DIRECTION(String LOAN_DIRECTION) {
        this.LOAN_DIRECTION = LOAN_DIRECTION;
    }

    public String getRULE_LOAN_AMT() {
        return RULE_LOAN_AMT;
    }

    public void setRULE_LOAN_AMT(String RULE_LOAN_AMT) {
        this.RULE_LOAN_AMT = RULE_LOAN_AMT;
    }

    public String getMAIN_BR_ID() {
        return MAIN_BR_ID;
    }

    public void setMAIN_BR_ID(String MAIN_BR_ID) {
        this.MAIN_BR_ID = MAIN_BR_ID;
    }

    public String getIS_CREDIT_IMPLEMENTATION() {
        return IS_CREDIT_IMPLEMENTATION;
    }

    public void setIS_CREDIT_IMPLEMENTATION(String IS_CREDIT_IMPLEMENTATION) {
        this.IS_CREDIT_IMPLEMENTATION = IS_CREDIT_IMPLEMENTATION;
    }

    public String getTOTAL_NESS_RATE() {
        return TOTAL_NESS_RATE;
    }

    public void setTOTAL_NESS_RATE(String TOTAL_NESS_RATE) {
        this.TOTAL_NESS_RATE = TOTAL_NESS_RATE;
    }

    public String getMANAGER_ADVICE() {
        return MANAGER_ADVICE;
    }

    public void setMANAGER_ADVICE(String MANAGER_ADVICE) {
        this.MANAGER_ADVICE = MANAGER_ADVICE;
    }

    public String getPROP() {
        return PROP;
    }

    public void setPROP(String PROP) {
        this.PROP = PROP;
    }

    public String getADVISE_AMT() {
        return ADVISE_AMT;
    }

    public void setADVISE_AMT(String ADVISE_AMT) {
        this.ADVISE_AMT = ADVISE_AMT;
    }

    public String getAPP_WORKFLOW_ID() {
        return APP_WORKFLOW_ID;
    }

    public void setAPP_WORKFLOW_ID(String APP_WORKFLOW_ID) {
        this.APP_WORKFLOW_ID = APP_WORKFLOW_ID;
    }

    public String getLOAN_BALANCE() {
        return LOAN_BALANCE;
    }

    public void setLOAN_BALANCE(String LOAN_BALANCE) {
        this.LOAN_BALANCE = LOAN_BALANCE;
    }

    public String getACCEPT_NAME() {
        return ACCEPT_NAME;
    }

    public void setACCEPT_NAME(String ACCEPT_NAME) {
        this.ACCEPT_NAME = ACCEPT_NAME;
    }

    public String getINPUT_CHANNEL() {
        return INPUT_CHANNEL;
    }

    public void setINPUT_CHANNEL(String INPUT_CHANNEL) {
        this.INPUT_CHANNEL = INPUT_CHANNEL;
    }

    public String getOTHER_REMARK() {
        return OTHER_REMARK;
    }

    public void setOTHER_REMARK(String OTHER_REMARK) {
        this.OTHER_REMARK = OTHER_REMARK;
    }

    public String getNEW_APPROVE_STATUS() {
        return NEW_APPROVE_STATUS;
    }

    public void setNEW_APPROVE_STATUS(String NEW_APPROVE_STATUS) {
        this.NEW_APPROVE_STATUS = NEW_APPROVE_STATUS;
    }

    public String getO_CONT_NO() {
        return o_CONT_NO;
    }

    public void setO_CONT_NO(String o_CONT_NO) {
        this.o_CONT_NO = o_CONT_NO;
    }

    public String getPAYEE_ACCOUNT() {
        return PAYEE_ACCOUNT;
    }

    public void setPAYEE_ACCOUNT(String PAYEE_ACCOUNT) {
        this.PAYEE_ACCOUNT = PAYEE_ACCOUNT;
    }

    public String getDUE_DAY() {
        return DUE_DAY;
    }

    public void setDUE_DAY(String DUE_DAY) {
        this.DUE_DAY = DUE_DAY;
    }

    public String getIS_GRT_THIRD() {
        return IS_GRT_THIRD;
    }

    public void setIS_GRT_THIRD(String IS_GRT_THIRD) {
        this.IS_GRT_THIRD = IS_GRT_THIRD;
    }

    public String getRULING_IR() {
        return RULING_IR;
    }

    public void setRULING_IR(String RULING_IR) {
        this.RULING_IR = RULING_IR;
    }

    public String getADVISE_DEADLINE() {
        return ADVISE_DEADLINE;
    }

    public void setADVISE_DEADLINE(String ADVISE_DEADLINE) {
        this.ADVISE_DEADLINE = ADVISE_DEADLINE;
    }

    public float getUSING_IR() {
        return USING_IR;
    }

    public void setUSING_IR(float USING_IR) {
        this.USING_IR = USING_IR;
    }

    public String getSELLER_CUS_NAME() {
        return SELLER_CUS_NAME;
    }

    public void setSELLER_CUS_NAME(String SELLER_CUS_NAME) {
        this.SELLER_CUS_NAME = SELLER_CUS_NAME;
    }

    public String getDAYS_OF_GRACE() {
        return DAYS_OF_GRACE;
    }

    public void setDAYS_OF_GRACE(String DAYS_OF_GRACE) {
        this.DAYS_OF_GRACE = DAYS_OF_GRACE;
    }

    public String getIS_AUTO_APPROVE() {
        return IS_AUTO_APPROVE;
    }

    public void setIS_AUTO_APPROVE(String IS_AUTO_APPROVE) {
        this.IS_AUTO_APPROVE = IS_AUTO_APPROVE;
    }

    public String getBID_CUS_ID() {
        return BID_CUS_ID;
    }

    public void setBID_CUS_ID(String BID_CUS_ID) {
        this.BID_CUS_ID = BID_CUS_ID;
    }

    public String getO_USING_IR() {
        return o_USING_IR;
    }

    public void setO_USING_IR(String o_USING_IR) {
        this.o_USING_IR = o_USING_IR;
    }

    public String getREFUSE_CODE() {
        return REFUSE_CODE;
    }

    public void setREFUSE_CODE(String REFUSE_CODE) {
        this.REFUSE_CODE = REFUSE_CODE;
    }

    public String getADVISE_REPAYMENT_WAY_DESC() {
        return ADVISE_REPAYMENT_WAY_DESC;
    }

    public void setADVISE_REPAYMENT_WAY_DESC(String ADVISE_REPAYMENT_WAY_DESC) {
        this.ADVISE_REPAYMENT_WAY_DESC = ADVISE_REPAYMENT_WAY_DESC;
    }

    public String getREPAY_ACCOUNT() {
        return REPAY_ACCOUNT;
    }

    public void setREPAY_ACCOUNT(String REPAY_ACCOUNT) {
        this.REPAY_ACCOUNT = REPAY_ACCOUNT;
    }

    public String getGROUPNO() {
        return GROUPNO;
    }

    public void setGROUPNO(String GROUPNO) {
        this.GROUPNO = GROUPNO;
    }

    public String getADVISE_TERM() {
        return ADVISE_TERM;
    }

    public void setADVISE_TERM(String ADVISE_TERM) {
        this.ADVISE_TERM = ADVISE_TERM;
    }

    public String getIS_FACE_SIGN() {
        return IS_FACE_SIGN;
    }

    public void setIS_FACE_SIGN(String IS_FACE_SIGN) {
        this.IS_FACE_SIGN = IS_FACE_SIGN;
    }

    public String getRIGHTER_CERT_TYPE() {
        return RIGHTER_CERT_TYPE;
    }

    public void setRIGHTER_CERT_TYPE(String RIGHTER_CERT_TYPE) {
        this.RIGHTER_CERT_TYPE = RIGHTER_CERT_TYPE;
    }

    public String getSPREAD_PLACE_DESC() {
        return SPREAD_PLACE_DESC;
    }

    public void setSPREAD_PLACE_DESC(String SPREAD_PLACE_DESC) {
        this.SPREAD_PLACE_DESC = SPREAD_PLACE_DESC;
    }

    public String getO_LOAN_START_DATE() {
        return o_LOAN_START_DATE;
    }

    public void setO_LOAN_START_DATE(String o_LOAN_START_DATE) {
        this.o_LOAN_START_DATE = o_LOAN_START_DATE;
    }

    public String getCANCEL_FAIL_CAUSE() {
        return CANCEL_FAIL_CAUSE;
    }

    public void setCANCEL_FAIL_CAUSE(String CANCEL_FAIL_CAUSE) {
        this.CANCEL_FAIL_CAUSE = CANCEL_FAIL_CAUSE;
    }

    public String getGUARANTOR_RELATE() {
        return GUARANTOR_RELATE;
    }

    public void setGUARANTOR_RELATE(String GUARANTOR_RELATE) {
        this.GUARANTOR_RELATE = GUARANTOR_RELATE;
    }

    public String getREAD_FLAG() {
        return READ_FLAG;
    }

    public void setREAD_FLAG(String READ_FLAG) {
        this.READ_FLAG = READ_FLAG;
    }

    public String getO_BILL_NO() {
        return o_BILL_NO;
    }

    public void setO_BILL_NO(String o_BILL_NO) {
        this.o_BILL_NO = o_BILL_NO;
    }

    public String getPAYEE_ACCOUNT_NAME() {
        return PAYEE_ACCOUNT_NAME;
    }

    public void setPAYEE_ACCOUNT_NAME(String PAYEE_ACCOUNT_NAME) {
        this.PAYEE_ACCOUNT_NAME = PAYEE_ACCOUNT_NAME;
    }

    public String getSPRD_RATE() {
        return SPRD_RATE;
    }

    public void setSPRD_RATE(String SPRD_RATE) {
        this.SPRD_RATE = SPRD_RATE;
    }

    public String getPARTNER_CERT_TYPE() {
        return PARTNER_CERT_TYPE;
    }

    public void setPARTNER_CERT_TYPE(String PARTNER_CERT_TYPE) {
        this.PARTNER_CERT_TYPE = PARTNER_CERT_TYPE;
    }

    public String getCORP_RELATE() {
        return CORP_RELATE;
    }

    public void setCORP_RELATE(String CORP_RELATE) {
        this.CORP_RELATE = CORP_RELATE;
    }

    public String getIS_ALREADY() {
        return IS_ALREADY;
    }

    public void setIS_ALREADY(String IS_ALREADY) {
        this.IS_ALREADY = IS_ALREADY;
    }

    public String getUSE_DETAIL() {
        return USE_DETAIL;
    }

    public void setUSE_DETAIL(String USE_DETAIL) {
        this.USE_DETAIL = USE_DETAIL;
    }

    public String getGUARANTOR() {
        return GUARANTOR;
    }

    public void setGUARANTOR(String GUARANTOR) {
        this.GUARANTOR = GUARANTOR;
    }

    public String getUSE_DATE() {
        return USE_DATE;
    }

    public void setUSE_DATE(String USE_DATE) {
        this.USE_DATE = USE_DATE;
    }

    public String getASSI_ID() {
        return ASSI_ID;
    }

    public void setASSI_ID(String ASSI_ID) {
        this.ASSI_ID = ASSI_ID;
    }

    public String getCUS_SEX() {
        return CUS_SEX;
    }

    public void setCUS_SEX(String CUS_SEX) {
        this.CUS_SEX = CUS_SEX;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPRD_TYPE() {
        return PRD_TYPE;
    }

    public void setPRD_TYPE(String PRD_TYPE) {
        this.PRD_TYPE = PRD_TYPE;
    }

    public String getACCOUNT_STATUS() {
        return ACCOUNT_STATUS;
    }

    public void setACCOUNT_STATUS(String ACCOUNT_STATUS) {
        this.ACCOUNT_STATUS = ACCOUNT_STATUS;
    }

    public String getINDIV_RSD_PLE() {
        return INDIV_RSD_PLE;
    }

    public void setINDIV_RSD_PLE(String INDIV_RSD_PLE) {
        this.INDIV_RSD_PLE = INDIV_RSD_PLE;
    }

    public String getINDIV_RSD_ADDR() {
        return INDIV_RSD_ADDR;
    }

    public void setINDIV_RSD_ADDR(String INDIV_RSD_ADDR) {
        this.INDIV_RSD_ADDR = INDIV_RSD_ADDR;
    }

    public String getINDIV_MAR_ST() {
        return INDIV_MAR_ST;
    }

    public void setINDIV_MAR_ST(String INDIV_MAR_ST) {
        this.INDIV_MAR_ST = INDIV_MAR_ST;
    }

    public String getINDIV_SPS_NAME() {
        return INDIV_SPS_NAME;
    }

    public void setINDIV_SPS_NAME(String INDIV_SPS_NAME) {
        this.INDIV_SPS_NAME = INDIV_SPS_NAME;
    }

    public String getINDIV_SPS_ID_CODE() {
        return INDIV_SPS_ID_CODE;
    }

    public void setINDIV_SPS_ID_CODE(String INDIV_SPS_ID_CODE) {
        this.INDIV_SPS_ID_CODE = INDIV_SPS_ID_CODE;
    }

    public String getINDIV_COM_TYP() {
        return INDIV_COM_TYP;
    }

    public void setINDIV_COM_TYP(String INDIV_COM_TYP) {
        this.INDIV_COM_TYP = INDIV_COM_TYP;
    }

    public String getBUSIN_NAME() {
        return BUSIN_NAME;
    }

    public void setBUSIN_NAME(String BUSIN_NAME) {
        this.BUSIN_NAME = BUSIN_NAME;
    }

    public String getOPER_NO() {
        return OPER_NO;
    }

    public void setOPER_NO(String OPER_NO) {
        this.OPER_NO = OPER_NO;
    }

    public static class User implements Serializable{
        private String register_latitude;

        private String user_name;

        private String create_date;

        private String head_photo;

        private String user_id;

        private String mobile;

        public void setRegister_latitude(String register_latitude){
            this.register_latitude = register_latitude;
        }
        public String getRegister_latitude(){
            return this.register_latitude;
        }
        public void setUser_name(String user_name){
            this.user_name = user_name;
        }
        public String getUser_name(){
            return this.user_name;
        }
        public void setCreate_date(String create_date){
            this.create_date = create_date;
        }
        public String getCreate_date(){
            return this.create_date;
        }
        public void setHead_photo(String head_photo){
            this.head_photo = head_photo;
        }
        public String getHead_photo(){
            return this.head_photo;
        }
        public void setUser_id(String user_id){
            this.user_id = user_id;
        }
        public String getUser_id(){
            return this.user_id;
        }
        public void setMobile(String mobile){
            this.mobile = mobile;
        }
        public String getMobile(){
            return this.mobile;
        }


    }

}
