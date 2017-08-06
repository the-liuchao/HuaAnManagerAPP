package com.sinosafe.xb.manager.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/17.
 * 消费贷贷款 用户信息
 */

public class LoanUserInfo {

    //产品类型 0：消费贷，2：微贷
    public String prdType = "0";
    //是否需要授权
    private boolean isAuth;
    private UserDetail user_detail;

    private boolean isGjjAuth = true;
    private boolean isSheBaoAuth = true;

    private List<UserContactor> user_contactor;

    public UserDetail getUser_detail() {
        return user_detail;
    }

    public void setUser_detail(UserDetail user_detail) {
        this.user_detail = user_detail;
    }

    public List<UserContactor> getUser_contactor() {
        return user_contactor;
    }

    public void setUser_contactor(List<UserContactor> user_contactor) {
        this.user_contactor = user_contactor;
    }

    public boolean isAuth() {
        return isAuth;
    }

    public void setAuth(boolean auth) {
        isAuth = auth;
    }

    public boolean isGjjAuth() {
        return isGjjAuth;
    }

    public void setGjjAuth(boolean gjjAuth) {
        isGjjAuth = gjjAuth;
    }

    public boolean isSheBaoAuth() {
        return isSheBaoAuth;
    }

    public void setSheBaoAuth(boolean sheBaoAuth) {
        isSheBaoAuth = sheBaoAuth;
    }

    public String getPrdType() {
        return prdType;
    }

    public void setPrdType(String prdType) {
        this.prdType = prdType;
    }

    /**
     * 紧急联系人
     */
    public static class UserContactor{

        private String con_mobile;

        private String con_name;

        private int con_id = 0;

        private String con_relation;

        public void setCon_mobile(String con_mobile){
            this.con_mobile = con_mobile;
        }
        public String getCon_mobile(){
            return this.con_mobile;
        }
        public void setCon_name(String con_name){
            this.con_name = con_name;
        }
        public String getCon_name(){
            return this.con_name;
        }
        public void setCon_id(int con_id){
            this.con_id = con_id;
        }
        public int getCon_id(){
            return this.con_id;
        }
        public void setCon_relation(String con_relation){
            this.con_relation = con_relation;
        }
        public String getCon_relation(){
            return this.con_relation;
        }
    }

    /**
     * 用户详情
     */
    public static class UserDetail{

        //配偶姓名
        private String indiv_sps_name;
        //单位电话
        private String indiv_com_phn;
        //配偶身份证号码
        private String indiv_sps_cardno;
        //配偶手机号
        private String indiv_sps_mphn;
        //单位性质
        private String indiv_com_typ;
        //居住地址 --详细地址
        private String indiv_rsd_addr;
        //婚姻状况
        private String indiv_mar_st;
        //配偶身份证正面
        private String indiv_sps_idcard;
        //单位地址
        private String indiv_com_addr;
        //客户名
        private String user_name;
        //单位地址行政区划编号
        private String indiv_com_addr_ple_id;
        private String indiv_com_addr_ple;
        //单位名称
        private String indiv_com_name;
        //最高学历（教育程度）：00 博士及以上、10 研究生、20 本科、30 大专、40 中专或技校、60 高中、70 初中、80 小学、90 文盲或半文盲
        private String indiv_edt;
        //银行卡号
        private String card_code;
        //持卡人
        private String card_belongs;

        //居住地址行政区划编号
        private String indiv_rsd_ple_id;
        //居住地址行政区划--居住地址
        private String indiv_rsd_ple;
        //单位工作起始年
        private String indiv_work_job_y;
        //联系方式
        private String mobile;
        //车产状况
        private String carl_st;
        //机动车证书
        private String carl_certificate;
        //房产状况
        private String house_st;
        //房产证书
        private String house_certificate;
        //贷款用途
        private String loan_for;
        //工牌
        private String badge;
        //流水号
        private String serno;
        //产品id
        private String prd_id;
        //客户id
        private String user_id;
        //卡信息纪录id
        private int card_id;
        //客户详情的纪录编号
        private String detail_id;
        //客户身份证号码
        private String cert_code;
        //企业营业执照编号
        private String oper_no;
        //企业/生意名称
        private String busin_name;
        //社保授权时间
        private String soc_auth_time;
        //房产验证时间
        private String house_auth_time;


