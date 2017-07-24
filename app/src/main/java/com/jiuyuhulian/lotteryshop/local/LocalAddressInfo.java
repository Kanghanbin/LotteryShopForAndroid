package com.jiuyuhulian.lotteryshop.local;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;


/**
 * Created by Admin on 2017/3/21.
 */
@Table("localaddress")
public class LocalAddressInfo implements Serializable{

    public static final String COL_ID = "_id";
    public static final String COL_NAME= "name";
    public static final String COL_PHONE= "phone";
    public static final String COL_ADDRESS = "address";
    public static final String COL_DETAIL_ADDRESS="detail_address";

    @PrimaryKey(AssignType.AUTO_INCREMENT)
    @Column(COL_ID)
    private int id;
    @Column(COL_NAME)
    private String name;
    @Column(COL_PHONE)
    private String phone;
    @Column(COL_ADDRESS)
    private String address;
    @Column(COL_DETAIL_ADDRESS)
    private String detail_address;

    private boolean isDefault;
    public LocalAddressInfo(boolean isDefault, String detail_address, String address, String phone, String name) {
        this.isDefault = isDefault;
        this.detail_address = detail_address;
        this.address = address;
        this.phone = phone;
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail_address() {
        return detail_address;
    }

    public void setDetail_address(String detail_address) {
        this.detail_address = detail_address;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "LocalAddressInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +

                ", address='" + address + '\'' +
                ", detail_address='" + detail_address + '\'' +
                ", isDefault=" + isDefault +
                '}';
    }
}
