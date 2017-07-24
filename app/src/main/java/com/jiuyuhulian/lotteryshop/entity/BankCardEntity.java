package com.jiuyuhulian.lotteryshop.entity;

import java.io.Serializable;

/**
 * Created by Admin on 2017/3/23.
 */

public class BankCardEntity implements Serializable{
    private int bankImg;
    private String bankKind;
    private String bankProporty;
    private String bankNum;

    public int getBankImg() {
        return bankImg;
    }

    public void setBankImg(int bankImg) {
        this.bankImg = bankImg;
    }

    public String getBankNum() {
        return bankNum;
    }

    public void setBankNum(String bankNum) {
        this.bankNum = bankNum;
    }

    public String getBankProporty() {
        return bankProporty;
    }

    public void setBankProporty(String bankProporty) {
        this.bankProporty = bankProporty;
    }

    public String getBankKind() {
        return bankKind;
    }

    public void setBankKind(String bankKind) {
        this.bankKind = bankKind;
    }

    public BankCardEntity(int bankImg, String bankNum, String bankProporty, String bankKind) {
        this.bankImg = bankImg;
        this.bankNum = bankNum;
        this.bankProporty = bankProporty;
        this.bankKind = bankKind;
    }

    public BankCardEntity() {
    }
}