        public void setIndiv_sps_name(String indiv_sps_name){
            this.indiv_sps_name = indiv_sps_name;
        }
        public String getIndiv_sps_name(){
            return this.indiv_sps_name;
        }
        public void setIndiv_com_phn(String indiv_com_phn){
            this.indiv_com_phn = indiv_com_phn;
        }
        public String getIndiv_com_phn(){
            return this.indiv_com_phn;
        }
        public void setIndiv_sps_cardno(String indiv_sps_cardno){
            this.indiv_sps_cardno = indiv_sps_cardno;
        }
        public String getIndiv_sps_cardno(){
            return this.indiv_sps_cardno;
        }
        public void setIndiv_com_typ(String indiv_com_typ){
            this.indiv_com_typ = indiv_com_typ;
        }
        public String getIndiv_com_typ(){
            return this.indiv_com_typ;
        }
        public void setIndiv_rsd_addr(String indiv_rsd_addr){
            this.indiv_rsd_addr = indiv_rsd_addr;
        }
        public String getIndiv_rsd_addr(){
            return this.indiv_rsd_addr;
        }
        public void setIndiv_mar_st(String indiv_mar_st){
            this.indiv_mar_st = indiv_mar_st;
        }
        public String getIndiv_mar_st(){
            return this.indiv_mar_st;
        }
        public void setIndiv_sps_idcard(String indiv_sps_idcard){
            this.indiv_sps_idcard = indiv_sps_idcard;
        }
        public String getIndiv_sps_idcard(){
            return this.indiv_sps_idcard;
        }
        public void setIndiv_com_addr(String indiv_com_addr){
            this.indiv_com_addr = indiv_com_addr;
        }
        public String getIndiv_com_addr(){
            return this.indiv_com_addr;
        }
        public void setUser_name(String user_name){
            this.user_name = user_name;
        }
        public String getUser_name(){
            return this.user_name;
        }
        public void setIndiv_com_addr_ple_id(String indiv_com_addr_ple_id){
            this.indiv_com_addr_ple_id = indiv_com_addr_ple_id;
        }
        public String getIndiv_com_addr_ple_id(){
            return this.indiv_com_addr_ple_id;
        }
        public void setIndiv_com_name(String indiv_com_name){
            this.indiv_com_name = indiv_com_name;
        }
        public String getIndiv_com_name(){
            return this.indiv_com_name;
        }
        public void setIndiv_edt(String indiv_edt){
            this.indiv_edt = indiv_edt;
        }
        public String getIndiv_edt(){
            return this.indiv_edt;
        }

        public String getCard_code() {
            return card_code;
        }

        public void setCard_code(String card_code) {
            this.card_code = card_code;
        }

        public String getCard_belongs() {
            return card_belongs;
        }

        public void setCard_belongs(String card_belongs) {
            this.card_belongs = card_belongs;
        }

        public void setIndiv_rsd_ple(String indiv_rsd_ple){
            this.indiv_rsd_ple = indiv_rsd_ple;
        }
        public String getIndiv_rsd_ple(){
            return this.indiv_rsd_ple;
        }
        public void setIndiv_work_job_y(String indiv_work_job_y){
            this.indiv_work_job_y = indiv_work_job_y;
        }
        public String getIndiv_work_job_y(){
            return this.indiv_work_job_y;
        }
        public void setMobile(String mobile){
            this.mobile = mobile;
        }
        public String getMobile(){
            return this.mobile;
        }

        public String getIndiv_sps_mphn() {
            return indiv_sps_mphn;
        }

        public void setIndiv_sps_mphn(String indiv_sps_mphn) {
            this.indiv_sps_mphn = indiv_sps_mphn;
        }

        public String getIndiv_rsd_ple_id() {
            return indiv_rsd_ple_id;
        }

        public void setIndiv_rsd_ple_id(String indiv_rsd_ple_id) {
            this.indiv_rsd_ple_id = indiv_rsd_ple_id;
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

        public String getLoan_for() {
            return loan_for;
        }

        public void setLoan_for(String loan_for) {
            this.loan_for = loan_for;
        }

        public String getBadge() {
            return badge;
        }

        public void setBadge(String badge) {
            this.badge = badge;
        }

        public String getSerno() {
            return serno;
        }

        public void setSerno(String serno) {
            this.serno = serno;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String cus_id) {
            this.user_id = cus_id;
        }

        public int getCard_id() {
            return card_id;
        }

        public void setCard_id(int card_id) {
            this.card_id = card_id;
        }
        public String getDetail_id() {
            return detail_id;
        }

        public void setDetail_id(String detail_id) {
            this.detail_id = detail_id;
        }

        public String getCert_code() {
            return cert_code;
        }

        public void setCert_code(String cert_code) {
            this.cert_code = cert_code;
        }

        public String getIndiv_com_addr_ple() {
            return indiv_com_addr_ple;
        }

        public void setIndiv_com_addr_ple(String indiv_com_addr_ple) {
            this.indiv_com_addr_ple = indiv_com_addr_ple;
        }
        public String getPrd_id() {
            return prd_id;
        }

        public void setPrd_id(String prd_id) {
            this.prd_id = prd_id;
        }

        public String getOper_no() {
            return oper_no;
        }

        public void setOper_no(String oper_no) {
            this.oper_no = oper_no;
        }

        public String getBusin_name() {
            return busin_name;
        }

        public void setBusin_name(String busin_name) {
            this.busin_name = busin_name;
        }
    }
}
