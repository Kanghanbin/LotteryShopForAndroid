package com.jiuyuhulian.lotteryshop.local;

import android.database.sqlite.SQLiteDatabase;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBaseConfig;
import com.litesuits.orm.db.assit.SQLiteHelper;
import com.litesuits.orm.db.assit.WhereBuilder;

import java.util.List;

import com.jiuyuhulian.lotteryshop.LotteryShopApplication;

/**
 * Created by Admin on 2017/3/22.
 */

public enum UOrm implements SQLiteHelper.OnUpdateListener {
    INSTANCE;
    private LiteOrm mLiteOrm;
    UOrm() {
        DataBaseConfig config = new DataBaseConfig(LotteryShopApplication.mcontext);
        config.dbName = "liteorm.db";
        config.dbVersion = 1;
        config.onUpdateListener = this;
        config.debugged=true;
        //可替换为 newCascadeInstance支持级联操作
        mLiteOrm = LiteOrm.newSingleInstance(config);
    }

    @Override public void onUpdate(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }


    /**
     * 保存数据
     * @param o
     */
    public void save(Object o) {
        if (o == null) {
            return;
        }

        mLiteOrm.save(o);
    }

    /**
     * 保存数据
     * @param collection
     * @param <T>
     */
    public <T> void saveList(List<T> collection) {
        if (collection.isEmpty()) {
            return;
        }

        mLiteOrm.save(collection);
    }

    /**
     * 删除全部
     * @param tClass
     * @param <T>
     */
    public <T> void delete(Class<T> tClass) {
        if (tClass == null) {
            return;
        }

        mLiteOrm.delete(tClass);
    }
    public <T> void delete(Class<T> tClass, WhereBuilder whereBuilder){
        if(tClass==null){
            return;
        }
        mLiteOrm.delete(whereBuilder);
    }

    /**
     * 查询全部数据
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> List<T> queryAll(Class<T> tClass) {
        if (tClass == null) {
            return null;
        }

        return mLiteOrm.query(tClass);
    }


}