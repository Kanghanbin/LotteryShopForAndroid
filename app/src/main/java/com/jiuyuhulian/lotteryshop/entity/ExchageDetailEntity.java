package com.jiuyuhulian.lotteryshop.entity;

/**
 * Created by Admin on 2017/3/11.
 */

public class ExchageDetailEntity {
    private String mouth;
    private String day;
    private String time;
    private String name;
    private double price;
    private boolean status;

    public ExchageDetailEntity(String mouth, boolean status, String name, String time, String day, double price) {
        this.mouth = mouth;
        this.status = status;
        this.name = name;
        this.time = time;
        this.day = day;
        this.price=price;
    }

    public String getMouth() {
        return mouth;
    }

    public void setMouth(String mouth) {
        this.mouth = mouth;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
