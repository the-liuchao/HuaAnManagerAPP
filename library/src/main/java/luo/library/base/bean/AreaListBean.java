package luo.library.base.bean;


import com.wheelview.IWheelViewData;

/**
 * 省市区
 * Created by john lee on 2017/5/31.
 */

public class AreaListBean implements IWheelViewData {

    private String LEVELS;
    private String CNNAME;
    private String ENNAME;
    private String ABVENNAME;

    public String getLEVELS() {
        return LEVELS;
    }

    public void setLEVELS(String LEVELS) {
        this.LEVELS = LEVELS;
    }

    public String getCNNAME() {
        return CNNAME;
    }

    public void setCNNAME(String CNNAME) {
        this.CNNAME = CNNAME;
    }

    public String getENNAME() {
        return ENNAME;
    }

    public void setENNAME(String ENNAME) {
        this.ENNAME = ENNAME;
    }

    public String getABVENNAME() {
        return ABVENNAME;
    }

    public void setABVENNAME(String ABVENNAME) {
        this.ABVENNAME = ABVENNAME;
    }

    @Override
    public String getPickerViewText() {
        return this.CNNAME;
    }
}
