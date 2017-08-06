package com.sinosafe.xb.manager.bean;

/**
 * Created by Administrator on 2017/6/17.
 * 常见问题
 */

public class LoanProblemBean {


    private String pro_id;
    private String problem_text;
    private String problem_title;
    private String problem_id;
    private String create_date;
    private long sort_weight;

    public String getPro_id() {
        return pro_id;
    }

    public void setPro_id(String pro_id) {
        this.pro_id = pro_id;
    }

    public String getProblem_text() {
        return problem_text;
    }

    public void setProblem_text(String problem_text) {
        this.problem_text = problem_text;
    }

    public String getProblem_title() {
        return problem_title;
    }

    public void setProblem_title(String problem_title) {
        this.problem_title = problem_title;
    }

    public String getProblem_id() {
        return problem_id;
    }

    public void setProblem_id(String problem_id) {
        this.problem_id = problem_id;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public long getSort_weight() {
        return sort_weight;
    }

    public void setSort_weight(long sort_weight) {
        this.sort_weight = sort_weight;
    }
}
