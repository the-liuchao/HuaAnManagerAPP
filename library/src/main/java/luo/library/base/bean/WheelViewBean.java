package luo.library.base.bean;


import com.wheelview.IWheelViewData;

/**
 * 字典 (用于wheelview 选择器)
 * Created by john lee on 2017/5/31.
 */

public class WheelViewBean implements IWheelViewData {

    private String type;
    private String name;

    public WheelViewBean(String type, String name) {
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
