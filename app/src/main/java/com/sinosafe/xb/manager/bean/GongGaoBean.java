package com.sinosafe.xb.manager.bean;

/**
 * 类名称：   com.cnmobi.huaan.manager.bean
 * 内容摘要： //公告类。
 * 修改备注：
 * 创建时间： 2017/6/5 0005
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class GongGaoBean {

    private String create_date;
    private String post_content;
    private String post_id;
    private String is_default;

    private String post_title;

    public String getCreate_date() {
        return this.create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getPost_content() {
        return this.post_content;
    }

    public void setPost_content(String post_content) {
        this.post_content = post_content;
    }

    public String getPost_id() {
        return this.post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getIs_default() {
        return this.is_default;
    }

    public void setIs_default(String is_default) {
        this.is_default = is_default;
    }


    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    @Override
    public String toString() {
        return "NoticeBean{" +
                "create_date='" + create_date + '\'' +
                ", post_content='" + post_content + '\'' +
                ", post_id='" + post_id + '\'' +
                ", is_default='" + is_default + '\'' +
                '}';
    }
}
