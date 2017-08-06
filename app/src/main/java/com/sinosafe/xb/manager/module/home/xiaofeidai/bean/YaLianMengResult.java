package com.sinosafe.xb.manager.module.home.xiaofeidai.bean;

/**
 * 类名称：   com.sinosafe.xb.manager.module.home.xiaofeidai.bean
 * 内容摘要： //亚联盟银行卡校验结果bean。
 * 修改备注：
 * 创建时间： 2017/8/4 0004
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class YaLianMengResult {

    private String msg;
    private String result;
    private String orderId;
    private Data data;
    private String sign;
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }

    public void setResult(String result) {
        this.result = result;
    }
    public String getResult() {
        return result;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getOrderId() {
        return orderId;
    }

    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
        return data;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
    public String getSign() {
        return sign;
    }

    public static class Data {

        private String code;
        private String desc;
        private String org_code;
        private String org_desc;
        private String bank_id;
        private String bank_description;
        public void setCode(String code) {
            this.code = code;
        }
        public String getCode() {
            return code;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
        public String getDesc() {
            return desc;
        }

        public void setOrg_code(String org_code) {
            this.org_code = org_code;
        }
        public String getOrg_code() {
            return org_code;
        }

        public void setOrg_desc(String org_desc) {
            this.org_desc = org_desc;
        }
        public String getOrg_desc() {
            return org_desc;
        }

        public void setBank_id(String bank_id) {
            this.bank_id = bank_id;
        }
        public String getBank_id() {
            return bank_id;
        }

        public void setBank_description(String bank_description) {
            this.bank_description = bank_description;
        }
        public String getBank_description() {
            return bank_description;
        }
    }
}
