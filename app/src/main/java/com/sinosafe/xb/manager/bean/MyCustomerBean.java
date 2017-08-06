package com.sinosafe.xb.manager.bean;

import java.io.Serializable;

/**
 * 类名称：   com.cnmobi.huaan.manager.bean
 * 内容摘要： //我的客户bean。
 * 修改备注：
 * 创建时间： 2017/6/6 0006
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class MyCustomerBean implements Serializable{

    private String cus_name;

    private int cus_id;

    private String cus_status;

    private String cus_photo;

    private String cus_phone;

    public void setCus_name(String cus_name){
        this.cus_name = cus_name;
    }
    public String getCus_name(){
        return this.cus_name;
    }
    public void setCus_id(int cus_id){
        this.cus_id = cus_id;
    }
    public int getCus_id(){
        return this.cus_id;
    }
    public void setCus_status(String cus_status){
        this.cus_status = cus_status;
    }
    public String getCus_status(){
        return this.cus_status;
    }
    public void setCus_photo(String cus_photo){
        this.cus_photo = cus_photo;
    }
    public String getCus_photo(){
        return this.cus_photo;
    }
    public void setCus_phone(String cus_phone){
        this.cus_phone = cus_phone;
    }
    public String getCus_phone(){
        return this.cus_phone;
    }
}
