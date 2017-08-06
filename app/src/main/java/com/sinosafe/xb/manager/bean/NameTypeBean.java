package com.sinosafe.xb.manager.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名称：   com.sinosafe.xb.manager.bean
 * 内容摘要： //还款方式 、担保方式。
 * 修改备注：
 * 创建时间： 2017/7/5 0005
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class NameTypeBean {

    public static List<NameTypeBean> repaymentMethods = new ArrayList<>();
    public static List<NameTypeBean> guaranteeMethods = new ArrayList<>();

    private String key;

    private String name;

    public NameTypeBean(String key, String name) {
        this.key = key;
        this.name = name;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 还款方式
     * @return
     */
    public static List<NameTypeBean> getRepaymentModel(){

        if(repaymentMethods.size()==0){
            repaymentMethods.add(new NameTypeBean("9", "按月等本等息"));
            repaymentMethods.add(new NameTypeBean("6", "按月付息到期还本"));
            repaymentMethods.add(new NameTypeBean("7", "到期一次还款"));
            repaymentMethods.add(new NameTypeBean("3", "按月等额本息"));
            repaymentMethods.add(new NameTypeBean("1", "其他还款"));
            repaymentMethods.add(new NameTypeBean("2", "等额本金还款"));
            repaymentMethods.add(new NameTypeBean("4", "按季还息到期还本"));
            repaymentMethods.add(new NameTypeBean("5", "按月付息按季还本"));
            repaymentMethods.add(new NameTypeBean("8", "按月付息 + 部分本，到期还本"));
        }
        return repaymentMethods;
    }


    /**
     * 担保方式
     * @return
     */
    public static List<NameTypeBean> getGuaranteeModel(){

        if(guaranteeMethods.size()==0){
            guaranteeMethods.add(new NameTypeBean("10", "抵押担保"));
            guaranteeMethods.add(new NameTypeBean("00", "信用担保"));
            guaranteeMethods.add(new NameTypeBean("20", "质押担保"));
            guaranteeMethods.add(new NameTypeBean("31", "担保公司保证"));
            guaranteeMethods.add(new NameTypeBean("32", "保证金"));
            guaranteeMethods.add(new NameTypeBean("30", "保证"));
        }
        return guaranteeMethods;
    }
}
