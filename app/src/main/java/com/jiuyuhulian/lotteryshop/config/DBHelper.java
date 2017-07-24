package com.jiuyuhulian.lotteryshop.config;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Admin on 2017/3/21.
 */

public class DBHelper extends SQLiteOpenHelper{
    public DBHelper(Context context){
        super(context, MySql.DATABASE_NAME,null, MySql.DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MySql.createAddressTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
