package com.sinosafe.xb.manager.bean;

/**
 * 类名称：   com.sinosafe.xb.manager.bean
 * 内容摘要： //消息。
 * 修改备注：
 * 创建时间： 2017/6/12 0012
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class MessageBean {

    private String id;
    private String mess_json;
    private int mess_type;
    private String mess_title;
    private String mess_content;
    private String create_time;
    private String bean_id;
    //是否已读（0未读、1已读）
    private int is_read;
    private String receiver_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMess_json() {
        return mess_json;
    }

    public void setMess_json(String mess_json) {
        this.mess_json = mess_json;
    }

    public int getMess_type() {
        return mess_type;
    }

    public void setMess_type(int mess_type) {
        this.mess_type = mess_type;
    }


    public String getMess_title() {
        return mess_title;
    }

    public void setMess_title(String mess_title) {
        this.mess_title = mess_title;
    }

    public String getMess_content() {
        return mess_content;
    }

    public void setMess_content(String mess_content) {
        this.mess_content = mess_content;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getBean_id() {
        return bean_id;
    }

    public void setBean_id(String bean_id) {
        this.bean_id = bean_id;
    }

    public int getIs_read() {
        return is_read;
    }

    public void setIs_read(int is_read) {
        this.is_read = is_read;
    }

    public String getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(String receiver_id) {
        this.receiver_id = receiver_id;
    }

    public static class MessJSon{
        private String serno;

        private String prd_id;

        private String prd_type;

        private String loan_type;

        private String scene_type;

        private String content;

        private String approve_status;

        public void setSerno(String serno){
            this.serno = serno;
        }
        public String getSerno(){
            return this.serno;
        }
        public void setPrd_id(String prd_id){
            this.prd_id = prd_id;
        }
        public String getPrd_id(){
            return this.prd_id;
        }
        public void setPrd_type(String prd_type){
            this.prd_type = prd_type;
        }
        public String getPrd_type(){
            return this.prd_type;
        }
        public void setLoan_type(String loan_type){
            this.loan_type = loan_type;
        }
        public String getLoan_type(){
            return this.loan_type;
        }
        public void setScene_type(String scene_type){
            this.scene_type = scene_type;
        }
        public String getScene_type(){
            return this.scene_type;
        }
        public void setContent(String content){
            this.content = content;
        }
        public String getContent(){
            return this.content;
        }
        public void setApprove_status(String approve_status){
            this.approve_status = approve_status;
        }
        public String getApprove_status(){
            return this.approve_status;
        }
    }
}
