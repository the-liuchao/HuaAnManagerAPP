package com.sinosafe.xb.manager.bean;

/**
 * 银行卡归属
 * Created by john lee on 2017/7/17.
 */

public class BankOwnerShip {

    private RESULT RESULT;
    private String REQUESTTIME;
    private int STATUS;
    private String ERRORMESSAGE;

    public BankOwnerShip.RESULT getRESULT() {
        return RESULT;
    }

    public void setRESULT(BankOwnerShip.RESULT RESULT) {
        this.RESULT = RESULT;
    }

    public String getREQUESTTIME() {
        return REQUESTTIME;
    }

    public void setREQUESTTIME(String REQUESTTIME) {
        this.REQUESTTIME = REQUESTTIME;
    }

    public int getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(int STATUS) {
        this.STATUS = STATUS;
    }

    public String getERRORMESSAGE() {
        return ERRORMESSAGE;
    }

    public void setERRORMESSAGE(String ERRORMESSAGE) {
        this.ERRORMESSAGE = ERRORMESSAGE;
    }

    public class RESULT {
        private String sign;
        private String ret_code;
        private String bank_name;
        private String card_type;
        private String sign_type;
        private String ret_msg;
        private String bank_code;

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getRet_code() {
            return ret_code;
        }

        public void setRet_code(String ret_code) {
            this.ret_code = ret_code;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getCard_type() {
            return card_type;
        }

        public void setCard_type(String card_type) {
            this.card_type = card_type;
        }

        public String getSign_type() {
            return sign_type;
        }

        public void setSign_type(String sign_type) {
            this.sign_type = sign_type;
        }

        public String getRet_msg() {
            return ret_msg;
        }

        public void setRet_msg(String ret_msg) {
            this.ret_msg = ret_msg;
        }

        public String getBank_code() {
            return bank_code;
        }

        public void setBank_code(String bank_code) {
            this.bank_code = bank_code;
        }
    }

}
