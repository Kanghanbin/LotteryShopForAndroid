package com.jiuyuhulian.lotteryshop.entity;

/**
 * Created by Admin on 2017/3/18.
 */

public class SubTotalEntity {
    private int img;
    private String name;
    private double price;
    private double winAmount;
    private String time;

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getWinAmount() {
        return winAmount;
    }

    public void setWinAmount(double winAmount) {
        this.winAmount = winAmount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SubTotalEntity(int img, String time, double winAmount, double price, String name) {
        this.img = img;
        this.time = time;
        this.winAmount = winAmount;
        this.price = price;
        this.name = name;
    }
}
