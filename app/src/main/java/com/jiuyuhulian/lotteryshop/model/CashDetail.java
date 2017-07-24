package com.jiuyuhulian.lotteryshop.model;

import java.util.List;

/**
 * Created by khb on 2017/5/8.
 */

public class CashDetail {

    /**
     * code : 0
     * data : [{"encash_time":"时间","cash_status":"兑奖状态（1未中奖，2中奖，3无法识别，4已开奖）","lottery":"彩票名称","img":"图片地址","month":"月份","day":"日期","week":"周"}]
     */

    private int code;
    private List<DataBean> data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * encash_time : 时间
         * cash_status : 兑奖状态（1未中奖，2中奖，3无法识别，4已开奖）
         * win_money: "中奖金额"
         * lottery : 彩票名称
         * img : 图片地址
         * price:单价,
         * month : 月份
         * day : 日期
         * week : 周
         */

        private String encash_time;
        private int cash_status;
        private double win_money;
        private String lottery;
        private String img;
        private String month;
        private String day;
        private String week;
        private String price;

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getEncash_time() {
            return encash_time;
        }

        public void setEncash_time(String encash_time) {
            this.encash_time = encash_time;
        }

        public int getCash_status() {
            return cash_status;
        }

        public void setCash_status(int cash_status) {
            this.cash_status = cash_status;
        }

        public double getWin_money() {
            return win_money;
        }

        public void setWin_money(double win_money) {
            this.win_money = win_money;
        }

        public String getLottery() {
            return lottery;
        }

        public void setLottery(String lottery) {
            this.lottery = lottery;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }
    }
}
