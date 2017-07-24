package com.jiuyuhulian.lotteryshop.entity;

/**
 * Created by Admin on 2017/3/14.
 */

public class InterflowEntity {
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public InterflowEntity(String desp, String time, String date) {

        this.desp = desp;
        this.time = time;
        this.date = date;
    }

    private String time;
    private String desp;



}
