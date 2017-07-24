package com.jiuyuhulian.lotteryshop.entity;

/**
 * Created by Admin on 2017/3/14.
 */

public class OrderEntity {
    private int img;
    private String name;
    private double price;
    private int num;
    private int type;
    private double freight;

    public OrderEntity(int img, int num, double price, String name,int type,double freight) {
        this.img = img;
        this.num = num;
        this.price = price;
        this.name = name;
        this.type=type;
        this.freight=freight;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
    }
}
