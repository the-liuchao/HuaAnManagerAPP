package com.sinosafe.xb.manager.module.yeji.bean;

import java.util.List;

/**
 * 类名称：   com.sinosafe.xb.manager.module.yeji.bean
 * 内容摘要： //业绩排行榜列表。
 * 修改备注：
 * 创建时间： 2017/6/25 0025
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class YeJiRanking {


    private List<RankingItem> data;

    private RankingItem self;
    public List<RankingItem> getData() {
        return data;
    }

    public void setData(List<RankingItem> data) {
        this.data = data;
    }

    public RankingItem getSelf() {
        return self;
    }

    public void setSelf(RankingItem self) {
        this.self = self;
    }

    public static class RankingItem{

        private String FRIST_CUS_MGR;

        private double RESULT;

        private int ROW_NUM;

        private String CUS_MGR;

        private String DATE_INFO;

        private int SEQ = -1;

        private String IMAGE_URL;

        private String ORGANSHORTFORM;

        private String CUS_NAME;

        private int PAGE_COUNT;

        public void setFRIST_CUS_MGR(String FRIST_CUS_MGR){
            this.FRIST_CUS_MGR = FRIST_CUS_MGR;
        }
        public String getFRIST_CUS_MGR(){
            return this.FRIST_CUS_MGR;
        }
        public void setRESULT(double RESULT){
            this.RESULT = RESULT;
        }
        public double getRESULT(){
            return this.RESULT;
        }
        public void setROW_NUM(int ROW_NUM){
            this.ROW_NUM = ROW_NUM;
        }
        public int getROW_NUM(){
            return this.ROW_NUM;
        }
        public void setCUS_MGR(String CUS_MGR){
            this.CUS_MGR = CUS_MGR;
        }
        public String getCUS_MGR(){
            return this.CUS_MGR;
        }
        public void setDATE_INFO(String DATE_INFO){
            this.DATE_INFO = DATE_INFO;
        }
        public String getDATE_INFO(){
            return this.DATE_INFO;
        }
        public void setSEQ(int SEQ){
            this.SEQ = SEQ;
        }
        public int getSEQ(){
            return this.SEQ;
        }
        public void setIMAGE_URL(String IMAGE_URL){
            this.IMAGE_URL = IMAGE_URL;
        }
        public String getIMAGE_URL(){
            return this.IMAGE_URL;
        }
        public void setORGANSHORTFORM(String ORGANSHORTFORM){
            this.ORGANSHORTFORM = ORGANSHORTFORM;
        }
        public String getORGANSHORTFORM(){
            return this.ORGANSHORTFORM;
        }
        public void setCUS_NAME(String CUS_NAME){
            this.CUS_NAME = CUS_NAME;
        }
        public String getCUS_NAME(){
            return this.CUS_NAME;
        }
        public void setPAGE_COUNT(int PAGE_COUNT){
            this.PAGE_COUNT = PAGE_COUNT;
        }
        public int getPAGE_COUNT(){
            return this.PAGE_COUNT;
        }
    }

}
