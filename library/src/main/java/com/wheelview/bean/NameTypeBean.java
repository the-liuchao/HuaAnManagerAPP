package com.wheelview.bean;

import com.wheelview.IWheelViewData;

/**
 * 担保方式 还款方式 学历 婚姻状况
 * Created by john lee on 2017/7/2.
 */

public class NameTypeBean implements IWheelViewData {

    public String type;
    public String name;

    public NameTypeBean(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPickerViewText() {
        return this.name;
    }
}

