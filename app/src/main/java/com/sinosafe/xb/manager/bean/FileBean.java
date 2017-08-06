package com.sinosafe.xb.manager.bean;

import java.io.File;

/**
 * 上传文件
 * Created by john lee on 2017/6/9.
 */

public class FileBean {
    private String fileId;
    private String fileName;
    private String filePath;
    private String fileType;
    private String mainFlag;
    private String sideFlag;
    private File imgFile;
    private String remoteFilePath;
    private String ftpFlag;

    public FileBean() {
    }

    public String getRemoteFilePath() {
        return this.remoteFilePath;
    }

    public void setRemoteFilePath(String remoteFilePath) {
        this.remoteFilePath = remoteFilePath;
    }

    public String getFtpFlag() {
        return this.ftpFlag;
    }

    public void setFtpFlag(String ftpFlag) {
        this.ftpFlag = ftpFlag;
    }

    public File getImgFile() {
        return this.imgFile;
    }

    public void setImgFile(File imgFile) {
        this.imgFile = imgFile;
    }

    public String getFileId() {
        return this.fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getMainFlag() {
        return this.mainFlag;
    }

    public void setMainFlag(String mainFlag) {
        this.mainFlag = mainFlag;
    }

    public String getSideFlag() {
        return this.sideFlag;
    }

    public void setSideFlag(String sideFlag) {
        this.sideFlag = sideFlag;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return this.fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
