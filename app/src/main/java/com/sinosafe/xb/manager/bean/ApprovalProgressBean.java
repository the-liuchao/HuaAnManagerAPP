package com.sinosafe.xb.manager.bean;

/**
 * 类名称：   com.sinosafe.xb.manager.bean
 * 内容摘要： //审批进度。
 * 修改备注：
 * 创建时间： 2017/6/7 0007
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class ApprovalProgressBean {
    
    //时间
    private String time;
    //状态描述
    private String progressStatus;
    //处理标记
    private int handleFlag;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getProgressStatus() {
        return progressStatus;
    }

    public void setProgressStatus(String progressStatus) {
        this.progressStatus = progressStatus;
    }

    public int getHandleFlag() {
        return handleFlag;
    }

    public void setHandleFlag(int handleFlag) {
        this.handleFlag = handleFlag;
    }
}
