package com.jiuyuhulian.lotteryshop.entity;

/**
 * Created by Admin on 2017/3/13.
 */

public class ChargeRecordsEntity {
    private int img;
    private String bankName;
    private double bankNum;
    private String time;
    private boolean isReview;


    public ChargeRecordsEntity(int img, boolean isReview, String time, double bankNum, String bankName) {
        this.img = img;
        this.isReview = isReview;
        this.time = time;
        this.bankNum = bankNum;
        this.bankName = bankName;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public boolean isReview() {
        return isReview;
    }

    public void setReview(boolean review) {
        isReview = review;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getBankNum() {
        return bankNum;
    }

    public void setBankNum(double bankNum) {
        this.bankNum = bankNum;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
