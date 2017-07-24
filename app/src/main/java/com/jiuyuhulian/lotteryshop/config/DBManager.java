package com.jiuyuhulian.lotteryshop.config;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Admin on 2017/3/21.
 * 单例模式
 */

public class DBManager {
    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private static DBManager singtonManager;


    private DBManager(Context context) {
        dbHelper=new DBHelper(context);
        database=dbHelper.getWritableDatabase();
    }

    public static DBManager getManager(Context context){
        if(singtonManager==null){
            synchronized (DBManager.class){
                singtonManager=new DBManager(context);
            }
        }
        return singtonManager;
    }
    /**
     * 删除所有地址
     */
    public void removeAllProducts() {
        database.beginTransaction();
        database.execSQL("delete from " + MySql.AddressTable);
        database.setTransactionSuccessful();
        database.endTransaction();
    }
    /**
     * 删除地址
     *
     * @param pid
     * @param uid
     */
    public void deleteAddress(int pid, int uid) {
        database.beginTransaction();
        database.execSQL("delete from " + MySql.AddressTable + " where  pid = ? and uid =?", new String[]{String.valueOf(pid), String.valueOf(uid
        )});
        database.setTransactionSuccessful();
        database.endTransaction();
    }


}
