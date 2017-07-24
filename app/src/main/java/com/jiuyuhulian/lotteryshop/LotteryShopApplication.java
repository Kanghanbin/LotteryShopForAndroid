package com.jiuyuhulian.lotteryshop;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.utils.Utils;
import com.jiuyuhulian.lotteryshop.rest.RetrofitHttp;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBaseConfig;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**
 * Created by Admin on 2017/3/13.
 */

public class LotteryShopApplication extends Application {

    public static Context mcontext;

    public static LiteOrm liteOrm;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        mcontext = this;
        // 全局工具类
        Utils.init(this);

        //yipanfengye快速集成二维码扫描功能
        ZXingLibrary.initDisplayOpinion(this);
        new RetrofitHttp().init(this);
        if (liteOrm == null) {
            DataBaseConfig config = new DataBaseConfig(this, "liteorm.db");
            //"liteorm.db"是数据库名称，名称里包含路径符号"/"则将数据库建立到该路径下，可以使用sd卡路径。 不包含则在系统默认路径下创建DB文件。
            //例如 public static final String DB_NAME = SD_CARD + "/lite/orm/liteorm.db";     DataBaseConfig config = new DataBaseConfig(this, DB_NAME);
            config.dbVersion = 1; // set database version
            config.onUpdateListener = null; // set database update listener
            //独立操作，适用于没有级联关系的单表操作，
            liteOrm = LiteOrm.newSingleInstance(config);
            //级联操作,适用于多表级联操作
            // liteOrm=LiteOrm.newCascadeInstance(config);
        }
        liteOrm.setDebugged(true); // open the log*/
    }

}
