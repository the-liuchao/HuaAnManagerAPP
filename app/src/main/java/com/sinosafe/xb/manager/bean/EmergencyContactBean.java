package com.sinosafe.xb.manager.bean;

/**
 * 类名称：   com.sinosafe.xb.manager.bean
 * 内容摘要： //紧急联系人。
 * 修改备注：
 * 创建时间： 2017/6/10 0010
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class EmergencyContactBean {

    private String token;
    private String con_id;//紧急联系人ID
    private String con_name;//紧急联系人名称
    private String con_mobile;//紧急联系人电话
    private String con_relation;//紧急联系人关系

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCon_id() {
        return con_id;
    }

    public void setCon_id(String con_id) {
        this.con_id = con_id;
    }

    public String getCon_name() {
        return con_name;
    }

    public void setCon_name(String con_name) {
        this.con_name = con_name;
    }

    public String getCon_mobile() {
        return con_mobile;
    }

    public void setCon_mobile(String con_mobile) {
        this.con_mobile = con_mobile;
    }

    public String getCon_relation() {
        return con_relation;
    }

    public void setCon_relation(String con_relation) {
        this.con_relation = con_relation;
    }
}
