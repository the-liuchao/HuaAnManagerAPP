package com.sinosafe.xb.manager.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * 类名称：   com.sinosafe.xb.manager.bean
 * 内容摘要： //登录用户信息。
 * 修改备注：
 * 创建时间： 2017/6/12 0012
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
@Table(name = "tb_LoginUserBean")
public class LoginUserBean implements Serializable{

    @Column(name = "id",isId = true)
    private int id;
    /**
     * 短号
     */
    @Column(name = "cornet")
    private String cornet;

    /**
     * 座机
     */
    @Column(name = "landline")
    private String landline;

    /**
     * 组织机构id
     */
    @Column(name = "orgid")
    private String orgid;

    /**
     * 工牌号
     */
    @Column(name = "actorno")
    private String actorno;

    /**
     * 身份证号
     */
    @Column(name = "idcardno")
    private String idcardno;


    /**
     * 启用日期
     */
    @Column(name = "startdate")
    private String startdate;

    /**
     * 头像
     */
    @Column(name = "avatar")
    private String avatar;
    /**
     * token
     */
    @Column(name = "token")
    private String token;
    /**
     *电话号码
     */
    @Column(name = "telnum")
    private String telnum;
    /**
     * 邮箱
     */
    @Column(name = "usermail")
    private String usermail;
    /**
     * 姓名
     */
    @Column(name = "actorname")
    private String actorname;


    /**
     * 推送token
     */
    @Column(name = "channel_id")
    private String channel_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCornet(String cornet) {
        this.cornet = cornet;
    }

    public String getCornet() {
        return cornet;
    }

    public void setActorno(String actorno) {
        this.actorno = actorno;
    }

    public String getActorno() {
        return actorno;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setTelnum(String telnum) {
        this.telnum = telnum;
    }

    public String getTelnum() {
        return telnum;
    }

    public void setUsermail(String usermail) {
        this.usermail = usermail;
    }

    public String getUsermail() {
        return usermail;
    }

    public void setActorname(String actorname) {
        this.actorname = actorname;
    }

    public String getActorname() {
        return actorname;
    }

    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    public String getIdcardno() {
        return idcardno;
    }

    public void setIdcardno(String idcardno) {
        this.idcardno = idcardno;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }
}
