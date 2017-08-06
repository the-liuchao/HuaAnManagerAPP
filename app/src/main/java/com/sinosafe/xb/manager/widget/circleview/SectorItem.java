package com.sinosafe.xb.manager.widget.circleview;

import java.io.Serializable;

/**
 * 类名称：   com.cnmobi.circleview
 * 内容摘要： //说明主要功能。
 * 修改备注：
 * 创建时间： 2017/7/11 0011
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class SectorItem implements Serializable {
    private int startAngle;
    private int endAngle;
    private String name;

    public int getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(int startAngle) {
        this.startAngle = startAngle;
    }

    public int getEndAngle() {
        return endAngle;
    }

    public void setEndAngle(int endAngle) {
        this.endAngle = endAngle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
