package com.sinosafe.xb.manager.module.home.weidai.bean;

/**
 * 类名称：   com.sinosafe.xb.manager.module.home.weidai.bean
 * 内容摘要： //图片文件信息
 * 修改备注：
 * 创建时间： 2017/6/27 0027
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class ImagesInfoBean {

    private int id = -1;
    /**
     * 流水号
     */
    private String serno;

    /**
     * 图片类型
     */
    private int type = -1;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件中文描述
     */
    private String photoDes;

    /**
     * 图片路径,该分类下所有路径，通过,分开
     */
    private String paths;

    private boolean uploadSuccess = false;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getPaths() {
        return paths;
    }

    public void setPaths(String paths) {
        this.paths = paths;
    }

    public boolean isUploadSuccess() {
        return uploadSuccess;
    }

    public void setUploadSuccess(boolean uploadSuccess) {
        this.uploadSuccess = uploadSuccess;
    }

    public String getPhotoDes() {
        return photoDes;
    }

    public void setPhotoDes(String photoDes) {
        this.photoDes = photoDes;
    }
}
