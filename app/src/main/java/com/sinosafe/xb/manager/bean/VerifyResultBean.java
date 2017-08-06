package com.sinosafe.xb.manager.bean;

/**
 * 人脸识别结果
 * Created by john lee on 2017/6/5.
 */

public class VerifyResultBean {

    private ResultFaceid result_faceid;
    private long time_used;
    private String data_source_image_update_status;
    private String request_id;

    public ResultFaceid getResult_faceid() {
        return result_faceid;
    }

    public void setResult_faceid(ResultFaceid result_faceid) {
        this.result_faceid = result_faceid;
    }

    public long getTime_used() {
        return time_used;
    }

    public void setTime_used(long time_used) {
        this.time_used = time_used;
    }

    public String getData_source_image_update_status() {
        return data_source_image_update_status;
    }

    public void setData_source_image_update_status(String data_source_image_update_status) {
        this.data_source_image_update_status = data_source_image_update_status;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public class ResultFaceid {
        private double confidence;

        public double getConfidence() {
            return this.confidence;
        }

        public void setConfidence(double confidence) {
            this.confidence = confidence;
        }
    }

}

