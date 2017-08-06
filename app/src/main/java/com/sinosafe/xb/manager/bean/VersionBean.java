package com.sinosafe.xb.manager.bean;

/**
 * Created by Administrator on 2017/6/15.
 * 版本信息
 */

public class VersionBean {


    private String version_name;
    private String update_description;
    private String remark;
    private String download_url;
    private String update_date;
    private int version_code;
    private String soft_name;

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setUpdate_description(String update_description) {
        this.update_description = update_description;
    }

    public String getUpdate_description() {
        return update_description;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }

    public String getUpdate_date() {
        return update_date;
    }

    public void setVersion_code(int version_code) {
        this.version_code = version_code;
    }

    public int getVersion_code() {
        return version_code;
    }

    public void setSoft_name(String soft_name) {
        this.soft_name = soft_name;
    }

    public String getSoft_name() {
        return soft_name;
    }
}
