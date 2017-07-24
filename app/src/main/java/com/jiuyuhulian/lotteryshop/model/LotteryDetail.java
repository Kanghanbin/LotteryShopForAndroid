package com.jiuyuhulian.lotteryshop.model;

import java.util.List;

/**
 * Created by khb on 2017/4/26.
 */

public class LotteryDetail {

    /**
     * code : 0
     * data : {"lottery":"中国红2","price":"2.00","img":"/upload/lottery/26057-1493033394.png","desc":"红红火火","rank":["5","10","20","50","100","200","500","1000","10000"],"lottery_id":"1"}
     */

    private int code;
    private DataBean data;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * lottery : 中国红2
         * price : 2.00
         * img : /upload/lottery/26057-1493033394.png
         * desc : 红红火火
         * rank : ["5","10","20","50","100","200","500","1000","10000"]
         * lottery_id : 1
         * "sell_status":"销售状态（爆，新，...）",
         * "num":"2"
         */

        private String lottery;
        private double price;
        private double money;
        private String img;
        private String desc;
        private int lottery_id;
        private String sell_status;
        private List<String> rank;
        private int num;

        public String getLottery() {
            return lottery;
        }

        public void setLottery(String lottery) {
            this.lottery = lottery;
        }

        public double getPrice() {
            return price;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getSell_status() {
            return sell_status;
        }

        public void setSell_status(String sell_status) {
            this.sell_status = sell_status;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getLottery_id() {
            return lottery_id;
        }

        public void setLottery_id(int lottery_id) {
            this.lottery_id = lottery_id;
        }

        public List<String> getRank() {
            return rank;
        }

        public void setRank(List<String> rank) {
            this.rank = rank;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
