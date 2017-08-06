package com.sinosafe.xb.manager.bean;

/**
 * Created by Administrator on 2017/6/15.
 * 等待处理事项数量
 */

public class WaitProcess {

    //代办事项-面签
    private String TaskType20;
    //30 -代办事项-补充资料
    private String TaskType30;
    //40-代办事项-缴费
    private String TaskType40;
    //50-代办事项-贷后管理
    private String TaskType50;

    private String TaskType100;

    public void setTaskType20(String TaskType20){
        this.TaskType20 = TaskType20;
    }
    public String getTaskType20(){
        return this.TaskType20;
    }
    public void setTaskType30(String TaskType30){
        this.TaskType30 = TaskType30;
    }
    public String getTaskType30(){
        return this.TaskType30;
    }
    public void setTaskType40(String TaskType40){
        this.TaskType40 = TaskType40;
    }
    public String getTaskType40(){
        return this.TaskType40;
    }
    public void setTaskType50(String TaskType50){
        this.TaskType50 = TaskType50;
    }
    public String getTaskType50(){
        return this.TaskType50;
    }

    public String getTaskType100() {
        return TaskType100;
    }

    public void setTaskType100(String taskType100) {
        TaskType100 = taskType100;
    }
}
