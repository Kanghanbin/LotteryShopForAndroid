package com.jiuyuhulian.lotteryshop.entity;

/**
 * Created by Admin on 2017/3/11.
 */

public class StockQureyEntity {
    private String name;
    private int left;
    private boolean active;
    private int img;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public StockQureyEntity(String name, int left, boolean active, int img) {
        this.name = name;
        this.left = left;
        this.active = active;
        this.img = img;

    }

    public StockQureyEntity() {
    }
}
