package com.jiuyuhulian.lotteryshop.entity;

import java.io.Serializable;

/**
 * Created by khb on 2017/4/15.
 */

public class OperatorEntity implements Serializable{
    private String id;
    private String pwd;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public OperatorEntity(String id, String pwd) {
        this.id = id;
        this.pwd = pwd;
    }



}
