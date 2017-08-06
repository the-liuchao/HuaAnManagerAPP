package com.sinosafe.xb.manager.module.home.weidai.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * 类名称：   com.sinosafe.xb.manager.module.home.weidai.bean.DataListBean
 * 内容摘要： //资料清单,每个微贷申请的资料。
 * 修改备注：
 * 创建时间： 2017/6/27 0027
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */

@Table(name = "tb_dataListBean")
public class DataListBean implements Serializable {

    @Column(isId = true,name = "id",autoGen = true)
    private int id = -1;

    /**
     * 流水号
     */
    @Column(name = "serno")
    private String serno;

    /**
     * 资料清单item,json格式
     */
    @Column(name = "dataList")
    private String dataList;

    /**
     *  上传资料进度
     */
    @Column(name = "progress")
    private int progress = 0;

    /**
     * 填写资料是否完整0：不完整；1：完整
     */
    @Column(name = "complete")
    private int complete = 0;

    //是否正在上传
    private boolean isUpload = false;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerno() {
        return serno;
    }

    public void setSerno(String serno) {
        this.serno = serno;
    }

    public String getDataList() {
        return dataList;
    }

    public void setDataList(String dataList) {
        this.dataList = dataList;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getComplete() {
        return complete;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }

    public boolean isUpload() {
        return isUpload;
    }

    public void setUpload(boolean upload) {
        isUpload = upload;
    }
}
