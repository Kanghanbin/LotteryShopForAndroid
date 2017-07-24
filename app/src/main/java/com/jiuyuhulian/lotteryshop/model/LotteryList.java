package com.jiuyuhulian.lotteryshop.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by khb on 2017/4/24.
 */

public class LotteryList {


    /**
     * code : 0
     * data : [{"id":"彩票ID","lottery":"彩票名称","price":"价格","img":"http://test-static.9yu.tv 图片地址","sell_status":"销售状态", "num" = "库存量"},"......"]
     */

    private int code;
    private List<DataBean> data;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * lottery_id : 彩票ID
         * lottery : 彩票名称
         * price : 价格
         * img : http://test-static.9yu.tv 图片地址
         * sell_status : 销售状态
         *   "money": "包价",
         *     "in_num" = "库存量"
         */

        private int lottery_id;
        private String lottery;
        private double price;
        private double money;
        private String img;
        private String sell_status;
        private int num;


        public int getId() {
            return lottery_id;
        }

        public void setId(int lottery_id) {
            this.lottery_id = lottery_id;
        }

        public String getLottery() {
            return lottery;
        }

        public void setLottery(String lottery) {
            this.lottery = lottery;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }


        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getSell_status() {
            return sell_status;
        }

        public void setSell_status(String sell_status) {
            this.sell_status = sell_status;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
