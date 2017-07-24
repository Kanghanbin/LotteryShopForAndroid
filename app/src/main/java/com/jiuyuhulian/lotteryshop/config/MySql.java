package com.jiuyuhulian.lotteryshop.config;

/**
 * Created by Admin on 2017/3/21.
 */

public class MySql {
    public static final String DATABASE_NAME="lotteryshop.db";
    public static final String UserTable="user";
    public static final String ShoppingTable="shopping";
    public static final String LotteryTable="lottery";
    public static final String AddressTable="address";
    public static final int DATABASE_VERSION=1;


    public static final String createUserTable="create table if not exists"+UserTable+
            "(id integer primary key autoincrement," +
            "userName text," +
            "login boolean default false," +
            "uid integer)";
    public static final String createAddressTable="create table if not exists"+AddressTable+
            "(id integer primary key autoincrement,)"+
            "addressName text"+
            "addressPhone text"+
            "address text"+
            "detailAddress text"+
            "pid integer"+
            "preferred boolean default false）";

}
