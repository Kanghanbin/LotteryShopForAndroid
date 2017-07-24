package com.jiuyuhulian.lotteryshop.entity;

/**
 * Created by Admin on 2017/3/20.
 */

public class UserDataEntity {
    private String name;
    private String idcard;
    private String phone;
    private String code;
    private String address;
    private String pwd;
    private String saftyPwd;

    public UserDataEntity() {
    }

    public String getName() {
        return name;

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSaftyPwd() {
        return saftyPwd;
    }

    public void setSaftyPwd(String saftyPwd) {
        this.saftyPwd = saftyPwd;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

}
