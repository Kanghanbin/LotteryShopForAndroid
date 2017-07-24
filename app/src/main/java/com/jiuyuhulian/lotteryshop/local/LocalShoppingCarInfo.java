package com.jiuyuhulian.lotteryshop.local;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;

/**
 * Created by Admin on 2017/3/16.
 */

@Table("shopcar")
public class LocalShoppingCarInfo implements Serializable {
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id;
    private int lottery_id;//"彩票ID",
    private double price;
    private String name;
    private int num;
    private String img;
    private String sell_status;//"销售状态"
    private boolean chooosed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isChooosed() {
        return chooosed;
    }

    public void setChooosed(boolean chooosed) {
        this.chooosed = chooosed;
    }

    public int getLottery_id() {
        return lottery_id;
    }

    public void setLottery_id(int lottery_id) {
        this.lottery_id = lottery_id;
    }

    public String getSell_status() {
        return sell_status;
    }

    public void setSell_status(String sell_status) {
        this.sell_status = sell_status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public LocalShoppingCarInfo(int lottery_id, double price, String name, int num, String img, String sell_status, boolean chooosed) {
        this.lottery_id = lottery_id;
        this.price = price;
        this.name = name;
        this.num = num;
        this.img = img;
        this.sell_status = sell_status;
        this.chooosed = chooosed;
    }
}
