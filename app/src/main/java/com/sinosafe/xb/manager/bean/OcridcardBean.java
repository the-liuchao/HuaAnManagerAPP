package com.sinosafe.xb.manager.bean;

/**
 * 检测和识别中华人民共和国第二代身份证。
 * <p>
 * Created by john lee on 2017/5/28.
 */

public class OcridcardBean {

    private long time_used;
    private String address;
    private String side;
    private String race;
    private String id_card_number;
    private Legality legality;
    private String request_id;
    private String name;
    private Birthday birthday;
    private Head_rect head_rect;
    private String gender;

    public long getTime_used() {
        return this.time_used;
    }

    public void setTime_used(long time_used) {
        this.time_used = time_used;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSide() {
        return this.side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getRace() {
        return this.race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getId_card_number() {
        return this.id_card_number;
    }

    public void setId_card_number(String id_card_number) {
        this.id_card_number = id_card_number;
    }

    public Legality getLegality() {
        return this.legality;
    }

    public void setLegality(Legality legality) {
        this.legality = legality;
    }

    public String getRequest_id() {
        return this.request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Birthday getBirthday() {
        return this.birthday;
    }

    public void setBirthday(Birthday birthday) {
        this.birthday = birthday;
    }

    public Head_rect getHead_rect() {
        return this.head_rect;
    }

    public void setHead_rect(Head_rect head_rect) {
        this.head_rect = head_rect;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}

class Head_rect {
    private Lt lt;
    private Lb lb;
    private Rt rt;
    private Rb rb;

    public Lt getLt() {
        return this.lt;
    }

    public void setLt(Lt lt) {
        this.lt = lt;
    }

    public Lb getLb() {
        return this.lb;
    }

    public void setLb(Lb lb) {
        this.lb = lb;
    }

    public Rt getRt() {
        return this.rt;
    }

    public void setRt(Rt rt) {
        this.rt = rt;
    }

    public Rb getRb() {
        return this.rb;
    }

    public void setRb(Rb rb) {
        this.rb = rb;
    }

}

class Rb {
    private double y;
    private double x;

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

}

class Rt {
    private double y;
    private double x;

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

}

class Lb {
    private double y;
    private double x;

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

}

class Lt {
    private double y;
    private double x;

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

}

class Birthday {
    private String day;
    private String year;
    private String month;

    public String getDay() {
        return this.day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return this.month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

}

class Legality {
    private long temporaryIDPhoto;
    private double photocopy;
    private double screen;
    private long edited;
    private double iDPhoto;

    public long getTemporaryIDPhoto() {
        return this.temporaryIDPhoto;
    }

    public void setTemporaryIDPhoto(long temporaryIDPhoto) {
        this.temporaryIDPhoto = temporaryIDPhoto;
    }

    public double getPhotocopy() {
        return this.photocopy;
    }

    public void setPhotocopy(double photocopy) {
        this.photocopy = photocopy;
    }

    public double getScreen() {
        return this.screen;
    }

    public void setScreen(double screen) {
        this.screen = screen;
    }

    public long getEdited() {
        return this.edited;
    }

    public void setEdited(long edited) {
        this.edited = edited;
    }

    public double getIDPhoto() {
        return this.iDPhoto;
    }

    public void setIDPhoto(double iDPhoto) {
        this.iDPhoto = iDPhoto;
    }
}
