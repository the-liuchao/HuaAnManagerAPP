package com.sinosafe.xb.manager.bean;

/**
 * 信保通用返回结果
 * <p>
 * Created by john lee on 2017/7/25.
 */

public class XinBaoResultBean<T> {

    private String REQUESTTIME;
    private String ERRORMESSAGE;
    private int STATUS;
    private T RESULT;

    public String getREQUESTTIME() {
        return this.REQUESTTIME;
    }

    public void setREQUESTTIME(String rEQUESTTIME) {
        this.REQUESTTIME = rEQUESTTIME;
    }

    public String getERRORMESSAGE() {
        return this.ERRORMESSAGE;
    }

    public void setERRORMESSAGE(String eRRORMESSAGE) {
        this.ERRORMESSAGE = eRRORMESSAGE;
    }

    public int getSTATUS() {
        return this.STATUS;
    }

    public void setSTATUS(int sTATUS) {
        this.STATUS = sTATUS;
    }

    public T getRESULT() {
        return RESULT;
    }

    public void setRESULT(T result) {
        this.RESULT = result;
    }

}
