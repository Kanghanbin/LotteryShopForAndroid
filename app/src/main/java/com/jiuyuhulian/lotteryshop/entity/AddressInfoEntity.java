package com.jiuyuhulian.lotteryshop.entity;

import java.io.Serializable;

/**
 * Created by Admin on 2017/3/18.
 * 收货地址类
 */

public class AddressInfoEntity implements Serializable {

    private String name;
    private String phone;
    private String adress;
    private String detailAdress;
    private boolean defaultAddress;

    public AddressInfoEntity() {
    }

    public AddressInfoEntity(String name, String phone, String detailAdress, String adress,boolean defaultAddress) {
        this.name = name;
        this.detailAdress = detailAdress;
        this.adress = adress;
        this.phone = phone;
        this.defaultAddress=defaultAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetailAdress() {
        return detailAdress;
    }

    public void setDetailAdress(String detailAdress) {
        this.detailAdress = detailAdress;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(boolean defaultAddress) {
        this.defaultAddress = defaultAddress;
    }
}
