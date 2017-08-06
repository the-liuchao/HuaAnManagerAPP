package com.sinosafe.xb.manager.bean;

/**
 * 类名称：   com.sinosafe.xb.manager.bean
 * 内容摘要： //贷款产品介绍。
 * 修改备注：
 * 创建时间： 2017/7/12 0012
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class LoanIntroduce {

    private String prd_introduce;//: "http://10.11.245.206:8080/haxb_api/tempDel/intro@3x.png",
    private String prd_condition;//": "http://10.11.245.206:8080/haxb_api/tempDel/apply@3x.png",
    private String prd_process;//": "http://10.11.245.206:8080/haxb_api/tempDel/flow@3x.png",
    private String prd_problems;

    public String getPrd_introduce() {
        return prd_introduce;
    }

    public void setPrd_introduce(String prd_introduce) {
        this.prd_introduce = prd_introduce;
    }

    public String getPrd_condition() {
        return prd_condition;
    }

    public void setPrd_condition(String prd_condition) {
        this.prd_condition = prd_condition;
    }

    public String getPrd_process() {
        return prd_process;
    }

    public void setPrd_process(String prd_process) {
        this.prd_process = prd_process;
    }

    public String getPrd_problems() {
        return prd_problems;
    }

    public void setPrd_problems(String prd_problems) {
        this.prd_problems = prd_problems;
    }
}
